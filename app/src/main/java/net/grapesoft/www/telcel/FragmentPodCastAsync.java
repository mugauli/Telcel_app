package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.FileCache;
import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 19/06/2016.
 */
public class FragmentPodCastAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted> {

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

    public FragmentPodCastAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected List_adapted doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String podcast = session.getPodcastDetails();

            if(podcast == null || podcast == "") {


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


                session.createPodcastSession(result11);
            }
            else
            {
                Log.e("Con session PODCAST",podcast);
                result11 = podcast;
            }

                if(result11.equals("true"+"\n")) {
                    // Log.e("Response: ", "true Int");
                    responseArray = new JSONArray("[{'resp':'true'}]");
                }else if(result11.equals("false"+"\n")) {
                    //Log.e("Response: ", "false int");
                    responseArray = new JSONArray("[{'resp':'false'}]");
                } else
                {
                    Log.e("Response: JSON Podcast", result11.toString());
                    if(result11.contains("["))
                        responseArray = new JSONArray(result11);
                    else
                        responseArray = new JSONArray("[" + result11 + "]");
                }


            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Podcast" ,  "Error");
            }else  if(responseArray.getJSONObject(0).has("error")) {
                Log.e("Item Podcast" ,  "Error");
            } else {

                for (int i = 0; i < responseArray.length(); i++) {


                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String url_podcast = responseArray.getJSONObject(i).get("url_podcast").toString();
                    String duracion = responseArray.getJSONObject(i).get("duracion").toString();
                    String fecha = responseArray.getJSONObject(i).get("fecha").toString();
                    URL imageUrl = null;

                //    imageUrl = new URL(imageHttpAddress + img_previa);
                //    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                //    try {
                //        conn.connect();
                //        loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                //        conn.disconnect();
                //    }
                //    catch (FileNotFoundException e)
                //    {
                //        loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                //    }
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
                    datos.add(new Lista_Entrada(fecha,id,loadedImage, titulo,url_podcast, duracion, R.drawable.play));


                }
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
        }

        List_adapted adaptadorLts = new List_adapted(activity, R.layout.entrada_podcast, datos){
            @Override
            public void onEntrada(Object entrada, View view) {


                if (entrada != null) {
                    // Applying font
                    if(primer) {
                        primer = false;
                        //view.setBackgroundResource(R.drawable.border_shadow);
                   //     view.setBackgroundColor(Color.WHITE);

                    }
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_titulo());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);


                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_duracion());

                   TextView texto_inferior_entrada2 = (TextView) view.findViewById(R.id.textView_inferior2);
                    if (texto_inferior_entrada2 != null)
                        texto_inferior_entrada2.setText(((Lista_Entrada) entrada).get_seccion());

                    TextView idPodcast = (TextView) view.findViewById(R.id.idPodcast);
                    if (idPodcast != null)
                        idPodcast.setText(((Lista_Entrada) entrada).get_id());

                    TextView url_Podcast = (TextView) view.findViewById(R.id.url_Podcast);

                    if (url_Podcast != null)
                        url_Podcast.setText(((Lista_Entrada) entrada).get_url());

                   ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView5);
                    if (imagen_entrada != null) {

                        imagen_entrada.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                    }
                    ImageView imagen_entrada2 = (ImageView) view.findViewById(R.id.imageView6);
                    if (imagen_entrada2 != null) {
                        imagen_entrada2.setImageResource(((Lista_Entrada) entrada).get_idImagen2());
                        imagen_entrada2.setTag(((Lista_Entrada)entrada).get_url());
                    }


                    //assert imagen_entrada2 != null;
                    imagen_entrada2.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            String URL = arg0.getTag().toString();
                            audioPlayer(URL);
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
        lista = (ListView) activity.findViewById(R.id.podcast);
        if(result != null && lista != null)
        lista.setAdapter(result);

        ProgressBar pBar = (ProgressBar)activity.findViewById(R.id.loadingPanel);
        if(pBar != null)
        pBar.setVisibility(View.INVISIBLE);


    }


    public void audioPlayer(String path){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
          // mp.setDataSource(path + File.separator + fileName);
          // mp.prepare();
          // mp.start();

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

            Log.e("URL audio", path);
            Uri data = Uri.parse(path.replace(" ","%20"));
            intent.setDataAndType(data, "audio/mp3");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
