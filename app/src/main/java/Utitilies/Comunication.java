package Utitilies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import net.grapesoft.www.telcel.R;

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



    ProgressDialog progressDialog;
    public String IP = "",tokenCTE = "";
    public static JSONArray arreglo;



    public Comunication (Context cxt){
        progressDialog = new ProgressDialog(cxt);
        IP = cxt.getString(R.string.URL);
        tokenCTE = cxt.getString(R.string.tokenXM);
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

            Log.e("IP",IP + params[0].get(1));
            HttpPost httppost = new HttpPost(IP + params[0].get(1));

            httppost.setEntity(new UrlEncodedFormEntity(createPost(params[0])));

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

    public List<NameValuePair> createPost(ArrayList<String> paramsPassed)
    {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

        if(paramsPassed.get(0)=="1")
        {
            nameValuePair.add(new BasicNameValuePair("dato", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("password", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("campo", paramsPassed.get(5)));

        }else if(paramsPassed.get(0)=="2")
        {
            nameValuePair.add(new BasicNameValuePair("dato", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("password", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("campo", paramsPassed.get(5)));

        }


        return nameValuePair;

    }







}
