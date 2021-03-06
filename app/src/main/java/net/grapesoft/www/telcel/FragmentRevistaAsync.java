package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.FileCache;
import Utitilies.FileDownloader;
import Utitilies.List_adapted_Revista;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 20/06/2016.
 */
public class FragmentRevistaAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Revista> {

    ProgressDialog dialog;
    Activity activity;
    ImageView img;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "", NAMESPACE = "",SOAP_ACTIONCTE = "" , USERNAME_HEADER = "", PASS_HEADER = "";
    public boolean primer = true,primer2 = true;
    SessionManagement session;

    public FragmentRevistaAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        imageHttpAddress = activity.getText(R.string.URL_media).toString();
        this.activity = activity;
        NAMESPACE = activity.getText(R.string.NAMESPACE).toString();
        SOAP_ACTIONCTE = activity.getText(R.string.SOAP_ACTION).toString();
        USERNAME_HEADER = activity.getText(R.string.USERNAME_HEADER).toString();
        PASS_HEADER = activity.getText(R.string.PASS_HEADER).toString();
    }
    Context context;
    private FragmentRevistaAsync(Context context) {
        this.context = context.getApplicationContext();
    }


    @Override
    protected List_adapted_Revista doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();


        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String revista = session.getRevistaDetails();

            if(revista == null || revista == "" || revista.contains("error")) {

                //----SOAP

                String SOAP_ACTION = SOAP_ACTIONCTE + params[0].get(1);

                // Modelo el request

                SoapObject request = new SoapObject(NAMESPACE, params[0].get(1));

                SoapObject datosG = new SoapObject();

                datosG.addProperty("token", params[0].get(2));
                datosG.addProperty("reg", params[0].get(3));


                request.addProperty("datos_generales_entrada", datosG);

                // Modelo el Sobre
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                //header

                List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
                headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode((USERNAME_HEADER+":"+PASS_HEADER).getBytes())));

                sobre.dotNet = false;
                sobre.implicitTypes = true;
                sobre.setAddAdornments(false);
                sobre.encodingStyle = SoapSerializationEnvelope.XSD;
                sobre.setOutputSoapObject(request);

                // Modelo el transporte
                HttpTransportSE transporte = new HttpTransportSE(Proxy.NO_PROXY, IP, 35000);
                transporte.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                transporte.debug = true;

                // Llamada
                transporte.call(SOAP_ACTION, sobre, headerList);
                Log.i("Respuesta", sobre.bodyIn.toString());
                if(sobre.bodyIn.toString().contains("fault"))
                {
                    // Llamada
                    transporte.call(SOAP_ACTION, sobre, headerList);
                    Log.i("Intento", "segundo");
                }
                // Resultado
                SoapObject resultado = (SoapObject) sobre.getResponse();
                result11 = resultado.getPropertyAsString("return");

                //--SOAP


                //----RESt
                //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                //HttpClient httpclient = new DefaultHttpClient();
//
                //HttpPost httppost = new HttpPost(IP + params[0].get(1));
                //nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                //nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
//
                //// Execute HTTP Post Request
                //HttpResponse response = httpclient.execute(httppost);
//
                //BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
                //StringBuilder sb = new StringBuilder();
                //sb.append(reader.readLine() + "\n");
                //String line = "0";
                //while ((line = reader.readLine()) != null) {
                //    sb.append(line + "\n");
                //}
                //reader.close();
                //result11 = sb.toString();

                session.createRevistaSession(result11);
            }
            else
            {
                Log.e("Con session REVISTA",revista);
                result11 = revista;
            }

            if(result11.equals("true"+"\n")) {
                // Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            }else if(result11.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else
            {
                //Log.e("Response: ", "JSON");
                if(result11.contains("["))
                    responseArray = new JSONArray(result11);
                else
                    responseArray = new JSONArray("[" + result11 + "]");
            }
            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Revista" ,  "Error");
            }
            else {
                int i = 0,ids= 0;

                for (int ii = 0; ii < responseArray.length(); ii++) {
                        if (Integer.parseInt(responseArray.getJSONObject(i).get("id").toString()) > ids)
                        {
                            ids = Integer.parseInt(responseArray.getJSONObject(i).get("id").toString());
                            i = ii;
                        }
                }


                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String url_podcast = responseArray.getJSONObject(i).get("url_pdf").toString();

                    URL imageUrl = null;


            //      imageUrl = new URL(imageHttpAddress + img_previa);
            //      HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            //  try {
            //      conn.connect();
            //      loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            //      conn.disconnect();
            //  }
            //  catch (FileNotFoundException e)
            //  {
            //      loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
            //  }
                try {
                    FileCache m = new FileCache();

                    byte[] c = m.getObject(activity, img_previa);

                    if (c != null && c.length > 0)

                        loadedImage = BitmapFactory.decodeByteArray(c, 0, c.length);


                    else {
                        Log.e("cache_", "No se encontro el objeto y se guarda");

                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        boolean result = m.saveObject(activity, loadedImage, img_previa);

                        if (result)
                            Log.e("cache_0", "Saved object");
                        else
                            Log.e("cache_0", "Error saving object");
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

                datos.add(new Lista_Entrada(id,loadedImage, titulo,url_podcast, "", R.drawable.descarga));

            }
        } catch (JSONException e) {
            Log.e("Async", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        List_adapted_Revista adaptadorLts = new List_adapted_Revista(activity, R.layout.entrada_revista, datos){
            @Override
            public void onEntrada(Object entrada, final View view) {
                Log.e("Item Revista", "NO");
                if (entrada != null) {

                    TextView titulo = (TextView) view.findViewById(R.id.textView2);

                    if (titulo != null)
                        titulo.setText(((Lista_Entrada) entrada).get_titulo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.revista);
                    if (imagen_entrada != null) {
                        imagen_entrada.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                    }

                    ImageView imagen_descarga = (ImageView) view.findViewById(R.id.imgDescarga);
                    if (imagen_descarga != null) {
                        imagen_descarga.setImageResource(((Lista_Entrada) entrada).get_idImagen2());
                        imagen_descarga.setTag(((Lista_Entrada) entrada).get_url());
                    }

                    TextView textView15 = (TextView) view.findViewById(R.id.textView15);
                    if (textView15 != null) {

                        textView15.setTag(((Lista_Entrada) entrada).get_url());
                    }

                    WindowManager wm = (WindowManager) activity.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();

                    LinearLayout RelRevista = (LinearLayout) view.findViewById(R.id.RelRevista);
                    int height = (int) (display.getHeight() * (0.79));
                    RelRevista.setMinimumHeight(height);

                    assert imagen_descarga != null;
                    imagen_descarga.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Log.e("URL PDF", arg0.getTag().toString());
                            abrirPDF(arg0.getTag().toString());
                        }
                    });

                    assert textView15 != null;
                    textView15.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("URL PDF", v.getTag().toString());
                            abrirPDF(v.getTag().toString());
                        }
                    });

                }
            }
        };
        return adaptadorLts;
    }

    @Override
    protected void onPostExecute(List_adapted_Revista result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.lvRevista);
        if(result != null && lista != null)
        lista.setAdapter(result);

        ProgressBar pBar = (ProgressBar)activity.findViewById(R.id.loadingPanelRevista);
        if(pBar != null)
        pBar.setVisibility(View.GONE);
    }

    public void abrirPDF(String URL){


        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        Uri data = Uri.parse(URL);
        intent.setDataAndType(data, "application/pdf");
        activity.startActivity(intent);

    }

    public void download(View v)
    {
        new DownloadFile().execute("http://internetencaja.com.mx/telcel/revistas/revista-demo.pdf", "revista-demo.pdf");
    }

    public void ver(View v)
    {


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "revista-demo.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            context.startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){

        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.DownloadFile(fileUrl, pdfFile);
            return null;
        }
    }
}
