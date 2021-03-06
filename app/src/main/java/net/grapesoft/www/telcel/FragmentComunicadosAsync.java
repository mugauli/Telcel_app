package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import Utitilies.List_adapted;
import Utitilies.List_adapted_Comunicados;
import Utitilies.List_adapted_Revista;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 24/06/2016.
 */

public class FragmentComunicadosAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Comunicados> {

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

    public FragmentComunicadosAsync(Activity activity) {
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
    protected List_adapted_Comunicados doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();


        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String comunicadosDetails = session.getComunicadosDetails();

            if(comunicadosDetails == null || comunicadosDetails == "" || comunicadosDetails.contains("error")) {

                Log.e("Se obtiene Comunicados","Procesando...");
                //------------SOAP

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
                Log.e("Respuesta", sobre.bodyIn.toString());
                if(sobre.bodyIn.toString().contains("fault"))
                {
                    // Llamada
                    transporte.call(SOAP_ACTION, sobre, headerList);
                    Log.e("Intento comunicados", "segundo");
                }
                // Resultado
                SoapObject resultado = (SoapObject) sobre.getResponse();
                result11 = resultado.getPropertyAsString("return");

                //-----SOAP
                //-------REST
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
//
                session.createComunicadosSession(result11);
            }
            else
            {
                Log.e("Con session COMUNICACOS",comunicadosDetails);
                result11 = comunicadosDetails;
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
                Log.e("Item Comunicados" ,  "Error");
            }
            else {

                for (int i = 0; i < responseArray.length(); i++) {

                       String id = responseArray.getJSONObject(i).get("id").toString();
                       String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                       String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                       String imagen_detalle = responseArray.getJSONObject(i).get("imagen_detalle").toString();
                       String texto = responseArray.getJSONObject(i).get("texto").toString();
                       String fecha = responseArray.getJSONObject(i).get("fecha").toString();
                       String tipo = responseArray.getJSONObject(i).get("tipo").toString();

                       String contenido = responseArray.getJSONObject(i).get("contenido").toString();




                       URL imageUrl = null;

               //      imageUrl = new URL(imageHttpAddress + img_previa);
               //      HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
               //   try {
               //       conn.connect();
               //       loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
               //       conn.disconnect();
               //   }
               //   catch (FileNotFoundException e)
               //   {
               //       loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage)
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


                    if(texto == null) {

                        datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,fecha,tipo,contenido));

                    }else{
                       datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,fecha,tipo,contenido));

                    }
               //    datos.add(new Lista_Entrada(R.drawable.mas, fecha,texto));

                }
            }
        } catch (JSONException e) {
            Log.e("Error async Comunicados", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error async 1 Comunicados", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error async 2 Comunicados", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error async 3 Comunicados", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        List_adapted_Comunicados adaptadorLts = new List_adapted_Comunicados(activity, R.layout.entrada_comunicados, datos){
            @Override
            public void onEntrada(Object entrada, View view) {



                if (entrada != null) {

                    if (primer) {
                        primer = false;

                        ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenUC);
                        if (imagen_noticias != null) {
                            Log.e("imagen","pricipal");
                            imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                            imagen_noticias.setScaleType(ImageView.ScaleType.FIT_XY);
                        }

                        TextView noticiafecha = (TextView) activity.findViewById(R.id.fechaUC);
                        if (noticiafecha != null)
                            noticiafecha.setText(((Lista_Entrada) entrada).get_fecha());
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        noticiafecha.setTypeface(tfl);

                        TextView noticiatitulo = (TextView) activity.findViewById(R.id.titUC);

                        if (noticiatitulo != null)
                            noticiatitulo.setText(((Lista_Entrada) entrada).get_titulo());

                        noticiatitulo.setTypeface(tf);

                        TextView noticiaDescripcion = (TextView) activity.findViewById(R.id.descUC);

                        if (noticiaDescripcion != null) {
                            String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                            //desc = desc.substring(0,10);
                            noticiaDescripcion.setText(Html.fromHtml(desc));
                        }

                        TextView texto_tipo = (TextView) view.findViewById(R.id.comunicadotipoUC);

                        if (texto_tipo != null)
                            texto_tipo.setText(((Lista_Entrada) entrada).get_contenido());

                        LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalUC);

                        principal.setTag(entrada);
                    }

                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.comunicadofecha);
                    Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                    Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_fecha());

                    texto_superior_entrada.setTypeface(tfl);



                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.comunicadotitulo);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_titulo());
                    texto_inferior_entrada.setTypeface(tf);



                    TextView texto_tipo = (TextView) view.findViewById(R.id.comunicadotipo);

                    if (texto_tipo != null) {
                        String tp = "TP";
                        String in = "IF";
                        String ix = "IX";
                        String cm = "CM";
                        String todos = ((Lista_Entrada) entrada).get_contenido();
                        //Toast toast = Toast.makeText(view.getContext(), todos + texto_tipo, Toast.LENGTH_SHORT);
                        //toast.show();


                        //Toast.makeText(view.getContext(), todos , Toast.LENGTH_SHORT).show();
                        if(todos.compareTo(tp)==0 ){
                            texto_tipo.setText("TIPS SALUD");
                            texto_tipo.setTypeface(tfl);

                        }
                        if(todos.compareTo(in)==0 ){
                            texto_tipo.setText("INFOGRAFIA");
                            texto_tipo.setTypeface(tfl);

                        }
                        if(todos.compareTo(ix)==0 ){
                            texto_tipo.setText("INFORMATE");
                            texto_tipo.setTypeface(tfl);

                        }
                        if(todos.compareTo(cm)==0 ){
                            texto_tipo.setText("COMPARTE");
                            texto_tipo.setTypeface(tfl);

                        }

//                        texto_tipo.setText(((Lista_Entrada) entrada).get_contenido());
                    }
                    view.setTag(entrada);


                    //assert imagen_entrada2 != null;
                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenUC);
                            TextView fechaGrupo = (TextView) activity.findViewById(R.id.fechaUC);
                            TextView titGrupo = (TextView) activity.findViewById(R.id.titUC);
                            TextView descGrupo = (TextView) activity.findViewById(R.id.descUC);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalUC);

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
    protected void onPostExecute(List_adapted_Comunicados result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.listcomunicados);
        if(result != null && lista != null)
            lista.setAdapter(result);
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelCominicados);
        if(pBar != null)
        pBar.setVisibility(View.GONE);

    }
}
