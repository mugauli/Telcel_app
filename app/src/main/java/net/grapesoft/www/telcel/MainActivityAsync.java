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
import org.json.JSONObject;

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
import Utitilies.List_adapted_Producto_Mes;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 25/07/2016.
 */
public class MainActivityAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer3 = true;
    SessionManagement session;

    private String idP = "";
    private String tituloP = "";
    private String img_previaP ="";
    private String url_podcastP ="";
    private String duracionP ="";
    private Bitmap loadedImageP = null;

    public MainActivityAsync(Activity activity) {
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

            String home = session.getHomeDetails();

            if(home == null || home == "") {

                Log.e("Se obtiene Home","Procesando...");

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
                session.createHomeSession(result11);
            }
            else
            {
           //     Log.e("Con session Home",home);
                result11 = home;
            }

            Log.e("Response: ", result11);

            if(result11.equals("true"+"\n")) {
                Log.e("Response: ", "true Int");
                responseArray = new JSONArray("[{'resp':'true'}]");
            }else if(result11.equals("false"+"\n")) {
                Log.e("Response: ", "false int");
                responseArray = new JSONArray("[{'resp':'false'}]");
            } else
            {
                Log.e("Response: ", "JSON");
                if(result11 != "") {
                    if (result11.substring(0, 2).contains("["))
                        responseArray = new JSONArray(result11);
                    else
                        responseArray = new JSONArray("[" + result11 + "]");
                }
            }

            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Home" ,  "Error");
            }
            if(responseArray.getJSONObject(0).has("error")) {
                Log.e("Item Home" ,  "Error");
            }
            else {
                JSONObject prod1 = responseArray.getJSONObject(0);

               // JSONObject prod = prod1.getJSONObject("0");

                JSONArray homeArray = prod1.getJSONArray("home");

                for (int i = 0; i < homeArray.length(); i++) {
                   //Log.e("Response Item Home: ", homeArray.getJSONObject(i).toString());

                    String seccion = homeArray.getJSONObject(i).get("seccion").toString();
                    JSONArray elementos = homeArray.getJSONObject(i).getJSONArray("elementos");


                    if (seccion.equals("podcast")) {

                        idP = elementos.getJSONObject(0).get("id").toString();
                        tituloP = elementos.getJSONObject(0).get("titulo").toString();
                        img_previaP = elementos.getJSONObject(0).get("img_previa").toString();
                        url_podcastP = elementos.getJSONObject(0).get("url_podcast").toString();
                        duracionP = elementos.getJSONObject(0).get("duracion").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previaP);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImageP = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImageP = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }


                       // datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, url_podcast, duracion, R.drawable.play));

                    } else if (seccion.equals("promociones")) {
                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String img_detalle = elementos.getJSONObject(0).get("img_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }

                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, img_detalle, texto));
                    } else if (seccion.equals("revista")) {
                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String url_pdf = elementos.getJSONObject(0).get("url_pdf").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, url_pdf, "", R.drawable.descarga));
                    } else if (seccion.equals("SVA")) {

                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String img_mini = elementos.getJSONObject(0).get("img_mini").toString();
                        String img_detalle = elementos.getJSONObject(0).get("img_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();
                        String fecha = elementos.getJSONObject(0).get("fecha").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();

                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }

                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, img_mini, img_detalle, texto, fecha));
                    } else if (seccion.equals("carso")) {
                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String imagen_detalle = elementos.getJSONObject(0).get("imagen_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();
                        String fecha = elementos.getJSONObject(0).get("fecha").toString();


                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, imagen_detalle, texto, fecha));

                    } else if (seccion.equals("internas")) {
                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String imagen_detalle = elementos.getJSONObject(0).get("imagen_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();
                        String tipo = elementos.getJSONObject(0).get("tipo").toString();
                        String contenido = elementos.getJSONObject(0).get("contenido").toString();
                        String fecha = elementos.getJSONObject(0).get("fecha").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, imagen_detalle, texto, fecha));
                    } else if (seccion.equals("publicitarias")) {

                        Log.e("Response Item Seccion Home: ", seccion);
                        Log.e("Response Item Seccion Home: ", seccion);
                        Log.e("Response Item Seccion Home: ", seccion);
                        Log.e("Response Item Seccion Home: ", seccion);

                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String url_video = elementos.getJSONObject(0).get("url_video").toString();
                        String duracion = elementos.getJSONObject(0).get("duracion").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion,id, loadedImage, titulo, url_video, duracion, responseArray));
                    } else if (seccion.equals("noticias")) {
                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String imagen_detalle = elementos.getJSONObject(0).get("imagen_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();
                        String fecha = elementos.getJSONObject(0).get("fecha").toString();

                        if (fecha == null || fecha == "") {
                            fecha = "01-01-2016";
                        }

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, imagen_detalle, texto, fecha));

                    } else if (seccion.equals("comunicados")) {

                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String imagen_detalle = elementos.getJSONObject(0).get("imagen_detalle").toString();
                        String texto = elementos.getJSONObject(0).get("texto").toString();
                        String fecha = elementos.getJSONObject(0).get("fecha").toString();

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, imagen_detalle, texto, fecha));
                    } else if (seccion.equals("video")) {


                        String id = elementos.getJSONObject(0).get("id").toString();
                        String titulo = elementos.getJSONObject(0).get("titulo").toString();
                        String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                        String url_video = elementos.getJSONObject(0).get("url_video").toString();
                        String duracion = elementos.getJSONObject(0).get("duracion").toString();

                        Log.e("Img_previa Home",img_previa);

                        URL imageUrl = null;
                        imageUrl = new URL(imageHttpAddress + img_previa);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        try {
                            conn.connect();
                            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                            conn.disconnect();
                        } catch (FileNotFoundException e) {
                            loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                        }
                        datos.add(new Lista_Entrada(seccion, id, loadedImage, titulo, url_video, duracion, responseArray));
                    }

                }
            }
        } catch (JSONException e) {
            Log.e("Error JSONException Home", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException Home", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException Home", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException Home", e.getMessage());
            e.printStackTrace();
        }
        List_adapted a = new List_adapted(activity, R.layout.entrada_bienvenidos, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

                if(entrada != null) {

                    String seccion = ((Lista_Entrada)entrada).get_seccion();

                    Log.e("Entrada Home", seccion);


               //  if (seccion.equals("podcast")) {

               //      // Log.e ("Entrada Home Podcast", ((Lista_Entrada) entrada).get_titulo());

               //      ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenPodcast);
               //      if (imagen_noticias != null) {
               //          imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
               //      }

               //      TextView noticiafecha = (TextView) activity.findViewById(R.id.textView_superior);
               //      if (noticiafecha != null)
               //          noticiafecha.setText(((Lista_Entrada) entrada).get_titulo());

               //      TextView noticiatitulo = (TextView) activity.findViewById(R.id.textView_inferior);

               //      if (noticiatitulo != null)
               //          noticiatitulo.setText(((Lista_Entrada) entrada).get_duracion());

               //      ImageView imagen_entrada2 = (ImageView) view.findViewById(R.id.descargarPodcast);
               //      if (imagen_entrada2 != null) {
               //          imagen_entrada2.setImageResource(((Lista_Entrada) entrada).get_idImagen2());
               //          imagen_entrada2.setTag(((Lista_Entrada)entrada).get_url());
               //      }

               //      LinearLayout principal = (LinearLayout) activity.findViewById(R.id.lnPodcast);
               //      principal.setTag(entrada);

               //      principal.setOnClickListener(new View.OnClickListener() {

               //          @Override
               //          public void onClick(View arg0) {

               //              Intent i = new Intent(activity, ComunicacionInternaActivity.class);
               //              i.putExtra("direccion","1");
               //              activity.startActivity(i);

               //          }
               //      });

               //      view.setVisibility(View.GONE);

               //  } else
                    if (seccion.equals("promociones")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);

                        if (homeTitulo != null)
                            homeTitulo.setText("Prestaciones > Promociones");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);

                    view.setOnClickListener(new View.OnClickListener() {

                       @Override
                       public void onClick(View arg0) {

                           Intent i = new Intent(activity, PromocionesActivity.class);
                           i.putExtra("direccion","1");
                           activity.startActivity(i);

                        }
                    });




                    } else if (seccion.equals("revista")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);

                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Revista");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);

                        view.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","4");
                                activity.startActivity(i);

                            }
                        });

                    } else if (seccion.equals("SVA")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);

                        if (homeTitulo != null)
                            homeTitulo.setText("Productos y Servicios > SVA");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);

                        view.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                Intent i = new Intent(activity, ProductosActivity.class);
                                i.putExtra("direccion","4");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("carso")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);

                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Grupo Carso");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);

                        view.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","8");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("internas")) {
                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);
                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Campañas Internas");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","2");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("publicitarias")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);
                        if (homeTitulo != null)
                            homeTitulo.setText("Productos y Servicios > Campaña Publicitaria");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent i = new Intent(activity, ProductosActivity.class);
                                i.putExtra("direccion","4");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("noticias")) {
                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);
                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Noticias");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","3");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("comunicados")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);
                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Comunicados");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","1");
                                activity.startActivity(i);
                            }
                        });

                    } else if (seccion.equals("video")) {

                        TextView homeTitulo = (TextView) view.findViewById(R.id.txtTitulo);
                        if (homeTitulo != null)
                            homeTitulo.setText("Comunicación Interna > Videos");
                        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                        Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                        homeTitulo.setTypeface(tfl);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                                i.putExtra("direccion","5");
                                activity.startActivity(i);
                            }
                        });
                    }
                    if(!seccion.equals("podcast")) {

                        ImageView imagen_home = (ImageView) view.findViewById(R.id.imagenHome);
                        if (imagen_home != null)
                            imagen_home.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                           // imagen_home.setImageResource(R.drawable.iconpodcast);

                        if(seccion.equals("video")||seccion.equals("publicitarias")) {

                            TextView homeDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
                            if (homeDescripcion != null)
                                homeDescripcion.setText(((Lista_Entrada) entrada).get_duracion());
                            Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                            Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                            homeDescripcion.setTypeface(tf);
                        }else if(seccion.equals("revista")) {

                            TextView homeDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
                            if (homeDescripcion != null) {
                                homeDescripcion.setText(((Lista_Entrada)entrada).get_titulo());
                                Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                                Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                                homeDescripcion.setTypeface(tf);
                              //  homeDescripcion.setVisibility(View.GONE);
                            }
                        }else
                        {

                            TextView homeDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
                            if (homeDescripcion != null)
                                homeDescripcion.setText(Html.fromHtml(((Lista_Entrada) entrada).get_titulo()));
                            Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/media.otf");
                            Typeface tfl = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                            homeDescripcion.setTypeface(tf);
                        }
                    }
                }
            }
        };

        Log.e("tamaño", ""+a.getCount());
        return a;
    }

    @Override
    protected void onPostExecute(List_adapted result) {

        super.onPostExecute(result);

        lista = (ListView) activity.findViewById(R.id.lstHome);
        if(result != null && lista != null) {
            lista.setAdapter(result);
            Log.e("Llego", ""+result.getCount());
        }
        else
        {
            Log.e("No llego", "algo paso");
        }

        ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenPodcast);
        if (imagen_noticias != null) {
            //imagen_noticias.setImageBitmap(loadedImageP);
            imagen_noticias.setImageResource(R.drawable.iconpodcast);
        }

        TextView noticiafecha = (TextView) activity.findViewById(R.id.textView_superior);
        if (noticiafecha != null)
            noticiafecha.setText(tituloP);

        TextView noticiatitulo = (TextView) activity.findViewById(R.id.textView_inferior);

        if (noticiatitulo != null)
            noticiatitulo.setText(duracionP);

        LinearLayout principal = (LinearLayout) activity.findViewById(R.id.lnPodcast);
        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(activity, ComunicacionInternaActivity.class);
                i.putExtra("direccion","6");
                activity.startActivity(i);

            }
        });


        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelHome);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }

}
