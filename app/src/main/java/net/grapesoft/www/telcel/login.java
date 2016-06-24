package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import Utitilies.AlertDialogManager;
import Utitilies.Campos;
import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class login extends Activity  {

    public String tokenCTE = "";
    final Context context = this;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView txtGhost = (TextView) findViewById(R.id.textView3);
        TextView txtGhost2 = (TextView) findViewById(R.id.textView4);
        TextView txtGhost3 = (TextView) findViewById(R.id.tvRecuperar);
        TextView txtGhost4 = (TextView) findViewById(R.id.tvRegistrarme);
        TextView spinner_text=(TextView)findViewById(R.id.txtDato);
        final TextView txtpass=(TextView)findViewById(R.id.txtPassword);
        Button btn=(Button) findViewById(R.id.btnIngresar);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
        txtGhost4.setTypeface(tf);
        spinner_text.setTypeface(tfl);
        txtpass.setTypeface(tfl);
        btn.setTypeface(tf);
        txtGhost3.setPaintFlags(txtGhost3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtGhost4.setPaintFlags(txtGhost4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        session = new SessionManagement(getApplicationContext());

        tokenCTE = getText(R.string.tokenXM).toString();
        //Creamos la lista
        LinkedList SpnCampos = new LinkedList();

        SpnCampos.add(new Campos("0", "Elije un dato de ingreso..."));
        SpnCampos.add(new Campos("C", "Correo"));
        SpnCampos.add(new Campos("T", "Teléfono"));
        SpnCampos.add(new Campos("E", "No. de Empleado"));

        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SpnCampos);
        final TextView text = (TextView)findViewById(R.id.txtErrorDatos);
        //Se agrega el layout para el tipo de logeo y se asigna al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_campos = (Spinner) findViewById(R.id.spnCampos);
        spinner_campos.setAdapter(spinner_adapter);
        spinner_campos.setPrompt("Elige un dato e ingreso ...");
        spinner_campos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Campos item = (Campos)parentView.getItemAtPosition(position);
                EditText txtDato = (EditText) findViewById(R.id.txtDato);

                if(item.getId()=="0")
                {
                    txtDato.setVisibility(View.INVISIBLE);
                }
                else
                {
                    txtDato.setVisibility(View.VISIBLE);
                    txtDato.getBackground().setColorFilter(Color.rgb(77,177,209), PorterDuff.Mode.SRC_IN);
                }

                switch (item.getId())
                {
                    case "C":
                        txtDato.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        txtDato.setHint("Ingrese su correo");
                        break;
                    case "T":
                        txtDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                        txtDato.setHint("Ingrese su número de teléfono");
                        break;
                    case "E":
                        txtDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                        txtDato.setHint("Ingrese su número de empleado");
                        break;

                }
                txtDato.setText("");
                text.setText("");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        final EditText txtDato = (EditText) findViewById(R.id.txtDato);

        final TextView textPass = (TextView)findViewById(R.id.txtValidationPass);


        txtDato.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Spinner spinner_campos = (Spinner) findViewById(R.id.spnCampos);
                    Campos item = (Campos)spinner_campos.getSelectedItem();

                    if (txtDato.getText().toString().trim().length() > 0)
                    if(item.getId() == "C") {


                        if (!(txtDato.getText().toString().matches("[a-zA-Z0-9._-]+@[A-Za-z]+.[a-z]+"))) {

                            //mail.telcel.com
                            //Telcel.com
                            //americamovil.com
                            text.setText("Correo invalido.");
                            txtDato.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            Log.e("Email", "validacion de email: " + txtDato.getText().toString());
                            //message error

                        } else {
                            text.setText("");
                            //String[] correo =  txtDato.getText().toString().trim().split("@");

                            // String Email = correo[1].toString().trim();
                            //if(Email == "Telcel.com" )
                            //{
                            //    text.setText("");
                            //}
                            //else
                            //{
                            //    Log.e("Email", "Dominio: " + correo[1]);
                            //    text.setText("Correo invalido");
                            //}
                        }
                    }else if( item.getId() == "T")
                    {
                        if (!(txtDato.getText().toString().matches("[0-9]+"))) {


                            text.setText("Teléfono invalido.");
                            Log.e("Telefono", "validacion de telefono: " + txtDato.getText().toString());
                            //message error
                            txtDato.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        } else {
                            text.setText("");
                        }

                    }else if(item.getId() == "E")
                    {
                        if (!(txtDato.getText().toString().matches("[0-9]+"))) {


                            text.setText("Número de empleado invalido.");
                            Log.e("Telefono", "validacion de telefono: " + txtDato.getText().toString());
                            //message error
                            txtDato.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        } else {
                            text.setText("");

                        }

                    }
                }
            }
        });

        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);

        // add button listener
        btnIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
        public void onClick(View arg0) {
                JSONArray response;
                String dato="",password="";
                String campo = "";

                Spinner spinner_campos = (Spinner) findViewById(R.id.spnCampos);
                final EditText txtDato = (EditText) findViewById(R.id.txtDato);
                final EditText txtPass = (EditText) findViewById(R.id.txtPassword);
                final TextView txtErrorDato = (TextView) findViewById(R.id.txtErrorDatos);
                final TextView txtErrorPass = (TextView) findViewById(R.id.txtErrorContrasena);
                final TextView txtErrorInternet = (TextView) findViewById(R.id.txtErrorInternet);


                Campos item = (Campos)spinner_campos.getSelectedItem();
                campo = item.getId();
                dato = txtDato.getText().toString();
                password = txtPass.getText().toString();

               //Log.e("Datos",dato);
               //Log.e("tokenCTE",tokenCTE);
               //Log.e("Campo",campo);
               //Log.e("PASS",password);
                txtErrorDato.setText("");
                txtErrorPass.setText("");
                //-------//
                // Check if username, password is filled
                if(campo == "0")
                {
                    txtErrorDato.setText("Elige un dato de ingreso.");
                    //alert.showAlertDialog(login.this, "Aviso", "Elige un dato de ingreso.", false);
                }
                else if (dato.trim().length() == 0)
                {
                    txtErrorDato.setText("Ingresa tu " + item.toString()+ ".");
                    txtDato.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    //alert.showAlertDialog(login.this, "Aviso", "Ingresa tu " + item.toString()+ ".", false);
                }
                else if(password.trim().length() == 0)
                {
                    txtErrorPass.setText("Ingresa una contraseña..");
                    txtpass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    //alert.showAlertDialog(login.this, "Aviso", "Ingresa una contraseña.", false);

                }else{

                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    Boolean isInternetPresent = cd.isConnectingToInternet();

                    try {

                        if(isInternetPresent)
                        {
                            txtErrorInternet.setText("");
                            password = obtenerPassMD5(password);


                            //-- PARAMETROS PETICION LOGIN-----//


                            ArrayList<String> params = new ArrayList<String>();

                            //-- PARAMETROS PETICION LOGIN-----//
                            params.add("1");
                            params.add("GetLogin.php");
                            params.add(dato);
                            params.add(password);
                            params.add(tokenCTE);
                            params.add(campo);
                            response = new Comunication(login.this).execute(params).get();

                            //{"id":"5","num_empleado":"ANDROID","num_celular":"ANDROID","region":"1","nombre":"ANDROID","paterno":"ANDROID","materno":"ANDROID","interes_1":null,"interes_2":null}

                            if(!response.getJSONObject(0).has("error")) {
                                String id = response.getJSONObject(0).get("id").toString();
                                String num_empleado = response.getJSONObject(0).get("num_empleado").toString();
                                String num_celular = response.getJSONObject(0).get("num_celular").toString();
                                String region = response.getJSONObject(0).get("region").toString();
                                String nombre = response.getJSONObject(0).get("nombre").toString();
                                String paterno = response.getJSONObject(0).get("paterno").toString();
                                String materno = response.getJSONObject(0).get("materno").toString();
                                String interes_1 = response.getJSONObject(0).get("materno").toString();
                                String interes_2 = response.getJSONObject(0).get("materno").toString();

                                session.createLoginSession(tokenCTE, dato, campo, password, id, num_empleado, num_celular,
                                        region, nombre, paterno, materno, interes_1, interes_2);
                                Intent i = new Intent(login.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else
                            {
                                txtErrorPass.setText("La contraseña no es correcta.");
                                txtpass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);


                               // Toast toast = Toast.makeText(login.this, "Contraseña incorrecta", Toast.LENGTH_LONG);
                               // toast.show();

                            }

                            Log.i("Response", "Login: " + response);


                        }
                       else{

                            txtErrorInternet.setText("No hay conexión a internet.");
                            //Toast toast = Toast.makeText(login.this,  "No hay conexión a internet.", Toast.LENGTH_LONG);
                            //toast.show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }




          //String response = new Comunication().Post(dato,obtenerPassMD5(txtPass.getText().toString()),tokenCTE,campo);
               // String response = new Comunication().makePostRequest();
               // Log.i("Response", "Login: " + response);
        }
    });
        Log.i("LunchList", "Array Adapter Size: " + spinner_adapter.getCount());



        ImageView btnAyuda = (ImageView) findViewById(R.id.imageView2);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(login.this,ayuda.class);
                startActivity(intent);
            }
        });

        TextView txt = (TextView) findViewById(R.id.tvRecuperar);
        txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(login.this,recuperar.class);
                startActivity(intent);
            }
        });

        TextView txt2 = (TextView) findViewById(R.id.tvRegistrarme);
        txt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });

}

    public String obtenerPassMD5(String pass) {

        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }



}
