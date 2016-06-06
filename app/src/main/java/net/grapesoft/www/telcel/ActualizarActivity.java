package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.AlertDialogManager;
import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class ActualizarActivity extends ActionBarActivity {

    SessionManagement session;
    public String tokenCTE = "";

    public String Radio = "P";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        tokenCTE = getText(R.string.tokenXM).toString();
        Spinner spinner_Region = (Spinner) findViewById(R.id.spnRegion);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.region , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Region.setAdapter(spinner_adapter);

        Spinner spinner_Direccion = (Spinner) findViewById(R.id.spnDireccion);

        ArrayAdapter spinner_adapterD = ArrayAdapter.createFromResource( this, R.array.direccion , android.R.layout.simple_spinner_item);
        spinner_adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Direccion.setAdapter(spinner_adapterD);





        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);

        // add button listener
        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                Boolean isInternetPresent = cd.isConnectingToInternet();

                session = new SessionManagement(getApplicationContext());
                final HashMap<String, String> user = session.getUserDetails();
                String region = "",tipo_celular="";

                try {

                    if (isInternetPresent) {

                        //"ChangeData.php"
                        // ?token=67d6b32e8d96b8542feda3df334c04f5&
                        // idUsuario=5&
                        // num_celular=5548326608&
                        // tipo_celular=P&
                        // region=9&
                        // nombre=Guillermo&
                        // paterno=Rodriguez&
                        // materno=Garibay&
                        // email=guillermo@telcel.com


                        Spinner spinner_Region = (Spinner) findViewById(R.id.spnRegion);
                        Spinner spinner_Direccion = (Spinner) findViewById(R.id.spnDireccion);
                        EditText txtCelular = (EditText) findViewById(R.id.txtCelular);
                        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
                        EditText txtPaterno = (EditText) findViewById(R.id.txtPaterno);
                        EditText txtMaterno = (EditText) findViewById(R.id.txtMaterno);
                        EditText txtCorreo = (EditText) findViewById(R.id.txtCorreo);

                        ArrayList<String> params = new ArrayList<String>();

                        String idUsuario = user.get(SessionManagement.KEY_ID);
                        String num_Celular = txtCelular.getText().toString();

                        tipo_celular = Radio;
                        //String region = region;

                        region = Long.toString(spinner_Region.getSelectedItemId()+1);


                        if (region == "11") {
                            region = "C";
                        } else if (region == "12") {
                            region = "A";
                        }

                        Log.e("Request", "Region: " + region);

                        String nombre = txtNombre.getText().toString();
                        String paterno = txtPaterno.getText().toString();
                        String materno = txtMaterno.getText().toString();
                        String email = txtCorreo.getText().toString();

                        //-- PARAMETROS PETICION Actualizar trabajador-----//
                        params.add("2");
                        params.add("ChangeData.php");
                        params.add(tokenCTE);
                        params.add(idUsuario);
                        params.add(num_Celular);
                        params.add(tipo_celular);
                        params.add(region);
                        params.add(nombre);
                        params.add(paterno);
                        params.add(materno);
                        params.add(email);


                        JSONArray response = new Comunication(ActualizarActivity.this).execute(params).get();


                        if(response.getJSONObject(0).has("error")) {

                            Toast toast = Toast.makeText(ActualizarActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG);
                            toast.show();

                        }
                        else if(response.getJSONObject(0).has("resp"))
                        {
                            Intent i = new Intent(ActualizarActivity.this, ActualizarActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {

                        }
                         Log.i("Response", "Login: " + response);


                    } else {
                        //Toast
                    }
                }catch (Exception e)
                {

                }

            }
        });
        ImageView btnAyuda = (ImageView) findViewById(R.id.imgAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActualizarActivity.this,ayuda.class);
                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton rbAsignado = (RadioButton)  findViewById(R.id.rbAsignado);
        RadioButton rbPersonal = (RadioButton)  findViewById(R.id.rbPersonal);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbPersonal:
                if (checked) {
                    Radio = "P";
                    rbAsignado.setChecked(false);
                }
                    break;
            case R.id.rbAsignado:
                if (checked) {
                    Radio = "A";
                    rbPersonal.setChecked(false);
                }
                    break;
        }
        Toast toast = Toast.makeText(ActualizarActivity.this, "Radio: " + Radio , Toast.LENGTH_LONG);
        toast.show();
    }
}
