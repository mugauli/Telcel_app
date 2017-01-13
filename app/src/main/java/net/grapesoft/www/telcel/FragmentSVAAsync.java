package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
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
import Utitilies.List_adapted_SVA;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;
import Utitilies.SvaElement;

/**
 * Created by Mugauli on 21/07/2016.
 */
public class FragmentSVAAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_SVA> {

    ProgressDialog dialog;
    Activity activity;
    ImageView img;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage,loadedImage2;
    public String IP = "",tokenCTE = "", NAMESPACE = "",SOAP_ACTIONCTE = "";
    public boolean primer = true,primer2 = true;
    SessionManagement session;



    public FragmentSVAAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
        NAMESPACE = activity.getText(R.string.NAMESPACE).toString();
        SOAP_ACTIONCTE = activity.getText(R.string.SOAP_ACTION).toString();

    }



    @Override
    protected List_adapted_SVA doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String video = session.getSVAProductosDetails();

            if(video == null || video == "") {

                Log.e("Se obtiene SVA","Procesando...");

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
                transporte.call(SOAP_ACTION, sobre);

                Log.i("Respuesta", sobre.bodyIn.toString());
                if(sobre.bodyIn.toString().contains("fault"))
                {
                    // Llamada
                    transporte.call(SOAP_ACTION, sobre);
                    Log.i("Intento", "segundo");
                }

                // Resultado
                SoapObject resultado = (SoapObject) sobre.getResponse();
                result11 = resultado.getPropertyAsString("return");

                //--SOAP


                //----REST
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

                session.createSVAProductosSession(result11);
            }
            else
            {
                Log.e("Con session SVA",video);
                result11 = video;
            }

            if(result11.equals("true"+"\n")) {
                // Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            }else if(result11.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else
            {
                Log.e("Response JSON SVA:", result11.toString());
                if(result11.contains("["))
                    responseArray = new JSONArray(result11);
                else
                    responseArray = new JSONArray("[" + result11 + "]");
            }

            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item SVA" ,  "Error");
            }
            else {
                boolean par = true;

                String id="", titulo="", img_previa="", img_mini="", img_detalle="", texto="", fecha="",
                        id2, titulo2, img_previa2, img_mini2, img_detalle2, texto2, fecha2;

                for (int i = 0; i < responseArray.length(); i++) {

                    if (par) {
                        Log.e("TIPO","1");
                        par = false;
                        id = responseArray.getJSONObject(i).get("id").toString();
                        titulo = responseArray.getJSONObject(i).get("titulo").toString();
                        img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                        img_mini = responseArray.getJSONObject(i).get("img_mini").toString();
                        img_detalle = responseArray.getJSONObject(i).get("img_detalle").toString();
                        texto = responseArray.getJSONObject(i).get("texto").toString();
                        fecha = responseArray.getJSONObject(i).get("fecha").toString();
                        URL imageUrl = null;
                     //   imageUrl = new URL(imageHttpAddress + img_mini);
                     //   HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
//
                     //   try {
                     //       conn.connect();
                     //       loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                     //       conn.disconnect();
                     //   }
                     //   catch (FileNotFoundException e)
                     //   {
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

                    } else {

                        Log.e("TIPO","2");
                        par= true;
                        id2 = responseArray.getJSONObject(i).get("id").toString();
                        titulo2 = responseArray.getJSONObject(i).get("titulo").toString();
                        img_previa2 = responseArray.getJSONObject(i).get("img_previa").toString();
                        img_mini2 = responseArray.getJSONObject(i).get("img_mini").toString();
                        img_detalle2 = responseArray.getJSONObject(i).get("img_detalle").toString();
                        texto2 = responseArray.getJSONObject(i).get("texto").toString();
                        fecha2 = responseArray.getJSONObject(i).get("fecha").toString();

                        URL imageUrl = null;

                    //   imageUrl = new URL(imageHttpAddress + img_mini2);
                    //   HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();

                    //   try {
                    //       conn.connect();
                    //       loadedImage2 = BitmapFactory.decodeStream(conn.getInputStream());
                    //       conn.disconnect();
                    //   }
                    //   catch (FileNotFoundException e)
                    //   {
                    //       loadedImage2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                    //   }

                        try {
                            FileCache m = new FileCache();

                            byte[] c = m.getObject(activity, img_mini2);

                            if (c != null && c.length > 0)

                                loadedImage2 = BitmapFactory.decodeByteArray(c, 0, c.length);


                            else {
                                Log.e("cache_", "No se encontro el objeto y se guarda");

                                imageUrl = new URL(imageHttpAddress + img_mini2);
                                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                                try {
                                    conn.connect();
                                    loadedImage2 = BitmapFactory.decodeStream(conn.getInputStream());
                                    conn.disconnect();
                                } catch (FileNotFoundException e) {
                                    loadedImage2 = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                                }
                                boolean result = m.saveObject(activity, loadedImage2, img_mini2);

                                if (result)
                                    Log.e("cache_0", "Saved object");
                                else
                                    Log.e("cache_0", "Error saving object");
                            }
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }


                        SvaElement svaElement1 = new SvaElement(id, loadedImage, titulo, img_detalle, texto, fecha, img_mini);
                        SvaElement svaElement2 = new SvaElement(id2, loadedImage2, titulo2, img_detalle2, texto2, fecha2, img_mini2);
                        datos.add(new Lista_Entrada(svaElement1, svaElement2, 2));
                    }

                    if (i == responseArray.length() - 1 && !par) {
                        Log.e("TIPO1","1");
                        SvaElement svaElement1 = new SvaElement(id, loadedImage, titulo, img_detalle, texto, fecha, img_mini);
                        datos.add(new Lista_Entrada(id, loadedImage, titulo, img_detalle, texto, fecha, img_mini,svaElement1, 1));
                    }


                    //Lista_Entrada(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha, String img_mini)

                }
            }


        } catch (JSONException e) {
            Log.e("Error Async 0 SVA", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error Async 1 SVA", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error Async 2 SVA", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error Async 3 SVA", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        List_adapted_SVA adaptadorLts = new List_adapted_SVA(activity, R.layout.entrada_sva, datos){

            @Override
            public void onEntrada(Object entrada, View view) {
                //  Log.e ("Entrada Video", ((Lista_Entrada) entrada).get_id());
                if (entrada != null) {

                    DisplayMetrics metrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);


                    int height = metrics.heightPixels; // alto absoluto en pixels
                    Double d = new Double(height * 0.3);
                    int i = d.intValue();

                    LinearLayout containerSVA = (LinearLayout) activity.findViewById(R.id.containerSVA);
                    if(containerSVA != null)
                    containerSVA.setMinimumHeight(i);

                    int type =  ((Lista_Entrada)entrada).get_type();

                    //Toast.makeText(activity, String.valueOf(type), Toast.LENGTH_LONG).show();
                    if(type == 2) {

                        Log.e("TYPE",""+type);

                        LinearLayout linear = (LinearLayout) view.findViewById(R.id.linearSva2);
                        if(linear != null)
                            linear.setVisibility(View.VISIBLE);

                        SvaElement sva1 = ((Lista_Entrada) entrada).get_svaelement1();
                        SvaElement sva2 = ((Lista_Entrada) entrada).get_svaelement2();

                        if(primer){
                            primer = false;

                            ImageView imgSVA = (ImageView) activity.findViewById(R.id.imgSVA);
                            imgSVA.setScaleType(ImageView.ScaleType.FIT_XY);
                           // TextView tituloSVA = (TextView) activity.findViewById(R.id.tituloSVA);
                           // TextView descSVA = (TextView) activity.findViewById(R.id.descSVA);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalSva);

                            principal.setTag(sva1);
                            imgSVA.setImageBitmap(sva1.get_img_previaSva());
                           // tituloSVA.setText(sva1.get_tituloSva());
                           // descSVA.setText(Html.fromHtml(sva1.get_textoDebajoSva()));
                        }

                        ImageView imagenSva1 = (ImageView) view.findViewById(R.id.imagenSva1);
                        if (imagenSva1 != null) {
                            imagenSva1.setImageBitmap(sva1.get_img_previaSva());
                            imagenSva1.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        TextView tituloSVA = (TextView) view.findViewById(R.id.tituloSva1);
                        if (tituloSVA != null) {

                            if(sva1.get_tituloSva().length() > 20) {
                              tituloSVA.setTextSize(10);
                            }
                            else
                                tituloSVA.setTextSize(13);
                            tituloSVA.setText(sva1.get_tituloSva());
                        }
                        LinearLayout linearSva1 =  (LinearLayout) view.findViewById(R.id.linearSva1);
                        if (linearSva1 != null) {
                            linearSva1.setTag(sva1);
                            linearSva1.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                    ImageView imagenVideo = (ImageView) activity.findViewById(R.id.imgSVA);
                                    SvaElement Entrada = (SvaElement) arg0.getTag();

                                    imagenVideo.setImageBitmap(Entrada.get_img_previaSva());
                                    LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalSva);
                                    principal.setTag(Entrada);

                                }
                            });
                        }

                        ImageView imagenSva2 = (ImageView) view.findViewById(R.id.imagenSva2);
                        if (imagenSva2 != null) {
                            imagenSva2.setImageBitmap(sva2.get_img_previaSva());
                            imagenSva2.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        TextView tituloSVA2 = (TextView) view.findViewById(R.id.tituloSva2);
                        if (tituloSVA2 != null) {
                            String tit = "";
                            if(sva2.get_tituloSva().length() > 20) {
                                tituloSVA2.setTextSize(10);
                            }
                            else
                                tituloSVA2.setTextSize(13);
                            tituloSVA2.setText(sva2.get_tituloSva());
                        }
                        LinearLayout linearSva2 =  (LinearLayout) view.findViewById(R.id.linearSva2);
                        if (linearSva2 != null) {
                            linearSva2.setTag(sva2);
                            linearSva2.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                    ImageView imagenVideo = (ImageView) activity.findViewById(R.id.imgSVA);
                                    SvaElement Entrada = (SvaElement) arg0.getTag();
                                    imagenVideo.setImageBitmap(Entrada.get_img_previaSva());
                                    LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalSva);
                                    principal.setTag(Entrada);

                                }
                            });
                        }



                    }
                    else
                    {
                        Log.e("TYPE2",""+type);

                        LinearLayout linear = (LinearLayout) activity.findViewById(R.id.linearSva2);
                        if(linear != null)
                            linear.setVisibility(View.VISIBLE);

                        if(primer){
                            primer = false;

                            ImageView imgSVA = (ImageView) activity.findViewById(R.id.imgSVA);
                            imgSVA.setScaleType(ImageView.ScaleType.FIT_XY);
                            imgSVA.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalSva);
                            principal.setTag(((Lista_Entrada) entrada).get_svaelement1());
                        }

                        ImageView imagenSva1 = (ImageView) view.findViewById(R.id.imagenSva1);
                        if (imagenSva1 != null) {
                            imagenSva1.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                            imagenSva1.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        TextView tituloSVA = (TextView) view.findViewById(R.id.tituloSva1);
                        if (tituloSVA != null)
                            tituloSVA.setText(((Lista_Entrada) entrada).get_titulo());
                        LinearLayout linearSva1 =  (LinearLayout) view.findViewById(R.id.linearSva1);
                        if (linearSva1 != null) {
                            linearSva1.setTag(((Lista_Entrada) entrada).get_svaelement1());
                            linearSva1.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                    ImageView imagenVideo = (ImageView) activity.findViewById(R.id.imgSVA);
                                    SvaElement Entrada = (SvaElement) arg0.getTag();

                                    imagenVideo.setImageBitmap(Entrada.get_img_previaSva());
                                    LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalSva);
                                    principal.setTag(Entrada);

                                }
                            });
                        }
                    }
                }
            }



        };

        return adaptadorLts;
    }

    @Override
    protected void onPostExecute(List_adapted_SVA result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.lstSVA);
        if (result != null && lista != null)
            lista.setAdapter(result);

        ProgressBar pBar = (ProgressBar) activity.findViewById(R.id.loadingPanelSVA);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }
}