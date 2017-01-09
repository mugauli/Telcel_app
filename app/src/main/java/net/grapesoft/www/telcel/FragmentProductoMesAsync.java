package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import Utitilies.FileCache;
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
    public boolean primer3 = true,vacio= false;
    SessionManagement session;
    Context thiscontext;
    //Mes Actual

    private String mesActual = "",tituloActual="", img_miniActual="", textoActual="",idActual="0";
    private Bitmap loadedImageActual= null;
    private ArrayList<String> imagenes_sliderActual = new ArrayList<String>();

    //END Mes Actual

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
            Log.e("JSON Producto del mes" ,  result11);

            if(responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Producto del mes" ,  "Error");
                vacio = true;
            }
            if(responseArray.getJSONObject(0).has("error")) {
                Log.e("Item Producto del mes" ,  "Error");

                vacio = true;

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

                    Log.e("TITULO Producto del mes: ", elementos.getJSONObject(0).get("titulo").toString());
                    ArrayList<String> imagenes_slider = new ArrayList<String>();

                    for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                        imagenes_slider.add(imagenes_slide.getJSONObject(i).get("url_img").toString());
                    }
                    URL imageUrl = null;

                //   imageUrl = new URL(imageHttpAddress + img_previa);
                //   HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                //   try {
                //       conn.connect();
                //       loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
                //       conn.disconnect();
                //   } catch (FileNotFoundException e) {
                //       loadedImage = BitmapFactory.decodeResource(activity.getResources(), R.drawable.noimage);
                //   }
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

                    Log.e("ADD MES--------------------->",mes);
                    datos.add(new Lista_Entrada(mes, id, loadedImage, titulo, img_mini, texto, imagenes_slider));

                    if (mes.equals("actual")) {
                        Log.e("Entrada producto mes ACTUAL llenado----------------------------------1", mes);
                        idActual = id;
                        tituloActual = titulo;
                        loadedImageActual = loadedImage;
                        img_miniActual = img_mini;
                        textoActual = texto;
                        imagenes_sliderActual = imagenes_slider;
                    }
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


        Log.e("Llego Producto Mes", "al final");

        return new List_adapted_Producto_Mes(activity, R.layout.entrada_productos_mes, datos){

            @Override
            public void onEntrada(Object entrada, View view) {

             if(entrada != null) {

                 String mes = ((Lista_Entrada) entrada).get_mes();

                 Log.e("MES--------------------->",mes);


                     ImageView imagen_producto_mes_l = (ImageView) view.findViewById(R.id.imagenProductoMesL);
                     if (imagen_producto_mes_l != null)
                         imagen_producto_mes_l.setImageBitmap(((Lista_Entrada) entrada).get_img_previa());

                     TextView producto_mes = (TextView) view.findViewById(R.id.txtMesProductoMesL);
                     if (producto_mes != null) {
                         String MS = ((Lista_Entrada) entrada).get_mes();
                         if (MS.equals("actual"))
                             producto_mes.setText("Mes actual");
                         else
                             producto_mes.setText(MS);
                     }

                     TextView producto_titulo = (TextView) view.findViewById(R.id.tituloProductoMesL);
                     if (producto_titulo != null)
                         producto_titulo.setText(((Lista_Entrada) entrada).get_titulo());

                   // TextView producto_descripcion = (TextView) view.findViewById(R.id.txtDescripcionProductoMesL);

                   // if (producto_descripcion != null)
                   //     producto_descripcion.setText(Html.fromHtml(((Lista_Entrada) entrada).get_textoDebajo()));

                     view.setTag(entrada);

                     view.setOnClickListener(new View.OnClickListener() {

                         @Override
                         public void onClick(View arg0) {

                             ImageView imagenGrupo = (ImageView) activity.findViewById(R.id.imagenUNT);
                             TextView titGrupo = (TextView) activity.findViewById(R.id.titUN);
                             TextView descGrupo = (TextView) activity.findViewById(R.id.descUN);
                             TextView productoMes = (TextView) activity.findViewById(R.id.txtMesMain);

                             LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalNT);

                             Lista_Entrada Entrada = (Lista_Entrada) arg0.getTag();

                             imagenGrupo.setImageBitmap(Entrada.get_img_previa());
                             String MS = Entrada.get_mes();
                             if (MS.equals("actual"))
                                 productoMes.setText("Este mes");
                             else
                                 productoMes.setText(MS);
                             titGrupo.setText(Entrada.get_titulo());
                             descGrupo.setText(Html.fromHtml(Entrada.get_textoDebajo()));
                             principal.setTag(Entrada);

                         }
                     });

                     //  }
                 }

            }
        };
    }

    @Override
    protected void onPostExecute(List_adapted_Producto_Mes result) {

        super.onPostExecute(result);
        ImageView imgFaltaInfo = (ImageView) activity.findViewById(R.id.imgFaltaInfo);
        LinearLayout linearPrincipalProductos = (LinearLayout) activity.findViewById(R.id.linearPrincipalProductos);
        if(vacio) {
            Log.e("imagens","imagen");
            if(imgFaltaInfo != null)
            imgFaltaInfo.setVisibility(View.VISIBLE);
            if(linearPrincipalProductos != null)
            linearPrincipalProductos.setVisibility(View.GONE);
        }


        lista = (ListView) activity.findViewById(R.id.lstProductosMes);

        if(result != null && lista != null) {
            if(result.getCount() > 0) {
                if (imgFaltaInfo != null)
                    imgFaltaInfo.setVisibility(View.GONE);
                lista.setAdapter(result);
                Log.e("Llego Producto del mes", "" + result.getCount());
            }
            else
            {
                if(imgFaltaInfo != null)
                    imgFaltaInfo.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if(imgFaltaInfo != null)
                imgFaltaInfo.setVisibility(View.VISIBLE);
            Log.e("No llego", "algo paso");
        }
        RelativeLayout pBar = (RelativeLayout)activity.findViewById(R.id.loadingPanelProductoMes);
        if(pBar != null)
            pBar.setVisibility(View.GONE);

        if (!idActual.equals("0")) {
            Log.e("Entrada producto mes ACTUAL----------------------------------1", mesActual);

            TextView productoMes = (TextView) activity.findViewById(R.id.txtMesMain);
            if (productoMes != null)
                productoMes.setText("Este mes");

            ImageView imagen_producto_mes = (ImageView) activity.findViewById(R.id.imagenUNT);
            if (imagen_producto_mes != null) {
                imagen_producto_mes.setImageBitmap(loadedImageActual);
            }

            TextView productoMesTitulo = (TextView) activity.findViewById(R.id.titUN);
            if (productoMesTitulo != null)
                productoMesTitulo.setText(tituloActual);

            TextView productoMesDescripcion = (TextView) activity.findViewById(R.id.descUN);

            if (productoMesDescripcion != null) {
                productoMesDescripcion.setText(Html.fromHtml(textoActual));
            }
            LinearLayout principal = (LinearLayout) activity.findViewById(R.id.linearPrincipalNT);

            principal.setTag(new Lista_Entrada(mesActual, idActual, loadedImageActual, tituloActual, img_miniActual, textoActual, imagenes_sliderActual));

        }
    }

}
