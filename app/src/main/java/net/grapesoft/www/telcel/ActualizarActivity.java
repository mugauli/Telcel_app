package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class ActualizarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";

    public String Radio = "P";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();




        setContentView(R.layout.activity_actualizar);

        //Fuentes
        TextView tvCelular = (TextView) findViewById(R.id.tvCelular);

        TextView tvRegion = (TextView) findViewById(R.id.tvRegion);
        TextView tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        RadioButton rbAsignado = (RadioButton) findViewById(R.id.rbAsignado);
        RadioButton rbPersonal = (RadioButton) findViewById(R.id.rbPersonal);
        TextView tvNombres = (TextView) findViewById(R.id.tvNombres);
        TextView tvPaterno = (TextView) findViewById(R.id.tvPaterno);
        TextView tvMaterno = (TextView) findViewById(R.id.tvMaterno);
        TextView tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        TextView txt1 = (TextView) findViewById(R.id.txtCelular);

        TextView txt2 = (TextView) findViewById(R.id.txtNombre);
        TextView txt3 = (TextView) findViewById(R.id.txtPaterno);
        TextView txt4 = (TextView) findViewById(R.id.txtMaterno);
        TextView txt5 = (TextView) findViewById(R.id.txtCorreo);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        // Applying font
        tvCelular.setTypeface(tfm);
        tvRegion.setTypeface(tfm);
        tvDireccion.setTypeface(tfm);
        tvNombres.setTypeface(tfm);
        tvPaterno.setTypeface(tfm);
        tvMaterno.setTypeface(tfm);
        tvCorreo.setTypeface(tfm);
        txt1.setTypeface(tf);
        txt2.setTypeface(tf);
        txt3.setTypeface(tf);
        txt4.setTypeface(tf);
        txt5.setTypeface(tf);
        rbAsignado.setTypeface(tfm);
        rbPersonal.setTypeface(tfm);

        tokenCTE = getText(R.string.tokenXM).toString();
        Spinner spinner_Region = (Spinner) findViewById(R.id.spnRegion);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.region , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Region.setAdapter(spinner_adapter);

        Spinner spinner_Direccion = (Spinner) findViewById(R.id.spnDireccion);

        ArrayAdapter spinner_adapterD = ArrayAdapter.createFromResource( this, R.array.direccion , android.R.layout.simple_spinner_item);
        spinner_adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Direccion.setAdapter(spinner_adapterD);

        //Obtener valores de session

        //asiganción de valores

        // num_celular=5548326608&
        // tipo_celular=P&
        // region=9&
        // nombre=Guillermo&
        // paterno=Rodriguez&
        // materno=Garibay&
        // email=guillermo@telcel.com

        if(user.get(SessionManagement.KEY_PD_ID) != null) {
            Spinner spinner_Region1 = (Spinner) findViewById(R.id.spnRegion);
            Spinner spinner_Direccion1 = (Spinner) findViewById(R.id.spnDireccion);

            spinner_Region1.setSelection(Integer.parseInt(user.get(SessionManagement.KEY_PD_REGION))-1);

            EditText txtCelular = (EditText) findViewById(R.id.txtCelular);
            txtCelular.setText(user.get(SessionManagement.KEY_PD_NUM_CELULAR));
            txtCelular.setKeyListener(null);
            txtCelular.setEnabled(false);

            EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
            txtNombre.setText(user.get(SessionManagement.KEY_PD_NOMBRE));

            EditText txtPaterno = (EditText) findViewById(R.id.txtPaterno);
            txtPaterno.setText(user.get(SessionManagement.KEY_PD_PATERNO));

            EditText txtMaterno = (EditText) findViewById(R.id.txtMaterno);
            txtMaterno.setText(user.get(SessionManagement.KEY_PD_MATERNO));

            EditText txtCorreo = (EditText) findViewById(R.id.txtCorreo);
            txtCorreo.setText(user.get(SessionManagement.KEY_PD_CORREO));
        }
        else
        {
            Intent i = new Intent(ActualizarActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }



        //and Asignacion de valores

        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);

        // add button listener
        if(btnEnviar != null)
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

                        String idUsuario = user.get(SessionManagement.KEY_PD_ID);
                        String num_Celular = txtCelular.getText().toString();
                        Log.e("Request", "Celular: " + num_Celular);
                        if(num_Celular.length() == 0 || num_Celular.length() > 10 || num_Celular.length() < 10) {
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Número de teléfono invalido.", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        tipo_celular = Radio;

                        region = Long.toString(spinner_Region.getSelectedItemId()+1);

                        if (region == "11") {
                            region = "C";
                        } else if (region == "12") {
                            region = "A";
                        }

                        Log.e("Request", "Region: " + region);

                        String nombre = txtNombre.getText().toString();
                        if(nombre.length() == 0) {
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Nombre(s) invalido.", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        String paterno = txtPaterno.getText().toString();
                        if(paterno.length() == 0) {
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Apellido paterno invalido.", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        String materno = txtMaterno.getText().toString();
                        if(materno.length() == 0) {
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Apellido materno invalido.", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }
                        String email = txtCorreo.getText().toString();
                        if(email.length() == 0|| !email.matches("[a-zA-Z0-9._-]+@[A-Za-z]+.+[a-z]+")) {
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Correo invalido.", Toast.LENGTH_LONG);
                            toast.show();
                            return;
                        }

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

                        Log.e("Response", "Actualizar: " + response);
                        if(response.getJSONObject(0).has("error")) {
                            Log.e("Response Actualizar: ", "ERROR");
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Error al actualizar los datos", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if(response.getJSONObject(0).has("resp"))
                        {
                            String resp = response.getJSONObject(0).get("resp").toString();
                            Log.e("Response Actualizar: ", resp);

                            if(resp.equals("true")) {
                                Intent i = new Intent(ActualizarActivity.this, ActualizadosActivity.class);
                                i.putExtra("titulo","MODIFICAR O ACTUALIZAR DATOS");
                                i.putExtra("mensaje","Tus datos han sido actualizados.");
                                startActivity(i);
                                finish();
                            }else
                            {
                                Toast toast = Toast.makeText(ActualizarActivity.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                        else
                        {
                            Log.e("Response Actualizar: ", "ERROR NO DEFINIDO");
                            Toast toast = Toast.makeText(ActualizarActivity.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else {
                        //Toast
                    }
                }catch (Exception e)
                {

                }

            }
        });
       /* ImageView btnAyuda = (ImageView) findViewById(R.id.imgAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActualizarActivity.this,ayuda.class);
                startActivity(intent);
            }
        });*/

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActualizarActivity.this,ayuda.class);
                startActivity(intent);
            }
        });
        //boton ayuda


//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ActualizarActivity.this, MainActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(imgButton != null)
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActualizarActivity.this, triviasActivity.class);
                startActivity(i);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        TextView titulo = (TextView) findViewById(R.id.TitleSeccion);
        titulo.setTypeface(tf);

        titulo.setText("MODIFICAR O ACTUALIZAR DATOS");
        //ToolBar Menu
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            Intent i = new Intent(ActualizarActivity.this, ActualizarActivity.class);

            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(ActualizarActivity.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            /*Intent i = new Intent(ActualizarActivity.this, preferencias.class);
            startActivity(i);*/

        } else if (id == R.id.nav_send) {
            session.logoutUser();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
      //  Toast toast = Toast.makeText(ActualizarActivity.this, "Radio: " + Radio , Toast.LENGTH_LONG);
        // toast.show();
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
       // if (id == R.id.action_favorite) {
       //     return true;
       // }

        return super.onOptionsItemSelected(item);




    }
}
