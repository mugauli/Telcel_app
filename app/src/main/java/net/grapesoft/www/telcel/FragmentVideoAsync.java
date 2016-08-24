package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
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
import Utitilies.List_adapted_Video;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 20/06/2016.
 */
public class FragmentVideoAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Video> {

    ProgressDialog dialog;
    Activity activity;
    ImageView img;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer = true,primer2 = true;
    SessionManagement session;



    public FragmentVideoAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;

    }



    @Override
    protected List_adapted_Video doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String video = session.getVideoDetails();

            if(video == null || video == "") {

                Log.e("Se obtiene VIDEO","Procesando...");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(IP + params[0].get(1));
                nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                nameValuePair.add(new BasicNameValuePair("reg", params[0].get(3)));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));

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

                session.createVideoSession(result11);
            }
            else
            {
                Log.e("Con session VIDEO",video);
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
                //Log.e("Response: ", "JSON");
                if(result11.contains("["))
                    responseArray = new JSONArray(result11);
                else
                    responseArray = new JSONArray("[" + result11 + "]");
            }

            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Podcast" ,  "Error");
            }
            else {

                for (int i = 0; i < responseArray.length(); i++) {


                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String url_video = responseArray.getJSONObject(i).get("url_video").toString();
                    String duracion = responseArray.getJSONObject(i).get("duracion").toString();

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
                    datos.add(new Lista_Entrada(id,loadedImage, titulo,url_video, duracion,responseArray));

                }
            }


        } catch (JSONException e) {
            Log.e("Error Async 0 Video", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error Async 1 Video", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error Async 2 Video", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error Async 3 Video", e.getMessage());
            e.printStackTrace();
        }

        List_adapted_Video adaptadorLts = new List_adapted_Video(activity, R.layout.entrada_video, datos){

            @Override
            public void onEntrada(Object entrada, View view) {
              //  Log.e ("Entrada Video", ((Lista_Entrada) entrada).get_id());



                if (entrada != null) {

                    if(primer){
                        primer = false;

                        ImageView imagenVideo = (ImageView) activity.findViewById(R.id.video);
                        ImageView imagenPlay = (ImageView) activity.findViewById(R.id.play);
                        LinearLayout videotodo = (LinearLayout)activity.findViewById(R.id.videotodo);

                        //ImageView imagenDescarga = (ImageView) activity.findViewById(R.id.descarga);
                        TextView txtTiempo = (TextView) activity.findViewById(R.id.txtTiempo);
                        TextView txtTitulo = (TextView) activity.findViewById(R.id.txtTitulo);
                        imagenVideo.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                       // imagenPlay.setTag(((Lista_Entrada) entrada).get_id());
                        videotodo.setTag(((Lista_Entrada) entrada).get_id());

                        //imagenDescarga.setTag(((Lista_Entrada) entrada).get_url());
                        txtTiempo.setText(((Lista_Entrada) entrada).get_duracion());
                        txtTitulo.setText(((Lista_Entrada) entrada).get_titulo());
                    }

                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.videotitulo);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_titulo());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.videoduracion);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_duracion());

                    TextView idVideo = (TextView) view.findViewById(R.id.idVideo);

                    if (idVideo != null)
                        idVideo.setText(((Lista_Entrada) entrada).get_id());

                    final TextView url_Video = (TextView) view.findViewById(R.id.url_Video);

                    if (url_Video != null)
                        url_Video.setText(((Lista_Entrada) entrada).get_url());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagevideo);
                    if (imagen_entrada != null) {
                        imagen_entrada.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                        imagen_entrada.setTag(((Lista_Entrada) entrada).get_img_previa());
                    }

                    view.setTag(entrada);

                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            ImageView imagenVideo = (ImageView) activity.findViewById(R.id.video);
                            ImageView imagenPlay = (ImageView) activity.findViewById(R.id.play);
                            //ImageView imagenDescarga = (ImageView) activity.findViewById(R.id.descarga);
                            TextView txtTiempo = (TextView) activity.findViewById(R.id.txtTiempo);
                            TextView txtTitulo = (TextView) activity.findViewById(R.id.txtTitulo);
                            LinearLayout videotodo = (LinearLayout)activity.findViewById(R.id.videotodo);

                            Lista_Entrada Entrada = (Lista_Entrada)arg0.getTag();

                            String id_video = ((TextView)arg0.findViewById(R.id.idVideo)).getText().toString();

                            imagenVideo.setImageBitmap(Entrada.get_img_previa());
                            videotodo.setTag(id_video);
                            //imagenPlay.setTag(Entrada.get_id());
                           //imagenDescarga.setTag(Entrada.get_url());
                            txtTiempo.setText(Entrada.get_duracion());
                            txtTitulo.setText(Entrada.get_titulo());

                        }
                    });

                }
            }



        };

        return adaptadorLts;
    }

    @Override
    protected void onPostExecute(List_adapted_Video result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.listvideo);
        if (result != null && lista != null)
            lista.setAdapter(result);

        ProgressBar pBar = (ProgressBar) activity.findViewById(R.id.loadingPanelVideo);
        if(pBar != null)
        pBar.setVisibility(View.INVISIBLE);
    }
}