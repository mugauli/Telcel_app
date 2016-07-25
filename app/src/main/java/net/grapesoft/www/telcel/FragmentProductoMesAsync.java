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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Utitilies.List_adapted_Producto_Mes;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 24/06/2016.
 */
public class FragmentProductoMesAsync extends AsyncTask<ArrayList<String>, Integer, List_adapted_Producto_Mes> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer3 = true;
    SessionManagement session;

    public FragmentProductoMesAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected List_adapted_Producto_Mes doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String noticias = session.getMesProductosDetails();

            if(noticias == null || noticias == "") {

                Log.e("Se obtiene Producto del mes","Procesando...");

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


                session.createMesProductosSession(result11);
            }
            else
            {
                Log.e("Con session Producto del mes",noticias);
                result11 = noticias;
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
                Log.e("Item Producto del mes" ,  "Error");
            }
            else {
                JSONObject prod = responseArray.getJSONObject(0);

                JSONArray productos = prod.getJSONArray("productos");
                for (int i = 0; i < productos.length(); i++) {
                    Log.e("Response Item Prodcuto del mes: ", productos.getJSONObject(i).toString());

                    String mes = productos.getJSONObject(i).get("mes").toString();
                    JSONArray elementos = productos.getJSONObject(i).getJSONArray("elementos");

                    String id = elementos.getJSONObject(0).get("id").toString();
                    String titulo = elementos.getJSONObject(0).get("titulo").toString();
                    String img_previa = elementos.getJSONObject(0).get("img_previa").toString();
                    String img_mini = elementos.getJSONObject(0).get("img_mini").toString();
                    String texto = elementos.getJSONObject(0).get("texto").toString();
                    JSONArray imagenes_slide = elementos.getJSONObject(0).getJSONArray("imagenes_slide");

                    Log.e("TITULO Prodcuto del mes: ", elementos.getJSONObject(0).get("titulo").toString());
                    ArrayList<String> imagenes_slider = new ArrayList<String>();

                    for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                        imagenes_slider.add(imagenes_slide.getJSONObject(i).get("url_img").toString());
                    }
                    URL imageUrl = null;
                    imageUrl = new URL(imageHttpAddress + img_previa);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                    conn.disconnect();
                    datos.add(new Lista_Entrada(mes,id,loadedImage, titulo,img_mini,texto,imagenes_slider));
                }
            }


        } catch (JSONException e) {
            Log.e("Error JSONException ProductoMes", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException ProductoMes", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException ProductoMes", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException ProductoMes", e.getMessage());
            e.printStackTrace();
        }


        Log.e("Llego", "al final");

        return new List_adapted_Producto_Mes(activity, R.layout.entrada_productos_mes, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

             if(entrada != null) {

              if(primer3) {
                  primer3 = false;

                  Log.e ("Entrada producto mes titulo primero", ((Lista_Entrada) entrada).get_titulo());

                  ImageView imagen_noticias = (ImageView) activity.findViewById(R.id.imagenUNT);
                  if (imagen_noticias != null) {
                      Log.e("imagen", "principal");
                      imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());
                  }

                  TextView noticiafecha = (TextView) activity.findViewById(R.id.fechaUN);
                  if (noticiafecha != null)
                      noticiafecha.setText(((Lista_Entrada) entrada).get_fecha());

                  TextView noticiatitulo = (TextView) activity.findViewById(R.id.titUN);

                  if (noticiatitulo != null)
                      noticiatitulo.setText(((Lista_Entrada) entrada).get_titulo());

                  TextView noticiaDescripcion = (TextView) activity.findViewById(R.id.descUN);

                  if (noticiaDescripcion != null) {
                      String desc = ((Lista_Entrada) entrada).get_textoDebajo();
                      noticiaDescripcion.setText(Html.fromHtml(desc));
                  }
                  LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalNT);

                  principal.setTag(entrada);

              }

                    ImageView imagen_noticias = (ImageView) view.findViewById(R.id.imagenCaleriaL);
                    if (imagen_noticias != null)
                        imagen_noticias.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                    TextView noticiafecha = (TextView) view.findViewById(R.id.fechaProductoMesL);
                    if (noticiafecha != null)
                        noticiafecha.setText(((Lista_Entrada) entrada).get_fecha());

                    TextView noticiatitulo = (TextView) view.findViewById(R.id.tituloProductoMesL);

                    if (noticiatitulo != null)
                        noticiatitulo.setText(((Lista_Entrada) entrada).get_titulo());

                    view.setTag(entrada);

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
    }

    @Override
    protected void onPostExecute(List_adapted_Producto_Mes result) {

        super.onPostExecute(result);

        lista = (ListView) activity.findViewById(R.id.lstProductosMes);
        if(result != null && lista != null) {
            lista.setAdapter(result);
            Log.e("Llego", ""+result.getCount());
        }
        else
        {
            Log.e("No llego", "algo paso");
        }
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelProductoMes);
        if(pBar != null)
            pBar.setVisibility(View.GONE);
    }

}
