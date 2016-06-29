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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.List_adapted_Grupo;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
<<<<<<< Updated upstream
 * Created by Mugauli on 28/06/2016.
=======
 * Created by umunoz on 27/06/2016.
>>>>>>> Stashed changes
 */
public class FragmentGrupoAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Grupo> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer = true;
    SessionManagement session;

    public FragmentGrupoAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected List_adapted_Grupo doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String grupo_carso= session.getGrupoCarsoDetails();

            if(grupo_carso == null || grupo_carso == "") {

                Log.e("Se obtiene GRUPO","Procesando...");

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


                session.createGrupoCarsoSession(result11);
            }
            else
            {
                Log.e("Con session GRUPO",grupo_carso);
                result11 = grupo_carso;
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
                Log.e("Item GRUPO" ,  "Error");
            }
            else {

                for (int i = 0; i < responseArray.length(); i++) {
                    Log.e("Response Item: ", responseArray.getJSONObject(i).toString());

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String imagen_detalle = responseArray.getJSONObject(i).get("imagen_detalle").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();
                    String fecha = responseArray.getJSONObject(i).get("fecha").toString();

                    URL imageUrl = null;
                    imageUrl = new URL(imageHttpAddress + img_previa);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                    conn.disconnect();

                    datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,fecha));
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

        List_adapted_Grupo ltsGrupo = new List_adapted_Grupo(activity, R.layout.entrada_grupo, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

                Log.e ("Entrada Noticia", ((Lista_Entrada) entrada).get_titulo());

                if (entrada != null) {

                    if (primer) {
                        primer = false;

                        ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenGrupo);
                        if (imagen_noticias != null) {
                            Log.e("imagen","pricipal");
                            imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                        }

                        TextView noticiafecha = (TextView) activity.findViewById(R.id.fechaGrupo);
                        if (noticiafecha != null)
                            noticiafecha.setText(((Lista_Entrada) entrada).get_fecha());

                        TextView noticiatitulo = (TextView) activity.findViewById(R.id.titGrupo);

                        if (noticiatitulo != null)
                            noticiatitulo.setText(((Lista_Entrada) entrada).get_titulo());

                        TextView noticiaDescripcion = (TextView) activity.findViewById(R.id.descGrupo);

                        if (noticiaDescripcion != null) {
                            String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                            // desc = desc.substring(0,200);
                            noticiaDescripcion.setText(Html.fromHtml(desc));
                        }

                    }

                    ImageView imagen_noticias = (ImageView) view.findViewById(R.id.imagenGrupoL);
                    if (imagen_noticias != null)
                        imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                    TextView noticiafecha = (TextView) view.findViewById(R.id.grupofechal);
                    if (noticiafecha != null)
                        noticiafecha.setText(((Lista_Entrada) entrada).get_fecha());

                    TextView noticiatitulo = (TextView) view.findViewById(R.id.grupotitulol);

                    if (noticiatitulo != null)
                        noticiatitulo.setText(((Lista_Entrada) entrada).get_titulo());

                    view.setTag(entrada);


                    //assert imagen_entrada2 != null;
                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenGrupo);
                            TextView fechaGrupo = (TextView) activity.findViewById(R.id.fechaGrupo);
                            TextView titGrupo = (TextView) activity.findViewById(R.id.titGrupo);
                            TextView descGrupo = (TextView) activity.findViewById(R.id.descGrupo);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipal);

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

        return ltsGrupo;
    }

    @Override
    protected void onPostExecute(List_adapted_Grupo result) {

        super.onPostExecute(result);

        lista = (ListView) activity.findViewById(R.id.listGrupo);

        if(result != null && lista != null) {
            lista.setAdapter(result);
            Log.e("Llego", ""+result.getCount());
        }
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelGrupo);

        pBar.setVisibility(View.GONE);
    }

}
