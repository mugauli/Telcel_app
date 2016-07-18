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
import org.json.JSONException;

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
//        progressDialog = new ProgressDialog(cxt);
        IP = cxt.getString(R.string.URL);
        tokenCTE = cxt.getString(R.string.tokenXM);
    }

    @Override
    public void onPreExecute(){
        super.onPreExecute();
      //  progressDialog.setTitle("Cargando");
      //  progressDialog.setMessage("Espere Por Favor . . . ");
      //  progressDialog.show();

    }

    @Override
    public JSONArray doInBackground(ArrayList<String>... params) {

        try {

            HttpClient httpclient = new DefaultHttpClient();

         //   Log.e("IP",IP + params[0].get(1));
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
            //Log.e("Response: ", result11);

         //Checar Parametros
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(httppost.getEntity().getContent(), "utf-8"), 8);
        StringBuilder sb1 = new StringBuilder();
        sb1.append(reader1.readLine() + "\n");
        String line1 = "0";
        while ((line1 = reader1.readLine()) != null) {
            sb1.append(line1 + "\n");
        }
        reader1.close();
        String result111 = sb1.toString();
        Log.e("Parametros: ", result111);
         //Fin Checar Parametros


            if(result11.equals("true"+"\n")) {
               // Log.e("Response: ", "true Int");
                arreglo = new JSONArray("[{'resp':'true'}]");
            }else if(result11.equals("false"+"\n")) {
                //Log.e("Response: ", "false int");
                arreglo = new JSONArray("[{'resp':'false'}]");
            } else
            {
                //Log.e("Response: ", "JSON");
                if(result11.contains("["))
                    arreglo = new JSONArray(result11);
                else
                    arreglo = new JSONArray("[" + result11 + "]");
            }


            // parsing data


        } catch (Exception e) {
            try {
                arreglo = new JSONArray("[{'error':'true'}]");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
        return arreglo;
    }

    @Override
    public void onPostExecute(JSONArray result) {
       // progressDialog.dismiss();
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
            //login

            //token: Siempre será igual a 67d6b32e8d96b8542feda3df334c04f5
            //campo: describe el campo con el cual el usuario quiere accesar y puede tomar 3 valores [C=correo, T=telefono, E=No. de empleado]
            //dato: El valor del campo de usuario [correo,telefono o numero de empleado]
            //password: Valor encriptado en MD5

            nameValuePair.add(new BasicNameValuePair("dato", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("password", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("campo", paramsPassed.get(5)));

        }else if(paramsPassed.get(0)=="2")
        {
            //Actualiza trabajador

            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login
            //num_celular
            //tipo_celular: Puede tomar 2 valores [A] celular asignado [P] Personal
            //region: Toma varios valores 1-9, C, A
            //nombre
            //paterno
            //materno
            //email

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("num_celular", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("tipo_celular", paramsPassed.get(5)));
            nameValuePair.add(new BasicNameValuePair("region", paramsPassed.get(6)));
            nameValuePair.add(new BasicNameValuePair("nombre", paramsPassed.get(7)));
            nameValuePair.add(new BasicNameValuePair("paterno", paramsPassed.get(8)));
            nameValuePair.add(new BasicNameValuePair("materno", paramsPassed.get(9)));
            nameValuePair.add(new BasicNameValuePair("email", paramsPassed.get(10)));

        }else if(paramsPassed.get(0)=="3")
        {
           //Fallas y sugerencias
           //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
           //idUsuario: es el id que les envio en el login
           //correo: en caso de no contar con el id de usuario, es para las sugerencias
           //tipo: puede tomar 2 valores [R] cuando es un reporte, [S] cuando es una sugerencia--
           //opcion: cuando es un reporte de falla [R] se le envia el numero de la falla ---
           //comentario: Comentario que pongan en la app ----

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("tipo", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("opcion", paramsPassed.get(5)));
            nameValuePair.add(new BasicNameValuePair("comentario", paramsPassed.get(6)));
           // nameValuePair.add(new BasicNameValuePair("correo", paramsPassed.get(7)));
        }else if(paramsPassed.get(0)=="4")
        {
            //Cambiar Contraseña
           //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
           //idUsuario: es el id que les envio en el login
           //password: encriptado con md5

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("password", paramsPassed.get(4)));

        }else if(paramsPassed.get(0)=="5")
        {
            //Recuperar Contraseña
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));

        }else if(paramsPassed.get(0)=="6")
        {
            //Recuperar Contraseña
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("reg", paramsPassed.get(3)));

        }


        return nameValuePair;

    }







}
