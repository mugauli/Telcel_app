package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import Utitilies.HorizontalListView;
import Utitilies.List_adapted_Lanzamientos;
import Utitilies.List_adapted_Noticias;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 22/07/2016.
 */
public class FragmentLanzamientosAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Lanzamientos> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage,loadedImage2;
    public String IP = "",tokenCTE = "", NAMESPACE = "",SOAP_ACTIONCTE = "" , USERNAME_HEADER = "", PASS_HEADER = "";
    public boolean primer3 = true;
    SessionManagement session;

    public FragmentLanzamientosAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
        NAMESPACE = activity.getText(R.string.NAMESPACE).toString();
        SOAP_ACTIONCTE = activity.getText(R.string.SOAP_ACTION).toString();
        USERNAME_HEADER = activity.getText(R.string.USERNAME_HEADER).toString();
        PASS_HEADER = activity.getText(R.string.PASS_HEADER).toString();
    }

    @Override
    protected List_adapted_Lanzamientos doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String lanzamientosProductosDetails = session.getLanzamientosProductosDetails();

            if (lanzamientosProductosDetails == null || lanzamientosProductosDetails == "" || lanzamientosProductosDetails.contains("error")) {

                Log.e("Se obtiene LANZAMIENTOS", "Procesando...");

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

                //----REST
                //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                //HttpClient httpclient = new DefaultHttpClient();

                //HttpPost httppost = new HttpPost(IP + params[0].get(1));
                //nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                //nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                //Log.e("IP", IP + params[0].get(1));
                //// Execute HTTP Post Request
                //HttpResponse response = httpclient.execute(httppost);

                //BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
                //StringBuilder sb = new StringBuilder();
                //sb.append(reader.readLine() + "\n");
                //String line = "0";
                //while ((line = reader.readLine()) != null) {
                //    sb.append(line + "\n");
                //}
                //reader.close();
                //result11 = sb.toString();
                //--REST


                session.createLanzamientosProductosSession(result11);
            } else {
                Log.e("Con session LANZAMIENTOS", lanzamientosProductosDetails);
                result11 = lanzamientosProductosDetails;
            }

            //Log.e("Response: ", result11);

            if (result11.equals("true" + "\n")) {
                // Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            } else if (result11.equals("false" + "\n")) {
                //Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else {
                //Log.e("Response: ", "JSON");
                if (result11.contains("["))
                    responseArray = new JSONArray(result11);
                else
                    responseArray = new JSONArray("[" + result11 + "]");
            }

            if (responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item LANZAMIENTOS", "RESP FALSE");
            }else  if (responseArray.getJSONObject(0).has("error")) {
                Log.e("Item LANZAMIENTOS", "Error");
            }  else {

                for (int i = 0; i < responseArray.length(); i++) {
                    Log.e("Response Item Lanzamientos: ", responseArray.getJSONObject(i).toString());

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String img_mini = responseArray.getJSONObject(i).get("img_mini").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();
                    JSONArray imagenes_slide = responseArray.getJSONObject(i).getJSONArray("imagenes_slide");
                    // String imagenes_slide_Json = responseArray.getJSONObject(0).getJSONArray("imagenes_slide").toString();
                    ArrayList<String> imagenes_slider = new ArrayList<String>();

                    for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                        imagenes_slider.add(imagenes_slide.getJSONObject(ii).get("url_img").toString());
                    }

                    URL imageUrl = null;
                 //   imageUrl = new URL(imageHttpAddress + img_previa);
                 //   HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                 //   try {
                 //       conn.connect();
                 //       loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                 //       conn.disconnect();
                 //   } catch (FileNotFoundException e) {
                 //       loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                 //   }
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

                    URL imageUrlM = null;

               //   imageUrlM = new URL(imageHttpAddress + img_mini);
               //   HttpURLConnection connM = (HttpURLConnection) imageUrlM.openConnection();
               //   try {
               //       connM.connect();
               //       loadedImage2 = BitmapFactory.decodeStream(connM.getInputStream());
               //       connM.disconnect();
               //   } catch (FileNotFoundException e) {
               //       loadedImage2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
//}

                    try {
                        FileCache m = new FileCache();

                        byte[] c = m.getObject(activity, img_mini);

                        if (c != null && c.length > 0)

                            loadedImage2 = BitmapFactory.decodeByteArray(c, 0, c.length);


                        else {
                            Log.e("cache_", "No se encontro el objeto y se guarda");

                            imageUrl = new URL(imageHttpAddress + img_mini);
                            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                            try {
                                conn.connect();
                                loadedImage2 = BitmapFactory.decodeStream(conn.getInputStream());
                                conn.disconnect();
                            } catch (FileNotFoundException e) {
                                loadedImage2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                            }
                            boolean result = m.saveObject(activity, loadedImage2, img_mini);

                            if (result)
                                Log.e("cache_0", "Saved object");
                            else
                                Log.e("cache_0", "Error saving object");
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                    datos.add(new Lista_Entrada(id, loadedImage,loadedImage2, titulo, texto, imagenes_slider,"",0,"","",""));
                    //Lista_Entrada( id,  img_previa,  titulo,  url,  textoDebajo,  ArrayList<String> imagenesSlide,String jsonStr, int idSiguiente,String tituloSig,String imagenSig, String textoSig ) {
                }
            }


        } catch (JSONException e) {
            Log.e("Error JSONException Lanzamientos", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException Lanzamientos", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException Lanzamientos", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException Lanzamientos", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


//        Log.e("Datos Lanzamientos", "" + datos.get(1).get_titulo());
        Log.e("Llego", "al final");
        return new List_adapted_Lanzamientos(activity, R.layout.entrada_lanzamientos, datos) {

            @Override
            public void onEntrada(Object entrada, View view) {

                Log.e("Entrada Lanzamientos", ((Lista_Entrada) entrada).get_titulo());

                if (entrada != null) {

                    if (primer3) {
                        primer3 = false;

                        ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenLZ);
                        if (imagen_noticias != null) {
                            Log.e("imagen", "pricipal");
                            imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa2());
                            imagen_noticias.setScaleType(ImageView.ScaleType.FIT_XY);
                        }

                        TextView lanzamientosTitulo = (TextView) activity.findViewById(R.id.titLZ);

                        if (lanzamientosTitulo != null)
                            lanzamientosTitulo.setText(((Lista_Entrada) entrada).get_titulo());

                        TextView lanzamientosDescripcion = (TextView) activity.findViewById(R.id.descLZ);

                        if (lanzamientosDescripcion != null) {
                            String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                            // desc = desc.substring(0,200);
                            lanzamientosDescripcion.setText(Html.fromHtml(desc));
                        }
                        LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalLZ);
                        principal.setTag(entrada);

                    }
                    //    else {

                    ImageView imagen_lanzamientos = (ImageView) view.findViewById(R.id.imagelanzamientos);
                    if (imagen_lanzamientos != null)
                        imagen_lanzamientos.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                    TextView lanzamientosTitulo = (TextView) view.findViewById(R.id.lanzamientostitulo);

                    /*if (lanzamientosTitulo != null)
                        lanzamientosTitulo.setText(((Lista_Entrada) entrada).get_titulo());*/
                    Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                    Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                    Integer conteo = 0;
                    conteo = ((Lista_Entrada) entrada).get_titulo().length();
                    if (lanzamientosTitulo != null && conteo <=20){
                        lanzamientosTitulo.setText(((Lista_Entrada) entrada).get_titulo());

                        lanzamientosTitulo.setTypeface(tf);
                    }else{
                        lanzamientosTitulo.setText(((Lista_Entrada) entrada).get_titulo().substring(0,13)+ "...");

                        lanzamientosTitulo.setTypeface(tf);
                    }

                    view.setTag(entrada);


                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenLZ);
                            TextView titGrupo = (TextView) activity.findViewById(R.id.titLZ);
                            TextView descGrupo = (TextView) activity.findViewById(R.id.descLZ);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalLZ);

                            Lista_Entrada Entrada = (Lista_Entrada) arg0.getTag();
                            imagenGrupo.setImageBitmap(Entrada.get_img_previa());
                            titGrupo.setText(Entrada.get_titulo());
                            descGrupo.setText(Html.fromHtml(Entrada.get_textoDebajo()));
                            principal.setTag(Entrada);

                        }
                    });
                }

                //   }
            }
        };


    }

    @Override
    protected void onPostExecute(List_adapted_Lanzamientos result) {

        super.onPostExecute(result);
        HorizontalListView listview = (HorizontalListView) activity.findViewById(R.id.listLanzamientos);
       // lista = (ListView)activity.findViewById(R.id.listLanzamientos);
        if(result != null && listview != null) {
            listview.setAdapter(result);
            Log.e("Llego Lanzamientos", ""+result.getCount());
        }else
        {
            Log.e("No llego Lanzamientos", "Algo paso");
        }

        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelLanzamientos);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }

}
