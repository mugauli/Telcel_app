package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class SugerenciasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);

        //Analytics
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Sugerencias");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Set the log level to verbose.
        GoogleAnalytics.getInstance(this).getLogger()
                .setLogLevel(Logger.LogLevel.VERBOSE);
        //
        tokenCTE = getText(R.string.tokenXM).toString();
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        TextView txtGhost = (TextView) findViewById(R.id.txtCorreoSugerencia);
        TextView txtGhost2 = (TextView) findViewById(R.id.txtSugerencia);
        TextView txtGhost3 = (TextView) findViewById(R.id.textView7);
        Button btn=(Button) findViewById(R.id.btnEnviarSugerencia);
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");

        // Applying font
        txtGhost.setTypeface(tfl);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
        btn.setTypeface(tf);
        txtGhost4.setTypeface(tf);
        txtGhost4.setText("SUGERENCIAS");

//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

      /*  toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SugerenciasActivity.this, LoginActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });*/
        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(user.get(SessionManagement.KEY_PD_ID) != null) {

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
                    Intent i = new Intent(SugerenciasActivity.this, triviasActivity.class);
                    startActivity(i);
                }
            });
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SugerenciasActivity.this, MainActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
            });

        }else
        {

        }

        /*if (imgButton != null) {
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
        }*/

        /*ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SugerenciasActivity.this, triviasActivity.class);
                startActivity(i);
            }
        });*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu


        Button btnEnviarSugerencia;
        btnEnviarSugerencia = (Button) findViewById(R.id.btnEnviarSugerencia);

        // add button listener
        if (btnEnviarSugerencia != null) {
            btnEnviarSugerencia.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    JSONArray response;

                    final EditText txtSugerencia = (EditText) findViewById(R.id.txtSugerencia);
                    final TextView tvErrorComentarioSugerencia = (TextView) findViewById(R.id.tvErrorComentarioSugerencia);
                    final EditText txtCorreoSugerencia = (EditText) findViewById(R.id.txtCorreoSugerencia);
                    final TextView tvErrorCorreoSugerencia = (TextView) findViewById(R.id.tvErrorCorreoSugerencia);
                    final TextView tvErrorSugerencia = (TextView) findViewById(R.id.tvErrorSugerencia);

                    String sugerencia = txtSugerencia.getText().toString();
                    String correo = txtCorreoSugerencia.getText().toString();



                    //   for (int i = 0; i < _listView.getAdapter().getCount(); i++) {
                    //       if (checked.get(i)) {
                    //           // Do something
                    //       }
                    //   }
                    //Log.e("Datos",dato);
                    //Log.e("tokenCTE",tokenCTE);
                    //Log.e("Campo",campo);
                    //Log.e("PASS",password);
                    tvErrorComentarioSugerencia.setText("");
                    tvErrorCorreoSugerencia.setText("");
                    tvErrorSugerencia.setText("");

                    //-------//

                    txtCorreoSugerencia.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                    if(user == null)
                    {
                        Log.e("Response", "Sin usuario ");
                        tvErrorSugerencia.setText("No existe un usuario logueado.");
                    }
                    else if(correo.trim().length() == 0)
                    {
                        Log.e("Response", "Sin correo ");
                        tvErrorCorreoSugerencia.setText("Ingrese su correo electrónico.");
                        txtCorreoSugerencia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                    }else if (!(correo.toString().matches("[a-zA-Z0-9._-]*[a-zA-Z0-9]+@mail\\.telcel\\.com"))
                            && !(correo.toString().matches("[a-zA-Z0-9._-]*[a-zA-Z0-9]+@americamovil\\.com"))
                            && !(correo.toString().matches("[a-zA-Z0-9._-]*[a-zA-Z0-9]+@telcel\\.com")))
                    {
                        Log.e("Response", "Ingrese un correo electrónico valido.");
                        tvErrorCorreoSugerencia.setText("Ingrese un correo electrónico valido.");
                        txtCorreoSugerencia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    } if (sugerencia.trim().length() == 0)
                    {
                        tvErrorComentarioSugerencia.setText("Ingrese una sugerencia.");
                        txtSugerencia.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        //alert.showAlertDialog(login.this, "Aviso", "Ingresa tu " + item.toString()+ ".", false);
                    }
                    else {

                        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                        Boolean isInternetPresent = cd.isConnectingToInternet();

                        try {

                            if(isInternetPresent)
                            {
                                //-- PARAMETROS PETICION LOGIN-----//
                                Log.e("Response", "Falla: Entro a envio de falla");

                                ArrayList<String> params = new ArrayList<String>();


                                String idUsuario = user.get(SessionManagement.KEY_PD_ID);




                                //Fallas y sugerencias
                                //token: siempre será 67d6b32e8d96b8542feda3df334c04f5
                                //idUsuario: es el id que les envio en el login
                                //correo: en caso de no contar con el id de usuario, es para las sugerencias
                                //tipo: puede tomar 2 valores [R] cuando es un reporte, [S] cuando es una sugerencia--
                                //opcion: cuando es un reporte de falla [R] se le envia el numero de la falla ---
                                //comentario: Comentario que pongan en la app ----

                                params.add("3");
                                params.add("GetComment.php");
                                params.add(tokenCTE);
                                params.add(idUsuario);
                                params.add("");
                                params.add("S");
                                params.add("");
                                params.add(sugerencia);

                                response = new Comunication(SugerenciasActivity.this).execute(params).get();

                                Log.e("Response", "Falla: " + response);
                                if(response.getJSONObject(0).has("error")) {
                                    Log.e("Response Falla: ", "ERROR");
                                    tvErrorSugerencia.setText("Error al enviar sugerencia.");

                                }
                                else if(response.getJSONObject(0).has("resp"))
                                {
                                    String resp = response.getJSONObject(0).get("resp").toString();
                                    Log.e("Response Falla: ", resp);

                                    if(resp.equals("true")) {
                                        Intent i = new Intent(SugerenciasActivity.this, enviado.class);
                                        startActivity(i);
                                        finish();
                                    }else
                                    {
                                        tvErrorSugerencia.setText("Error al enviar sugerencia.");
                                        //Toast toast = Toast.makeText(falla.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                                        //toast.show();
                                    }
                                }
                            }
                            else{
                                tvErrorSugerencia.setText("No hay conexión a internet.");
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
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String idUsuario = user.get(SessionManagement.KEY_PD_ID);

        if(user.get(SessionManagement.KEY_PD_ID) != null) {
            if (id == R.id.nav_camera) {
                Intent i = new Intent(SugerenciasActivity.this, ActualizarActivity.class);
                startActivity(i);

                // Handle the camera action
            } else if (id == R.id.nav_gallery) {
                Intent i = new Intent(SugerenciasActivity.this, pinActivity.class);
                startActivity(i);


            } else if (id == R.id.nav_slideshow) {
                Intent i = new Intent(SugerenciasActivity.this, preferencias.class);
                startActivity(i);

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

        }else
        {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

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
