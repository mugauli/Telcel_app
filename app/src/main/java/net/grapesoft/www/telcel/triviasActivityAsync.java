package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by umunoz on 03/08/2016.
 */
public class triviasActivityAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray,responseArray2;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "",preguntaPreg="",jsonPreguntas="", NAMESPACE = "",SOAP_ACTIONCTE = "" , USERNAME_HEADER = "", PASS_HEADER = "";
    public boolean primer3 = true,PregBool = true;
    SessionManagement session;

    public triviasActivityAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
        NAMESPACE = activity.getText(R.string.NAMESPACE).toString();
        SOAP_ACTIONCTE = activity.getText(R.string.SOAP_ACTION).toString();
        USERNAME_HEADER = activity.getText(R.string.USERNAME_HEADER).toString();
        PASS_HEADER = activity.getText(R.string.PASS_HEADER).toString();
    }

    @Override
    protected List_adapted doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result2 = "", result11T= "";
        try {

            //Pregunta del dia
            String preguntaDiaDetails = session.getPreguntaDetails();

            if(preguntaDiaDetails == null || preguntaDiaDetails == "" || preguntaDiaDetails.contains("error")) {

                Log.e("Se obtiene Pregunta","Procesando...");

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

                // Resultado
                SoapObject resultado = (SoapObject) sobre.getResponse();
                result2 = resultado.getPropertyAsString("return");

                //--SOAP


                //List<NameValuePair> nameValuePair2 = new ArrayList<NameValuePair>(2);
                //HttpClient httpclient2 = new DefaultHttpClient();

                //HttpPost httppost2 = new HttpPost(IP + params[0].get(1));
                //nameValuePair2.add(new BasicNameValuePair("token", params[0].get(2)));
                //nameValuePair2.add(new BasicNameValuePair("reg", params[0].get(3)));

                //httppost2.setEntity(new UrlEncodedFormEntity(nameValuePair2));
                //Log.e("IP", IP + params[0].get(1));
                //// Execute HTTP Post Request
                //HttpResponse response = httpclient2.execute(httppost2);

                //BufferedReader reader2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
                //StringBuilder sb2 = new StringBuilder();
                //sb2.append(reader2.readLine() + "\n");
                //String line = "0";
                //while ((line = reader2.readLine()) != null) {
                //    sb2.append(line + "\n");
                //}
                //reader2.close();
                //result2 = sb2.toString();


                session.createPreguntaSession(result2);
                Log.e("Sin session Preguntas",result2);
            }
            else
            {
                Log.e("Con session Preguntas",preguntaDiaDetails);
                result2 = preguntaDiaDetails;
            }
            jsonPreguntas = result2;

            if(result2.equals("true"+"\n")) {
                // Log.e("Response: ", "true Int");
                responseArray2 = new JSONArray("[{'resp':'true'}]");
            }else if(result2.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                responseArray2 = new JSONArray("[{'resp':'false'}]");
            } else
            {
                //Log.e("Response: ", "JSON");
                if(result2.contains("["))
                    responseArray2 = new JSONArray(result2);
                else
                    responseArray2 = new JSONArray("[" + result2 + "]");
            }
            if(responseArray2.getJSONObject(0).has("resp")) {
                Log.e("Item Preguntas" ,  "Error");
                PregBool = false;
            }
            else if(responseArray2.getJSONObject(0).has("error")) {
                Log.e("Item Preguntas" ,  "Error");
                PregBool = false;
            }
            else {
                if (responseArray2.length() > 0) {
                    Log.e("Response Item: ", responseArray2.getJSONObject(0).toString());
                    preguntaPreg = responseArray2.getJSONObject(0).get("pregunta").toString();
                }
                else
                {
                    PregBool = false;
                }
            }
            //END pregunta del dia

            String triviasDetails = session.getTriviasDetails();

            if(triviasDetails == null || triviasDetails == "" || triviasDetails.contains("error")) {

                Log.e("Se obtiene Trivias","Procesando..." + params[0].get(0));

                //----SOAP

                String SOAP_ACTIONT = SOAP_ACTIONCTE + params[0].get(0);

                // Modelo el request

                SoapObject requestT = new SoapObject(NAMESPACE, params[0].get(0));

                SoapObject datosGT = new SoapObject();

                datosGT.addProperty("token", params[0].get(2));
                datosGT.addProperty("reg", params[0].get(3));
                datosGT.addProperty("idUsuario", params[0].get(4));


                requestT.addProperty("promociones_entrada.", datosGT);

                // Modelo el Sobre
                SoapSerializationEnvelope sobreT = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobreT.dotNet = false;
                sobreT.implicitTypes = true;
                sobreT.setAddAdornments(false);
                sobreT.encodingStyle = SoapSerializationEnvelope.XSD;
                sobreT.setOutputSoapObject(requestT);

                // Modelo el transporte
                HttpTransportSE transporteT = new HttpTransportSE(Proxy.NO_PROXY, IP, 35000);
                transporteT.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                transporteT.debug = true;

                // Llamada
                transporteT.call(SOAP_ACTIONT, sobreT);
                Log.e("Respuesta Trivia", sobreT.bodyIn.toString());
                if(sobreT.bodyIn.toString().contains("fault"))
                {
                    // Llamada
                    transporteT.call(SOAP_ACTIONT, sobreT);
                    Log.e("Intento trivia", "segundo");
                }
                // Resultado
                SoapObject resultadoT = (SoapObject) sobreT.getResponse();
                result11T = resultadoT.getPropertyAsString("return");

                //--SOAP

                //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                //HttpClient httpclient = new DefaultHttpClient();

                //HttpPost httppost = new HttpPost(IP + params[0].get(0));

                //nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                //nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                //nameValuePair.add(new BasicNameValuePair("idUsuario", params[0].get(4)));

                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                //Log.e("IP", IP + params[0].get(0));

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

               // result11= "[{\"id\":\"1\",\"texto\":\"\",\"tipo\":\"C\",\"titulo\":\"Cruz Azul vs. Pumas\",\"img_previa\":\"http:\\/\\/internetencaja.com.mx\\/telcel\\/promociones\\/cruz-azul-pumas-detalle.png\"},{\"id\":\"2\",\"texto\":\"\",\"tipo\":\"T\",\"titulo\":\"Trivia de prueba\",\"duracion\":\"1\",\"img_previa\":\"http:\\/\\/internetencaja.com.mx\\/telcel\\/videos\\/video-Retroalimentacion.png\",\"elementos\":[{\"idPreg\":\"1\",\"txtPregunta\":\"Pregunta 1 Pregunta 1 Pregunta 1 Pregunta 1 Pregunta 1 Pregunta 1 \",\"respuestas\":[{\"idResp\":\"1\",\"txtRespuesta\":\"Respuesta 1 de 1\",\"valRespuesta\":\"1\"},{\"idResp\":\"2\",\"txtRespuesta\":\"Respuesta 2 de 1\",\"valRespuesta\":\"0\"},{\"idResp\":\"3\",\"txtRespuesta\":\"Respuesta 3 de 1\",\"valRespuesta\":\"0\"}]},{\"idPreg\":\"2\",\"txtPregunta\":\"Pregunta 2 Pregunta 2 Pregunta 2 Pregunta 2 Pregunta 2\",\"respuestas\":[{\"idResp\":\"1\",\"txtRespuesta\":\"Respuesta 1 de 2\",\"valRespuesta\":\"1\"},{\"idResp\":\"2\",\"txtRespuesta\":\"Respuesta 2 de 2\",\"valRespuesta\":\"0\"},{\"idResp\":\"3\",\"txtRespuesta\":\"Respuesta 3 de 2\",\"valRespuesta\":\"0\"}]},{\"idPreg\":\"3\",\"txtPregunta\":\"Pregunta 3 Pregunta 3 Pregunta 3 Pregunta 3 Pregunta 3\",\"respuestas\":[{\"idResp\":\"1\",\"txtRespuesta\":\"Respuesta 1 de 3\",\"valRespuesta\":\"1\"},{\"idResp\":\"2\",\"txtRespuesta\":\"Respuesta 2 de 3\",\"valRespuesta\":\"0\"},{\"idResp\":\"3\",\"txtRespuesta\":\"Respuesta 3 de 3\",\"valRespuesta\":\"0\"}]}]}]\n";
//result11 = "[{\"id\":\"2\",\"tipo\":\"T\",\"texto\":\"<p>Telcel te invita al partido<\\/p>\",\"titulo\":\"Pumas vs. Leon\",\"img_previa\":\"http:\\/\\/internetencaja.com.mx\\/telcel\\/promociones\\/pumas-leon-preview.png\",\"duracion\":\"1\",\"elementos\":[{\"idPreg\":\"1\",\"txtPregunta\":\"?En QUE periodo Francisco Palencia jugo con los Pumas? \",\"respuestas\":[{\"idResp\":\"1\",\"txtRespuesta\":\"2004 al 2010\",\"valRespuesta\":\"1\"},{\"idResp\":\"2\",\"txtRespuesta\":\"2005 al 2009\",\"valRespuesta\":\"0\"},{\"idResp\":\"3\",\"txtRespuesta\":\"2007 al 2011 \",\"valRespuesta\":\"0\"}]},{\"idPreg\":\"2\",\"txtPregunta\":\"?En que anio fue fundado el Club Leon?\",\"respuestas\":[{\"idResp\":\"4\",\"txtRespuesta\":\"1942 \",\"valRespuesta\":\"1\"},{\"idResp\":\"5\",\"txtRespuesta\":\"1944\",\"valRespuesta\":\"0\"},{\"idResp\":\"6\",\"txtRespuesta\":\"1952\",\"valRespuesta\":\"0\"}]},{\"idPreg\":\"3\",\"txtPregunta\":\"?Que marca de celulares patrocina al Club Universidad Nacional? \",\"respuestas\":[{\"idResp\":\"7\",\"txtRespuesta\":\"Huawei\",\"valRespuesta\":\"1\"},{\"idResp\":\"8\",\"txtRespuesta\":\"Samsung\",\"valRespuesta\":\"0\"},{\"idResp\":\"9\",\"txtRespuesta\":\"ZTE\",\"valRespuesta\":\"0\"}]},{\"idPreg\":\"4\",\"txtPregunta\":\"?Como se llama la ultima plataforma digital de aprendizaje que lanzo la Fundacion Carlos Slim?\",\"respuestas\":[{\"idResp\":\"10\",\"txtRespuesta\":\"Capacitate para el empleo\",\"valRespuesta\":\"0\"},{\"idResp\":\"11\",\"txtRespuesta\":\"Aprende.org\",\"valRespuesta\":\"1\"},{\"idResp\":\"12\",\"txtRespuesta\":\"ClikiSalud\",\"valRespuesta\":\"0\"}]}]}]";

                session.createTriviasSession(result11T);
                Log.e("Con create Trivias",result11T);
            }
            else
            {
                Log.e("Con session Trivias",triviasDetails);
                result11T = triviasDetails;
            }

            //Log.e("Response: ", result11);

            if(result11T.equals("true"+"\n")) {
                // Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            }else if(result11T.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else
            {
                //Log.e("Response: ", "JSON");
                if(result11T.contains("["))
                    responseArray = new JSONArray(result11T);
                else
                    responseArray = new JSONArray("[" + result11T + "]");
            }

            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Trivias" ,  "Error");
            }
            else if(responseArray.getJSONObject(0).has("error")) {
                Log.e("Item Trivias" ,  "Error");
            }
            else {

                for (int i = 0; i < responseArray.length(); i++) {
                    Log.e("Response Item: ", responseArray.getJSONObject(i).toString());

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_detalle = responseArray.getJSONObject(i).get("img_previa").toString();
                    String tipo = responseArray.getJSONObject(i).get("tipo").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();

                    Boolean estatus = Boolean.TRUE;
                    Boolean ganador = Boolean.TRUE;
                    String duracion = "1";
                    String preguntas = "0";
                    if(tipo.equals("T"))
                    {
                        preguntas = responseArray.getJSONObject(i).getJSONArray("elementos").toString();
                        duracion = responseArray.getJSONObject(i).get("duracion").toString();
                        estatus = responseArray.getJSONObject(i).get("estatus").toString().equals("1");
                        ganador = !(responseArray.getJSONObject(i).get("ganador").toString().equals("0"));
                        Log.e("estatus",""+estatus);
                    }

                    datos.add(new Lista_Entrada(id,titulo,tipo,texto,img_detalle,preguntas,duracion,estatus,ganador));
                }
            }


        } catch (JSONException e) {
            Log.e("Error JSONException Noticia", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException Noticia", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException Noticia", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException Noticia", e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        Log.e("Llego", "al final");
        return new List_adapted(activity, R.layout.entrada_trivias, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

                Log.e ("Entrada Trivias", ((Lista_Entrada) entrada).get_titulo());

                if (entrada != null){

                    ImageView imagen_trivias = (ImageView) view.findViewById(R.id.imagetrivia);
                    if (imagen_trivias != null) {
                        if(((Lista_Entrada)entrada).get_tipo().equals("T"))
                        {
                            Log.e("imagen", "trivias");
                            //imagen_trivias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                            imagen_trivias.setImageResource(R.drawable.trivia);
                        }else
                        {
                            imagen_trivias.setImageResource(R.drawable.concurso);
                        }


                    }

                    Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                    Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");


                    TextView titulo_trivias = (TextView) view.findViewById(R.id.triviatitulo);
                   // titulo_trivias.setTypeface(tf);
                    if (titulo_trivias != null)
                        titulo_trivias.setText("Trivias");

                    final TextView Descripcion_trivias = (TextView) view.findViewById(R.id.triviadetalle);

                    if (Descripcion_trivias != null) {
                        String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                        Descripcion_trivias.setText(Html.fromHtml(((Lista_Entrada) entrada).get_titulo()));
                    }
                    //Descripcion_trivias.setTypeface(tf);
                    view.setTag(entrada);
                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            Lista_Entrada entrada = (Lista_Entrada)arg0.getTag();

                            if(entrada.get_tipo().equals("C")) {

                                Intent i = new Intent(activity, activity_detalle_trivia.class);

                                i.putExtra("imagen", entrada.get_img_detalle());
                                i.putExtra("titulo", entrada.get_titulo());
                                i.putExtra("descripcion", entrada.get_textoDebajo());

                                activity.startActivity(i);
                            }
                            else
                            {
                                String trivias = session.getTriviasContestadoDetails();
                                if(trivias == null)
                                    trivias = "0";
                                if(trivias.contains("["+entrada.get_id().trim()+"]"))
                                {
                                    Toast toast = Toast.makeText(activity, "Trivia ya contestada.", Toast.LENGTH_LONG);
                                    toast.show();
                                }else if(entrada.get_estatus() || entrada.get_ganador())
                                {
                                    Toast toast = Toast.makeText(activity, "Trivia ya contestada.", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else
                                {
                                    session.createTriviasContestadoSession(trivias+"["+ entrada.get_id().trim()+"],");
                                    Intent i = new Intent(activity, activity_pregunta_respuesta_trivia.class);
                                    i.putExtra("preguntas", entrada.get_preguntas());
                                    i.putExtra("puntos","0");
                                    i.putExtra("siguiente","0");
                                    i.putExtra("trivia", entrada.get_id());
                                    i.putExtra("imagen", entrada.get_img_detalle());
                                    i.putExtra("duracion", entrada.get_duracion());
                                    activity.startActivity(i);
                                }

                            }
                            Log.e("imagen",""+ entrada.get_img_detalle());
                        }
                    });

                }
            }
        };

    }

    @Override
    protected void onPostExecute(List_adapted result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.lstTrivias);
        if(result != null && lista != null) {
            lista.setAdapter(result);
            lista.setDivider(null);
            Log.e("Llego", ""+result.getCount());
        }

        TextView txtPreguntaTrivia = (TextView) activity.findViewById(R.id.txtPreguntaTrivia);
        if(txtPreguntaTrivia!= null && PregBool)
        {
            txtPreguntaTrivia.setText(preguntaPreg);
        }



        LinearLayout btnPreguntaDia = (LinearLayout) activity.findViewById(R.id.btnPreguntaDia);
        if(btnPreguntaDia != null  && PregBool)
        {
            btnPreguntaDia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity,activity_pregunta_respuesta.class);
                    i.putExtra("pregunta",preguntaPreg);
                    i.putExtra("json",jsonPreguntas);
                    activity.startActivity(i);
                }
            });
        }


        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingTrivias);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }

}