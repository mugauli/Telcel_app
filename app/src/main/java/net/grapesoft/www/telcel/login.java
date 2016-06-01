package net.grapesoft.www.telcel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import Utitilies.Campos;
import Utitilies.Comunication;

public class login extends ActionBarActivity {
    public String tokenCTE = "67d6b32e8d96b8542feda3df334c04f5";
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Creamos la lista
        LinkedList SpnCampos = new LinkedList();

        SpnCampos.add(new Campos('C', "Correo"));
        SpnCampos.add(new Campos('T', "Teléfono"));
        SpnCampos.add(new Campos('E', "No. de Empleado"));

        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SpnCampos);

        //Se agrega el layout para el tipo de logeo y se asigna al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner_campos = (Spinner) findViewById(R.id.spnCampos);

        spinner_campos.setAdapter(spinner_adapter);

        spinner_campos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Campos item = (Campos)parentView.getItemAtPosition(position);
                Log.i("LunchList", "IDddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd:" + item.getId());
                ocultaCampos(item.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);

        // add button listener
        btnIngresar.setOnClickListener(new View.OnClickListener() {

            @Override
        public void onClick(View arg0) {

                Spinner spinner_campos = (Spinner) findViewById(R.id.spnCampos);

                String dato="",campo = spinner_campos.getSelectedItem().toString();
                if(campo=="C") {
                    final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
                    dato=txtEmail.getText().toString();
                }
                else if (campo == "T") {
                    final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
                    dato=txtPhone.getText().toString();
                }else {
                    final EditText txtEmpleado = (EditText) findViewById(R.id.txtNoEmpleado);
                    dato=txtEmpleado.getText().toString();
                }

                final EditText txtPass = (EditText) findViewById(R.id.txtPassword);


          String response = new Comunication().Post(dato,obtenerPassMD5(txtPass.getText().toString()),tokenCTE,campo);
                Log.i("Response", "Login: " + response);
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

    public boolean ocultaCampos (char campo)
    {
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtNoEmpleado = (EditText) findViewById(R.id.txtNoEmpleado);
        EditText txtPhone = (EditText) findViewById(R.id.txtPhone);

        txtEmail.setVisibility(View.INVISIBLE);
        txtEmail.setKeyListener(DialerKeyListener.getInstance());
        txtPhone.setVisibility(View.INVISIBLE);
        txtPhone.setKeyListener(DialerKeyListener.getInstance());
        txtNoEmpleado.setVisibility(View.INVISIBLE);
        txtNoEmpleado.setKeyListener(DialerKeyListener.getInstance());

        if(campo == 'C')
        {
            txtEmail.setVisibility(View.VISIBLE);

        }else if(campo == 'T')
        {
            txtPhone.setVisibility(View.VISIBLE);

        }else if(campo == 'E')
        {
            txtNoEmpleado.setVisibility(View.VISIBLE);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);




    }
}
