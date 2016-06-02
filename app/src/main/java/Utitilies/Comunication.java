package Utitilies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mugauli on 01/06/2016.
 */
public class Comunication extends AsyncTask<ArrayList<String>, Void, JSONArray> {

    public String Post(String dato, String pass, String token, String campo) {

        HttpClient httpClient = new DefaultHttpClient();
        //Create an object of HttpPost

        HttpPost httpPost = new HttpPost("http://internetencaja.com.mx/telcel/WServices/GetLogin.php");
        //Add POST parameters

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("dato", "ANDROID"));
        nameValuePair.add(new BasicNameValuePair("password", "09721ab88e0a552087391be1ef0c6826"));
        nameValuePair.add(new BasicNameValuePair("token", "67d6b32e8d96b8542feda3df334c04f5"));
        nameValuePair.add(new BasicNameValuePair("campo", "E"));
        //Encode POST data

        //We need to encode our data into valid URL format before making HTTP request.

        //Encoding POST data
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.e("Http Post Response:", response.toString());
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

        return "a";
    }


    ProgressDialog progressDialog;

    public static JSONArray arreglo;


    public Comunication (Context cxt){
        progressDialog = new ProgressDialog(cxt);
    }

    @Override
    public void onPreExecute(){
        super.onPreExecute();
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Espere Por Favor . . . ");
        progressDialog.show();

    }

    @Override
    public JSONArray doInBackground(ArrayList<String>... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            ArrayList<String> paramsPassed = params[0];

            String IP = "http://internetencaja.com.mx/telcel/WServices/GetLogin.php";
            String dato = paramsPassed.get(0);
            String password = paramsPassed.get(1);
            String tokenCTE = paramsPassed.get(2);
            String campo = paramsPassed.get(3);

            //Log.e("Datos",dato);
            //Log.e("tokenCTE",tokenCTE);
            //Log.e("Campo",campo);
            //Log.e("PASS",password);
            //Log.e("PASS","09721ab88e0a552087391be1ef0c6826");

            //HttpGet request = new HttpGet(IP +"?token="+tokenCTE+"&campo="+campo+"&dato="+dato+"&password=" + password);
            // replace with your url
            //Log.e("Response of GET request","Inicia");
            //HttpResponse responseGET;
            //responseGET = httpclient.execute(request);

            HttpPost httppost = new HttpPost(IP);

            //Add POST parameters

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

            nameValuePair.add(new BasicNameValuePair("dato", dato));
            nameValuePair.add(new BasicNameValuePair("password", password));
            nameValuePair.add(new BasicNameValuePair("token", tokenCTE));
            nameValuePair.add(new BasicNameValuePair("campo", campo));

            //nameValuePair.add(new BasicNameValuePair("dato", "ANDROID"));
            //nameValuePair.add(new BasicNameValuePair("password", "09721ab88e0a552087391be1ef0c6826"));
            //nameValuePair.add(new BasicNameValuePair("token", "67d6b32e8d96b8542feda3df334c04f5"));
            //nameValuePair.add(new BasicNameValuePair("campo", "E"));

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
            Log.e("Response: ", result11);

            //Checar Parametros
            //BufferedReader reader1 = new BufferedReader(new InputStreamReader(httppost.getEntity().getContent(), "utf-8"), 8);
            //StringBuilder sb1 = new StringBuilder();
            //sb1.append(reader1.readLine() + "\n");
            //String line1 = "0";
            //while ((line1 = reader1.readLine()) != null) {
            //    sb1.append(line1 + "\n");
            //}
            //reader1.close();
            //String result111 = sb1.toString();
            //Log.e("Parametros: ", result111);
            //Fin Checar Parametros


            arreglo = new JSONArray("[" + result11 + "]");
            // parsing data
            return arreglo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onPostExecute(JSONArray result) {
        progressDialog.dismiss();
        if (result != null) {

        } else {
            // error occured
        }
    }





}
