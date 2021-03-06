package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
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
import org.ksoap2.transport.HttpResponseException;
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
import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by umunoz on 11/07/2016.
 */
public class FragmentCampanaAsync  extends AsyncTask<ArrayList<String>, Integer, List_adapted> {

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

    public FragmentCampanaAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        imageHttpAddress = activity.getText(R.string.URL_media).toString();
        this.activity = activity;
        NAMESPACE = activity.getText(R.string.NAMESPACE).toString();
        SOAP_ACTIONCTE = activity.getText(R.string.SOAP_ACTION).toString();
        USERNAME_HEADER = activity.getText(R.string.USERNAME_HEADER).toString();
        PASS_HEADER = activity.getText(R.string.PASS_HEADER).toString();

    }

    @Override
    protected List_adapted doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();


        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String campanasInternasDetails = session.getCampanasInternasDetails();

            if(campanasInternasDetails == null || campanasInternasDetails == ""|| campanasInternasDetails.contains("error")) {

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

                //---REST
                //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                //HttpClient httpclient = new DefaultHttpClient();

                //HttpPost httppost = new HttpPost(IP + params[0].get(1));
                //nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                //nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));

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
                //---REST

                session.createCampanasInternasSession(result11);
            }
            else
            {
                Log.e("Con session Campana",campanasInternasDetails);
                result11 = campanasInternasDetails;
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
                Log.e("Item Campana" ,  "Error");
            }
            else {
                for (int i = 0; i < responseArray.length(); i++) {

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String imagen_detalle = responseArray.getJSONObject(i).get("imagen_detalle").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();
                    String tipo = responseArray.getJSONObject(i).get("tipo").toString();
                    String contenido = responseArray.getJSONObject(i).get("contenido").toString();
                    String fecha = responseArray.getJSONObject(i).get("fecha").toString();




                    URL imageUrl = null;



              // imageUrl = new URL(imageHttpAddress + img_previa);
              // HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
              // try {
              //     conn.connect();
              //     loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
              //     conn.disconnect();
              // }
              // catch (FileNotFoundException e)
              // {
              //     loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
              // }
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


                        datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,fecha,tipo));





                }
            }
        } catch (JSONException e) {
            Log.e("Error async Campana", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error async 1 Campana", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error async 2 Campana", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error async 3 Campana", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        List_adapted adaptadorLts = new List_adapted(activity, R.layout.entrada_campana, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    if (primer) {
                        primer = false;

                        ImageView imagen_campana = (ImageView) activity.findViewById(R.id.imagenCM);
                        if (imagen_campana != null) {
                            if(loadedImage == null)
                            {
                                imagen_campana.setImageResource(R.drawable.noimage);
                            }else
                                imagen_campana.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                            imagen_campana.setScaleType(ImageView.ScaleType.FIT_XY);
                        }

                        TextView fecha_campana = (TextView) activity.findViewById(R.id.fechaCM);
                        if (fecha_campana != null)
                            fecha_campana.setText(((Lista_Entrada) entrada).get_fecha());
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        fecha_campana.setTypeface(tf);

                        TextView titulo_campana = (TextView) activity.findViewById(R.id.titCM);
                        Integer conteo = 0;
                        conteo = ((Lista_Entrada) entrada).get_titulo().length();
                        if (titulo_campana != null && conteo <=40){
                            titulo_campana.setText(((Lista_Entrada) entrada).get_titulo());

                            titulo_campana.setTypeface(tf);
                        }else{
                            titulo_campana.setText(((Lista_Entrada) entrada).get_titulo().substring(0,30)+ "...");

                            titulo_campana.setTypeface(tf);
                        }



                        TextView descripcion_campana = (TextView) activity.findViewById(R.id.descCM);
                        if (descripcion_campana != null) {
                            String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                            descripcion_campana.setText(Html.fromHtml(desc));
                        }
                        LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalCM);
                        principal.setTag(entrada);
                    }

                    ImageView campanaImagen = (ImageView) view.findViewById(R.id.imagenCampanaL);

                    if (campanaImagen != null) {
                        if(loadedImage == null)
                        {
                            campanaImagen.setImageResource(R.drawable.noimage);
                        }else
                        campanaImagen.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                    }

                    TextView campanaFecha = (TextView) view.findViewById(R.id.fechaCampanaL);
                    if (campanaFecha != null)
                        campanaFecha.setText(((Lista_Entrada) entrada).get_fecha());

                    TextView campanaTitulo = (TextView) view.findViewById(R.id.tituloCampanaL);

                    Integer conteo = 0;
                    conteo = ((Lista_Entrada) entrada).get_titulo().length();
                    if (campanaTitulo != null && conteo <=40){
                        campanaTitulo.setText(((Lista_Entrada) entrada).get_titulo());
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        campanaTitulo.setTypeface(tf);
                    }else{
                        campanaTitulo.setText(((Lista_Entrada) entrada).get_titulo().substring(0,30) + "...");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        campanaTitulo.setTypeface(tf);
                    }


                    view.setTag(entrada);


                    //assert imagen_entrada2 != null;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenCM);
                            TextView fechaGrupo = (TextView) activity.findViewById(R.id.fechaCM);
                            TextView titGrupo = (TextView) activity.findViewById(R.id.titCM);
                            TextView descGrupo = (TextView) activity.findViewById(R.id.descCM);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalCM);

                            Lista_Entrada Entrada = (Lista_Entrada)arg0.getTag();

                            imagenGrupo.setImageBitmap(Entrada.get_img_previa());
                            fechaGrupo.setText(Entrada.get_fecha());
                            titGrupo.setText(Entrada.get_titulo());
                            descGrupo.setText(Html.fromHtml(Entrada.get_textoDebajo()));
                            principal.setTag(Entrada);
                        }
                    });



                }
            }
        };
        return adaptadorLts;
    }

    @Override
    protected void onPostExecute(List_adapted result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.listCampana);
        if(result != null && lista != null)
            lista.setAdapter(result);
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelCampana);
        if(pBar != null)
            pBar.setVisibility(View.GONE);

    }
}

