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
import Utitilies.SessionManagement;

/**
 * Created by umunoz on 02/08/2016.
 */
public class FragmentPromocionesAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer3 = true;
    SessionManagement session;

    public FragmentPromocionesAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected List_adapted doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String promos = session.getPromosDetails();

            if(promos == null || promos == "") {

                Log.e("Se obtiene PROMOS","Procesando...");

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

                session.createPromosSession(result11);
            }
            else
            {
                Log.e("Con session Promos",promos);
                result11 = promos;
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
                Log.e("Item Promos" ,  "Error");
            }
            else {

                for (int i = 0; i < responseArray.length(); i++) {
                    Log.e("Response Item: ", responseArray.getJSONObject(i).toString());

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String imagen_detalle = responseArray.getJSONObject(i).get("img_detalle").toString();
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
                    datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,""));
                }
            }


        } catch (JSONException e) {
            Log.e("Error JSONException Promos", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException Promos", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException Promos", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException Promos", e.getMessage());
            e.printStackTrace();
        }



        Log.e("Llego", "al final");
        return new List_adapted(activity, R.layout.entrada_promociones, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

                Log.e ("Entrada Promos", ((Lista_Entrada) entrada).get_titulo());

                if (entrada != null) {

                    ImageView imagen_promos = (ImageView) view.findViewById(R.id.imagenPromo);
                    if (imagen_promos != null)
                        imagen_promos.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                    TextView promosTitulo = (TextView) view.findViewById(R.id.txtTituloPromo);
                    if (promosTitulo != null)
                        promosTitulo.setText(((Lista_Entrada) entrada).get_titulo());

                    view.setTag(entrada);
                    view.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {


                            LinearLayout principal = (LinearLayout)activity.findViewById(R.id.linearPromo);

                            if (principal != null) {
                                principal.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {

                                        Intent i = new Intent(activity, activity_detalle_noticia.class);

                                        Lista_Entrada Entrada = (Lista_Entrada)arg0.getTag();
                                        if(Entrada != null) {
                                            i.putExtra("imagen", Entrada.get_img_detalle());
                                            activity.startActivity(i);
                                        }

                                    }
                                });
                            }

                        }
                    });

                }
            }
        };

    }

    @Override
    protected void onPostExecute(List_adapted result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.lstPromo);
        if(result != null && lista != null) {
            lista.setAdapter(result);
            Log.e("Llego", ""+result.getCount());
        }
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelPromos);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }

}