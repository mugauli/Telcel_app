package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.Lista_Entrada;

/**
 * Created by Mugauli on 20/06/2016.
 */
public class FragmentRevistaAsync extends AsyncTask<ArrayList<String>, Integer, Lista_adaptador> {

    ProgressDialog dialog;
    Activity activity;
    ImageView img;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer = true,primer2 = true;


    public FragmentRevistaAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;

    }

    @Override
    protected Lista_adaptador doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        try {
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
            String result11 = sb.toString();

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
                    String url_podcast = responseArray.getJSONObject(i).get("url_pdf").toString();

                    URL imageUrl = null;
                    imageUrl = new URL(imageHttpAddress + img_previa);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                    conn.disconnect();
                    datos.add(new Lista_Entrada(id,loadedImage, titulo,url_podcast, "", R.drawable.descarga));


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

        Lista_adaptador adaptadorLts = new Lista_adaptador(activity, R.layout.entrada_revista, datos){
            @Override
            public void onEntrada(Object entrada, View view) {

                if (entrada != null) {
                    Log.e("Titulo",((Lista_Entrada) entrada).get_titulo());
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
                    assert imagen_descarga != null;
                    imagen_descarga.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {


                        }
                    });
                }
            }



        };

        return adaptadorLts;
    }

    @Override
    protected void onPostExecute(Lista_adaptador result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.lvRevista);
        lista.setAdapter(result);

        ProgressBar pBar = (ProgressBar)activity.findViewById(R.id.loadingPanelRevista);

        pBar.setVisibility(View.INVISIBLE);


    }





}