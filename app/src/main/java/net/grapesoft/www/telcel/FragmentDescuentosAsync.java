package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.LinkedList;
import java.util.List;

import Utitilies.Campos;
import Utitilies.DescElement;
import Utitilies.List_adapted;
import Utitilies.List_adapted_Noticias;
import Utitilies.List_adapted_Revista;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 24/06/2016.
 */
public class FragmentDescuentosAsync extends AsyncTask<ArrayList<String>, Integer, ArrayList<Lista_Entrada>> {

    ProgressDialog dialog;
    Activity activity;
    private ListView lista;
    JSONArray responseArray;
    private String imageHttpAddress = "";
    private Bitmap loadedImage;
    public String IP = "",tokenCTE = "";
    public boolean primer3 = true;
    SessionManagement session;
    private String url="";

    public FragmentDescuentosAsync(Activity activity) {
        IP = activity.getString(R.string.URL);
        tokenCTE = activity.getString(R.string.tokenXM);
        this.activity = activity;
    }

    @Override
    protected ArrayList<Lista_Entrada> doInBackground(ArrayList<String>... params) {

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        imageHttpAddress = activity.getText(R.string.URL_media).toString();

        session = new SessionManagement(activity.getApplicationContext());
        String result11 = "";
        try {

            String descuentosDetails = session.getDescuentosDetails();

            if(descuentosDetails == null || descuentosDetails == "") {

                Log.e("Se obtiene DESCUENTOS","Procesando...");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(IP + params[0].get(1));
                nameValuePair.add(new BasicNameValuePair("token", params[0].get(2)));
                nameValuePair.add(new BasicNameValuePair("idUsuario", params[0].get(3)));
                nameValuePair.add(new BasicNameValuePair("reg", params[0].get(4)));
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


                session.createDescuentosSession(result11);

            }
            else
            {
                Log.e("Con session DESCUENTOS",descuentosDetails);
                result11 = descuentosDetails;
            }

            Log.e("Response: ", result11);

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
                Log.e("Item Descuentos" ,  "Error");
            }else
            if(responseArray.getJSONObject(0).has("error")) {
                Log.e("Item Descuentos" ,  "Error");
            }
            else {

                for (int ii = 0; ii < responseArray.length(); ii++) {

                    String titulo = responseArray.getJSONObject(ii).get("titulo").toString();
                    JSONArray prod = responseArray.getJSONObject(ii).getJSONArray("pdfs");
                    ArrayList<DescElement> data = new ArrayList<DescElement>();

                    datos.add(new Lista_Entrada(0,"SELECCIONAR...",data));
                    for (int i = 1; i < prod.length()+1; i++) {


                        Log.e("Response Item: ", prod.getJSONObject(i).toString());

                        String tipo = prod.getJSONObject(i).get("tipo").toString();
                        //String img_previa = prod.getJSONObject(i).get("img_previa").toString();
                        String url_pdf = prod.getJSONObject(i).get("url_pdf").toString();
                        data.add(new DescElement(i,"",tipo.toUpperCase(),url_pdf));

                        datos.add(new Lista_Entrada(ii,titulo.toUpperCase(),data));
                    }
                }
            }


        } catch (JSONException e) {
            Log.e("Error JSONException Descuentos", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e("Error UnsupportedEncodingException Descuentos", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.e("Error ClientProtocolException Descuentos", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error IOException Descuentos", e.getMessage());
            e.printStackTrace();
        }


        Log.e("Llego", "al final");


      return datos;

    }

    @Override
    protected void onPostExecute(ArrayList<Lista_Entrada> result) {
        super.onPostExecute(result);

        final Spinner pdfCampos = (Spinner) activity.findViewById(R.id.spnCamposPromos);
        if (pdfCampos != null)
            if (result != null) {
                pdfCampos.setTag(result.get(0).get_pdf());

                final LinkedList pdfs = new LinkedList();
                for (DescElement obj : result.get(0).get_pdf()) {

                    pdfs.add(obj);

                }
                ArrayAdapter spinner_adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, pdfs);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pdfCampos.setAdapter(spinner_adapter);
                pdfCampos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ArrayList<DescElement> a = (ArrayList<DescElement>)pdfCampos.getTag();
                        //Log.e("url_pdf", pdfCampos.getTag().toString());
                        //pdfCampos.getTag().toString()
                        if(id != 0)
                        for( int i = 0 ; i < a.size() ; i++ ){
                            if(id == a.get(i).get_idDesc()){
                               abrirPDF(a.get(i).get_url_pdfDesc());
                            }
                        }


                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

            }





        RelativeLayout pBar = (RelativeLayout) activity.findViewById(R.id.loadingPanelNoticias);
        if (pBar != null)
            pBar.setVisibility(View.GONE);
    }

    public void abrirPDF(String URL){


        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        Uri data = Uri.parse(URL);
        intent.setDataAndType(data, "application/pdf");
        activity.startActivity(intent);

    }
}
