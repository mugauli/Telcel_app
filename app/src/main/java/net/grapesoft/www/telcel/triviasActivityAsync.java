package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.List_adapted;
import Utitilies.List_adapted_Noticias;
import Utitilies.Lista_Entrada;
import Utitilies.PreguntaElement;
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
    public String IP = "",tokenCTE = "",preguntaPreg="",jsonPreguntas="";
    public boolean primer3 = true,PregBool = true;
    SessionManagement session;

    public triviasActivityAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected List_adapted doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "", result2 = "";
        try {

            //Pregunta del dia
            String preguntaDiaDetails = session.getPreguntaDetails();

            if(preguntaDiaDetails == null || preguntaDiaDetails == "") {

                Log.e("Se obtiene Trivias","Procesando...");

                List<NameValuePair> nameValuePair2 = new ArrayList<NameValuePair>(2);
                HttpClient httpclient2 = new DefaultHttpClient();

                HttpPost httppost2 = new HttpPost(IP + params[0].get(0));
                nameValuePair2.add(new BasicNameValuePair("token", params[0].get(2)));
                nameValuePair2.add(new BasicNameValuePair("reg", params[0].get(3)));
                httppost2.setEntity(new UrlEncodedFormEntity(nameValuePair2));
                Log.e("IP", IP + params[0].get(1));
                // Execute HTTP Post Request
                HttpResponse response = httpclient2.execute(httppost2);

                BufferedReader reader2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(reader2.readLine() + "\n");
                String line = "0";
                while ((line = reader2.readLine()) != null) {
                    sb2.append(line + "\n");
                }
                reader2.close();
                result2 = sb2.toString();


                session.createPreguntaSession(result11);
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

            if(triviasDetails == null || triviasDetails == "") {

                Log.e("Se obtiene Trivias","Procesando...");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(IP + params[0].get(1));
                nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                Log.e("IP", IP + params[0].get(1));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");
                String line = "0";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
                result11 = sb.toString();


                session.createTriviasSession(result11);
            }
            else
            {
                Log.e("Con session Trivias",triviasDetails);
                result11 = triviasDetails;
            }

            //Log.e("Response: ", result11);

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
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String tipo = responseArray.getJSONObject(i).get("tipo").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();

                    URL imageUrl = null;
                    imageUrl = new URL(imageHttpAddress + img_previa);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    try {
                        conn.connect();
                        loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                        conn.disconnect();
                    }
                    catch (FileNotFoundException e)
                    {
                        loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                    }
                    datos.add(new Lista_Entrada(id,loadedImage, titulo,tipo,texto,0,img_previa));
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
        }

        Log.e("Llego", "al final");
        return new List_adapted(activity, R.layout.entrada_trivias, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

                Log.e ("Entrada Trivias", ((Lista_Entrada) entrada).get_titulo());

                if (entrada != null){

                    ImageView imagen_trivias = (ImageView) view.findViewById(R.id.imagetrivia);
                    if (imagen_trivias != null) {
                        Log.e("imagen", "pricipal");
                        imagen_trivias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                    }

                    TextView titulo_trivias = (TextView) view.findViewById(R.id.triviatitulo);

                    if (titulo_trivias != null)
                        titulo_trivias.setText(((Lista_Entrada) entrada).get_titulo());

                    final TextView Descripcion_trivias = (TextView) view.findViewById(R.id.triviadetalle);

                    if (Descripcion_trivias != null) {
                        String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                        Descripcion_trivias.setText(Html.fromHtml(desc));
                    }

                    view.setTag(entrada);
                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            Lista_Entrada entrada = (Lista_Entrada)arg0.getTag();

                            Intent i = new Intent(activity, activity_detalle_triviaC.class);

                            i.putExtra("imagen",entrada.get_img_detalle());
                            i.putExtra("titulo",entrada.get_titulo());
                            i.putExtra("descripcion",entrada.get_textoDebajo());

                            activity.startActivity(i);
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