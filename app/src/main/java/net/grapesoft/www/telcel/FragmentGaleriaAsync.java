package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import Utitilies.List_adapted;
import Utitilies.List_adapted_galeria;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by umunoz on 11/07/2016.
 */
public class FragmentGaleriaAsync   extends AsyncTask<ArrayList<String>, Integer, List_adapted_galeria> {

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

    public FragmentGaleriaAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        imageHttpAddress = activity.getText(R.string.URL_media).toString();
        this.activity = activity;
    }

    @Override
    protected List_adapted_galeria doInBackground(ArrayList<String>... params){

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();


        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String galeriaDetails = session.getGaleriaDetails();

            if(galeriaDetails == null || galeriaDetails == "") {

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

                session.createGaleriaSession(result11);
            }
            else
            {
                Log.e("Con session Galeria",galeriaDetails);
                result11 = galeriaDetails;
            }

            if(result11.equals("true"+"\n")) {
                // Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            }else if(result11.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else
            {
                Log.e("JSON Galeria",result11.toString());
                if(result11.contains("["))
                    responseArray = new JSONArray(result11);
                else
                    responseArray = new JSONArray("[" + result11 + "]");
            }
            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Galeria" ,  "Error");
            }
            else {
                for (int i = 0; i < responseArray.length(); i++) {

                    String id = responseArray.getJSONObject(i).get("id").toString();
                    String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                    String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                    String imagen_detalle = responseArray.getJSONObject(i).get("url").toString();
                    String texto = responseArray.getJSONObject(i).get("texto").toString();

                    JSONArray imagenes_slide = responseArray.getJSONObject(0).getJSONArray("imagenes_slide");

                    Dictionary<String,String> imagenes_slider = new Dictionary<String, String>() {
                        @Override
                        public Enumeration<String> elements() {
                            return null;
                        }

                        @Override
                        public String get(Object key) {
                            return null;
                        }

                        @Override
                        public boolean isEmpty() {
                            return false;
                        }

                        @Override
                        public Enumeration<String> keys() {
                            return null;
                        }

                        @Override
                        public String put(String key, String value) {
                            return null;
                        }

                        @Override
                        public String remove(Object key) {
                            return null;
                        }

                        @Override
                        public int size() {
                            return 0;
                        }
                    };

                    for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                        imagenes_slider.put(imagenes_slide.getJSONObject(ii).get("id").toString(),imagenes_slide.getJSONObject(i).get("url_img").toString());
                    }

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
                    datos.add(new Lista_Entrada(id,loadedImage, titulo,imagen_detalle,texto,imagenes_slider));

                }
            }
        } catch (JSONException e) {
            Log.e("Error async Galeria", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error async 1 Galeria", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error async 2 Galeria", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error async 3 Galeria", e.getMessage());
            e.printStackTrace();
        }

        List_adapted_galeria adaptadorLts = new List_adapted_galeria(activity, R.layout.entrada_galeria, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    if (primer) {
                        primer = false;

                        ImageView imagen_galeria = (ImageView) activity.findViewById(R.id.imagenUNT);
                        if (imagen_galeria != null) {
                            imagen_galeria.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                        }

                        TextView galeriaFecha = (TextView) activity.findViewById(R.id.fechaUN);
                        if (galeriaFecha != null)
                            galeriaFecha.setText(((Lista_Entrada) entrada).get_fecha());

                        TextView galeriaTitulo = (TextView) activity.findViewById(R.id.titUN);

                        if (galeriaTitulo != null)
                            galeriaTitulo.setText(((Lista_Entrada) entrada).get_titulo());

                        TextView galeriaDescripcion = (TextView) activity.findViewById(R.id.descUN);
                        if (galeriaDescripcion != null) {
                            String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                            galeriaDescripcion.setText(Html.fromHtml(desc));
                        }
                        LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalNT);
                        principal.setTag(entrada);
                    }

                    ImageView campanaImagen = (ImageView) view.findViewById(R.id.imagenCaleriaL);

                    if (campanaImagen != null)
                        campanaImagen.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                    TextView campanaFecha = (TextView) view.findViewById(R.id.fechaGaleriaL);
                    if (campanaFecha != null)
                        campanaFecha.setText(((Lista_Entrada) entrada).get_fecha());

                    TextView campanaTitulo = (TextView) view.findViewById(R.id.tituloGaleriaL);

                    if (campanaTitulo != null)
                        campanaTitulo.setText(((Lista_Entrada) entrada).get_titulo());

                    view.setTag(entrada);


                    //assert imagen_entrada2 != null;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenUNT);
                            TextView fechaGrupo = (TextView) activity.findViewById(R.id.fechaUN);
                            TextView titGrupo = (TextView) activity.findViewById(R.id.titUN);
                            TextView descGrupo = (TextView) activity.findViewById(R.id.descUN);
                            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalNT);

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
    protected void onPostExecute(List_adapted_galeria result) {

        super.onPostExecute(result);
        lista = (ListView) activity.findViewById(R.id.listGaleria);
        if(result != null && lista != null)
            lista.setAdapter(result);
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelGaleria);
        if(pBar != null)
            pBar.setVisibility(View.GONE);

    }
}
