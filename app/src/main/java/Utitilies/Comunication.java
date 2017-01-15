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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mugauli on 01/06/2016.
 */
public class Comunication extends AsyncTask<ArrayList<String>, Void, JSONArray> {



    ProgressDialog progressDialog;
    public String IP = "",tokenCTE = "", NAMESPACE = "",SOAP_ACTIONCTE = "";
    public static JSONArray arreglo;



    public Comunication (Context cxt){
//        progressDialog = new ProgressDialog(cxt);
        IP = cxt.getString(R.string.URL);
        tokenCTE = cxt.getString(R.string.tokenXM);
        NAMESPACE = cxt.getString(R.string.NAMESPACE);
        SOAP_ACTIONCTE = cxt.getString(R.string.SOAP_ACTION);
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


            SoapObject resultado;
            String SOAP_ACTION = SOAP_ACTIONCTE + params[0].get(1);


            // Modelo el request
            SoapObject request = createSoapPost(params[0]);

            // Modelo el Sobre
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = false;
            sobre.implicitTypes = true;
            sobre.setAddAdornments(false);
            sobre.encodingStyle = SoapSerializationEnvelope.XSD;
            sobre.setOutputSoapObject(request);

            // Modelo el transporte
            HttpTransportSE transporte = new HttpTransportSE(Proxy.NO_PROXY, IP, 35000);
            transporte.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            transporte.debug = true;

            // Llamada
            transporte.call(SOAP_ACTION, sobre);

            if(sobre.bodyIn.toString().contains("fault"))
            {
                // Llamada
                transporte.call(SOAP_ACTION, sobre);
                Log.e("Intento", "segundo");
            }

            if(sobre.bodyIn.toString().contains("fault"))
            {
                // Llamada
                transporte.call(SOAP_ACTION, sobre);
                Log.e("Intento", "segundo");
            }

                // Resultado
                resultado = (SoapObject) sobre.getResponse();


            String result = resultado.getPropertyAsString("return");
            Log.i("Resultado", result);

            if (result.equals("true" + "\n")) {
                // Log.e("Response: ", "true Int");
                arreglo = new JSONArray("[{'resp':'true'}]");
            } else if (result.equals("false" + "\n")) {
                //Log.e("Response: ", "false int");
                arreglo = new JSONArray("[{'resp':'false'}]");
            } else {
                //Log.e("Response: ", "JSON");
                if (result.contains("["))
                    arreglo = new JSONArray(result);
                else
                    arreglo = new JSONArray("[" + result + "]");
            }
            return arreglo;


            //-----------------------------------------------------------RES
            //HttpClient httpclient = new DefaultHttpClient();

            //   Log.e("IP",IP + params[0].get(1));
            //HttpPost httppost = new HttpPost(IP + params[0].get(1));

            //httppost.setEntity(new UrlEncodedFormEntity(createPost(params[0])));

            //// Execute HTTP Post Request
            //HttpResponse response = httpclient.execute(httppost);

            //BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
            //StringBuilder sb = new StringBuilder();
            //sb.append(reader.readLine() + "\n");
            //String line = "0";
            //while ((line = reader.readLine()) != null) {
            //    sb.append(line + "\n");
            //}
            //reader.close();
            //
            //
            //
            //String result11 = sb.toString();
            //Log.e("Response: ", result11);
            //-----------------------------------------------------------RES FIN

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


            //  if(result11.equals("true"+"\n")) {
            //     // Log.e("Response: ", "true Int");
            //      arreglo = new JSONArray("[{'resp':'true'}]");
            //  }else if(result11.equals("false"+"\n")) {
            //      //Log.e("Response: ", "false int");
            //      arreglo = new JSONArray("[{'resp':'false'}]");
            //  } else
            //  {
            //      //Log.e("Response: ", "JSON");
            //      if(result11.contains("["))
            //          arreglo = new JSONArray(result11);
            //      else
            //          arreglo = new JSONArray("[" + result11 + "]");
            //  }


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

    }

    @Override
    public void onPostExecute(JSONArray result) {
       // progressDialog.dismiss();
        if (result != null) {

        } else {
            // error occured
        }
    }

    public SoapObject createSoapPost(ArrayList<String> paramsPassed )
    {

        SoapObject nameValuePair = new SoapObject(NAMESPACE, paramsPassed.get(1));

        if(paramsPassed.get(0)=="1")
        {
            //login

            //token: Siempre será igual a 67d6b32e8d96b8542feda3df334c04f5
            //campo: describe el campo con el cual el usuario quiere accesar y puede tomar 3 valores [C=correo, T=telefono, E=No. de empleado]
            //dato: El valor del campo de usuario [correo,telefono o numero de empleado]
            //password: Valor encriptado en MD5

            SoapObject datos = new SoapObject();

            datos.addProperty("dato", paramsPassed.get(2));
            datos.addProperty("password", paramsPassed.get(3));
            datos.addProperty("token", paramsPassed.get(4));
            datos.addProperty("campo", paramsPassed.get(5));

            nameValuePair.addProperty("datos_login_entrada", datos);

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

            //params.add("2");
            //params.add("ChangeData");
            //params.add(tokenCTE);
            //params.add(idUsuario);
            //params.add(num_Celular);
            //params.add(tipo_celular);
            //params.add(region);
            //params.add(nombre);
            //params.add(paterno);
            //params.add(materno);
            //params.add(email);

            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idUsuario", paramsPassed.get(3));
            //datos.addProperty("num_celular", paramsPassed.get(4));
            //datos.addProperty("tipo_celular", paramsPassed.get(5));
            //datos.addProperty("region", paramsPassed.get(6));
            datos.addProperty("nombre", paramsPassed.get(7));
            datos.addProperty("paterno", paramsPassed.get(8));
            datos.addProperty("materno", paramsPassed.get(9));
            datos.addProperty("email", paramsPassed.get(10));

            nameValuePair.addProperty("actualiza_datos_entrada", datos);

        }else if(paramsPassed.get(0)=="3")
        {
            //Fallas y sugerencias
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login
            //correo: en caso de no contar con el id de usuario, es para las sugerencias
            //tipo: puede tomar 2 valores [R] cuando es un reporte, [S] cuando es una sugerencia--
            //opcion: cuando es un reporte de falla [R] se le envia el numero de la falla ---
            //comentario: Comentario que pongan en la app ----

            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idUsuario", paramsPassed.get(3));
            datos.addProperty("tipo", paramsPassed.get(4));
            datos.addProperty("opcion", paramsPassed.get(5));
            datos.addProperty("comentario", paramsPassed.get(6));

            nameValuePair.addProperty("datos_comentario_entrada", datos);

        }else if(paramsPassed.get(0)=="4")
        {
            //Cambiar Contraseña
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login
            //password: encriptado con md5

            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idUsuario", paramsPassed.get(3));
            datos.addProperty("password", paramsPassed.get(4));

            nameValuePair.addProperty("actualiza_password_entrada", datos);




        }else if(paramsPassed.get(0)=="5")
        {
            //Recuperar Contraseña
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            SoapObject datos = new SoapObject();
            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idUsuario", paramsPassed.get(3));

            nameValuePair.addProperty("datos_recupera_entrada", datos);

        }else if(paramsPassed.get(0)=="6")
        {
            //GetHome
           //params.add("6");
           //params.add("GetHome");
           //params.add(tokenCTE);
           //params.add(region);

            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("reg", paramsPassed.get(3));

            nameValuePair.addProperty("datos_generales_entrada", datos);

        }else if(paramsPassed.get(0)=="7")
        {
            //PIN
           //params.add("7");
           //params.add("ChangePassword.php");
           //params.add(tokenCTE);
           //params.add(reg);
           //params.add(idUsuario);

            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("reg", paramsPassed.get(3));
            datos.addProperty("idUsuario", paramsPassed.get(4));

            nameValuePair.addProperty("actualiza_password_entrada", datos);

        }else if(paramsPassed.get(0)=="8")
        {
            //Descuentos
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            nameValuePair.addProperty("token", paramsPassed.get(2));
            nameValuePair.addProperty("idUsuario", paramsPassed.get(3));
            nameValuePair.addProperty("reg", paramsPassed.get(4));

        }else if(paramsPassed.get(0)=="9")
        {
            //SaveWinner
            //params.add("9");
            //params.add("SaveWinner.php");
            //params.add(tokenCTE);
            //params.add(trivia);
            //params.add(user.get(SessionManagement.KEY_PD_ID));
            //params.add(user.get(SessionManagement.KEY_PD_REGION));
            //params.add(puntos);
            //params.add("1");

            SoapObject datos = new SoapObject();
            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idTrivia", paramsPassed.get(3));
            datos.addProperty("idUsuario", paramsPassed.get(4));
            datos.addProperty("reg", paramsPassed.get(5));
            datos.addProperty("aciertos", paramsPassed.get(6));
            datos.addProperty("estatus", paramsPassed.get(7));

            nameValuePair.addProperty("datos_ganador_entrada", datos);



        }else if(paramsPassed.get(0)=="10")
        {
            //ChangeInterests.php
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login
            //interes_1: es el id de interes 1
            //interes_2: es el id de interes 2
            //-- PARAMETROS PETICION LOGIN-----//
            //params.add("10");
            //params.add("ChangeInterests");
            //params.add(tokenCTE);
            //params.add(usuario);
            //params.add(rb1 + "");
            //params.add(rb2 + "");


            SoapObject datos = new SoapObject();

            datos.addProperty("token", paramsPassed.get(2));
            datos.addProperty("idUsuario", paramsPassed.get(3));
            datos.addProperty("interes_1", paramsPassed.get(4));
            datos.addProperty("interes_2", paramsPassed.get(5));

            nameValuePair.addProperty("actualiza_intereses_entrada", datos);


        }

        return nameValuePair;

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

        }else if(paramsPassed.get(0)=="7")
        {
            //PIN
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("reg", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(4)));
           // nameValuePair.add(new BasicNameValuePair("password", paramsPassed.get(5)));

        }else if(paramsPassed.get(0)=="8")
        {
            //Descuentos
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("reg", paramsPassed.get(4)));
        }else if(paramsPassed.get(0)=="9")
        {
            //SaveWinner
           //params.add("9");
           //params.add("SaveWinner.php");
           //params.add(tokenCTE);
           //params.add(trivia);
           //params.add(user.get(SessionManagement.KEY_PD_ID));
           //params.add(user.get(SessionManagement.KEY_PD_REGION));
           //params.add(puntos);
           //params.add("1");


            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idTrivia", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("reg", paramsPassed.get(5)));
            nameValuePair.add(new BasicNameValuePair("aciertos", paramsPassed.get(6)));
            nameValuePair.add(new BasicNameValuePair("estatus", paramsPassed.get(7)));

        }else if(paramsPassed.get(0)=="10")
        {
            //ChangeInterests.php
            //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
            //idUsuario: es el id que les envio en el login
            //interes_1: es el id de interes 1
            //interes_2: es el id de interes 2

            nameValuePair.add(new BasicNameValuePair("token", paramsPassed.get(2)));
            nameValuePair.add(new BasicNameValuePair("idUsuario", paramsPassed.get(3)));
            nameValuePair.add(new BasicNameValuePair("interes_1", paramsPassed.get(4)));
            nameValuePair.add(new BasicNameValuePair("interes_2", paramsPassed.get(5)));
        }

        return nameValuePair;

    }

}
