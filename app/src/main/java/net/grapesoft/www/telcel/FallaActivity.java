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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;


public class FallaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";
    RadioButton rb1,rb2,rb3,rb4,rb5;

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
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);


        Button btn=(Button) findViewById(R.id.btnEnviarFalla);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        tokenCTE = getText(R.string.tokenXM).toString();
        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
        btn.setTypeface(tf);
        txtGhost4.setText("REPORTAR FALLA");

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(R.id.rdfalla, getString(R.string.rfuno)));
        datos.add(new Lista_Entrada(R.id.rdfalla,getString(R.string.rfdos)));
        datos.add(new Lista_Entrada(R.id.rdfalla, getString(R.string.rftres)));
        datos.add(new Lista_Entrada(R.id.rdfalla,getString(R.string.rfcuatro)));

        rb1 = (RadioButton) findViewById(R.id.rfUnoRB);
        rb2 = (RadioButton) findViewById(R.id.rfDosRB);
        rb3 = (RadioButton) findViewById(R.id.rfTresRB);
        rb4 = (RadioButton) findViewById(R.id.rfCuartoRB);
        rb5 = (RadioButton) findViewById(R.id.rfCincoRB);


     //   lista = (ListView) findViewById(R.id.falla);
     //  List_adapted adaptador = new List_adapted(this, R.layout.entrada_falla, datos){
     //      @Override
     //      public void onEntrada(Object entrada, View view) {
     //          if (entrada != null) {


     //              TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);

     //              Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");

     //              texto_inferior_entrada.setTypeface(tfi);


     //              if (texto_inferior_entrada != null)
     //                  texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());
     //          }
     //      }

     //  };
//        lista.setAdapter(adaptador);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unCheckedAll();
                rb1.setChecked(true);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCheckedAll();
                rb2.setChecked(true);
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCheckedAll();
                rb3.setChecked(true);
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCheckedAll();
                rb4.setChecked(true);
            }
        });
        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCheckedAll();
                rb5.setChecked(true);
            }
        });

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

                    //Log.e("Datos",dato);
                    //Log.e("tokenCTE",tokenCTE);
                    //Log.e("Campo",campo);
                    //Log.e("PASS",password);
                    txtErrorFalla.setText("");


                    //-------//
                  if(getChecked() == 0)
                  {
                      Log.e("Response", "Falla: no hay seleccionados");
                      txtErrorFalla.setText("Seleccione un tipo de falla");

                  }else if (comentario.trim().length() == 0)
                  {
                    txtErrorComentarioFalla.setText("Ingresa un comentario.");
                    txtComentario.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    //alert.showAlertDialog(login.this, "Aviso", "Ingresa tu " + item.toString()+ ".", false);
                 } else {

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
                             String idUsuario = user.get(SessionManagement.KEY_PD_ID);
                           //  Log.e("Response", "Falla: " + itemSelected);
                           //  RadioButton rdFalla = (RadioButton)itemSelected.findViewById(R.id.rdfalla);
                              String opcion = String.valueOf(getChecked());


              //                //Fallas y sugerencias
              //                //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
              //                //idUsuario: es el id que les envio en el login
              //                //correo: en caso de no contar con el id de usuario, es para las sugerencias
              //                //tipo: puede tomar 2 valores [R] cuando es un reporte, [S] cuando es una sugerencia--
              //                //opcion: cuando es un reporte de falla [R] se le envia el numero de la falla ---
              //                //comentario: Comentario que pongan en la app ----

                              //-- PARAMETROS PETICION -----//
                              params.add("3");
                              params.add("GetComment.php");
                              params.add(tokenCTE);
                              params.add(idUsuario);
                              params.add("R");
                              params.add(opcion);
                              params.add(comentario);

                              response = new Comunication(FallaActivity.this).execute(params).get();

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
                                      Intent i = new Intent(FallaActivity.this, ActualizadosActivity.class);
                                      i.putExtra("titulo","MENSAJE ENVIADO");
                                      i.putExtra("mensaje","Gracias, Tu mensaje ha sido enviado.");
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

        if (toolbar != null) {
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(FallaActivity.this, MainActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
            });
        }

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (imgButton != null) {
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
        }
        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FallaActivity.this, triviasActivity.class);
                startActivity(i);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu
    }

    public int getChecked()
    {
        if(rb1.isChecked())
            return 1;
        if(rb2.isChecked())
            return 2;
        if(rb3.isChecked())
            return 3;
        if(rb4.isChecked())
            return 4;
        if(rb5.isChecked())
            return 5;
        return 0;
    }

    public void unCheckedAll()
    {
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        rb5.setChecked(false);
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
            Intent i = new Intent(FallaActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(FallaActivity.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            /*Intent i = new Intent(falla.this, preferencias.class);
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
