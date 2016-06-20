package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import Utitilies.Campos;
import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;


public class falla extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";

    Context context;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falla);

        session = new SessionManagement(getApplicationContext());

        TextView txtGhost = (TextView) findViewById(R.id.textView_desc1);
        TextView txtGhost2 = (TextView) findViewById(R.id.textView_desc2);
        TextView txtGhost3 = (TextView) findViewById(R.id.textView_comentario);
        TextView TitleSeccion = (TextView) findViewById(R.id.TitleSeccion);

        TitleSeccion.setText("SUGERENCIAS");
        Button btn=(Button) findViewById(R.id.btnEnviarFalla);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        tokenCTE = getText(R.string.tokenXM).toString();
        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
        btn.setTypeface(tf);

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(R.id.rdfalla, getString(R.string.rfuno)));
        datos.add(new Lista_Entrada(R.id.rdfalla,getString(R.string.rfdos)));
        datos.add(new Lista_Entrada(R.id.rdfalla, getString(R.string.rftres)));
        datos.add(new Lista_Entrada(R.id.rdfalla,getString(R.string.rfcuatro)));


        lista = (ListView) findViewById(R.id.falla);
        Lista_adaptador adaptador = new Lista_adaptador(this, R.layout.entrada_falla, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {


                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);

                    Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");

                    texto_inferior_entrada.setTypeface(tfi);


                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());
                }
            }

        };
        lista.setAdapter(adaptador);

        Button btnEnviarFalla;
        btnEnviarFalla = (Button) findViewById(R.id.btnEnviarFalla);

        // add button listener
        if (btnEnviarFalla != null) {
            btnEnviarFalla.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    JSONArray response;


                    final TextView txtErrorFalla = (TextView) findViewById(R.id.txtErrorFalla);
                    final EditText txtComentario = (EditText) findViewById(R.id.txtComentario);
                    final TextView txtErrorComentarioFalla = (TextView) findViewById(R.id.txtErrorComentarioFalla);

                    String comentario = txtComentario.getText().toString();
                    ListView _listView = (ListView) findViewById(R.id.falla);
                    View itemSelected = (View)_listView.findViewById(R.id.rdfalla);

                    Log.e("Response", "Falla Lista: " + _listView);


                    //   for (int i = 0; i < _listView.getAdapter().getCount(); i++) {
                     //       if (checked.get(i)) {
                     //           // Do something
                     //       }
                     //   }
                    //Log.e("Datos",dato);
                    //Log.e("tokenCTE",tokenCTE);
                    //Log.e("Campo",campo);
                    //Log.e("PASS",password);
                    txtErrorFalla.setText("");

                    //-------//
                    if(itemSelected == null)
                    {
                        Log.e("Response", "Falla: " + itemSelected);
                        txtErrorFalla.setText("Seleccione un tipo de falla");


                    }else if (comentario.trim().length() == 0)
                    {
                        txtErrorComentarioFalla.setText("Ingresa un comentario.");
                        txtComentario.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        //alert.showAlertDialog(login.this, "Aviso", "Ingresa tu " + item.toString()+ ".", false);
                    }
                    else {

                        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                        Boolean isInternetPresent = cd.isConnectingToInternet();

                        try {

                            if(isInternetPresent)
                            {
                                txtErrorFalla.setText("");
                                txtErrorComentarioFalla.setText("");
                                //-- PARAMETROS PETICION LOGIN-----//
                                Log.e("Response", "Falla: Entro a envio de falla");

                                ArrayList<String> params = new ArrayList<String>();
                                final HashMap<String, String> user = session.getUserDetails();
                                String idUsuario = user.get(SessionManagement.KEY_ID);
                                Log.e("Response", "Falla: " + itemSelected);
                                RadioButton rdFalla = (RadioButton)itemSelected.findViewById(R.id.rdfalla);
                                String opcion = rdFalla.getTag().toString();


                                //Fallas y sugerencias
                                //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
                                //idUsuario: es el id que les envio en el login
                                //correo: en caso de no contar con el id de usuario, es para las sugerencias
                                //tipo: puede tomar 2 valores [R] cuando es un reporte, [S] cuando es una sugerencia--
                                //opcion: cuando es un reporte de falla [R] se le envia el numero de la falla ---
                                //comentario: Comentario que pongan en la app ----

                                //-- PARAMETROS PETICION LOGIN-----//
                                params.add("3");
                                params.add("GetComment.php");
                                params.add(tokenCTE);
                                params.add(idUsuario);
                                params.add("R");
                                params.add(opcion);
                                params.add(comentario);

                                response = new Comunication(falla.this).execute(params).get();

                                Log.e("Response", "Falla: " + response);
                                if(response.getJSONObject(0).has("error")) {
                                    Log.e("Response Falla: ", "ERROR");
                                    txtErrorFalla.setText("Error al enviar reporte.");

                                }
                                else if(response.getJSONObject(0).has("resp"))
                                {
                                    String resp = response.getJSONObject(0).get("resp").toString();
                                    Log.e("Response Falla: ", resp);

                                    if(resp.equals("true")) {
                                        Intent i = new Intent(falla.this, ActualizadosActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else
                                    {
                                        txtErrorFalla.setText("Error al enviar reporte.");
                                        //Toast toast = Toast.makeText(falla.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                                        //toast.show();
                                    }
                                }
                            }
                            else{
                                txtErrorFalla.setText("No hay conexión a internet.");
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
        }

        //Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


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



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu
    }

    private String isCheckedOrNot(RadioButton checkbox) {
        if(checkbox.isChecked())
            return "is checked";
        else
            return "is not checked";
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
            Intent i = new Intent(falla.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(falla.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(falla.this, preferencias.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
       // if (id == R.id.action_favorite) {
       //     return true;
       // }

        return super.onOptionsItemSelected(item);




    }


}
