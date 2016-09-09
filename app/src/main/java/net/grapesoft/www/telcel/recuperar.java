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
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class recuperar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManagement session;
    public String tokenCTE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        tokenCTE = getText(R.string.tokenXM).toString();
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();

        TextView texto_superior_entrada = (TextView) findViewById(R.id.tvCabeceraRecuperar);
        TextView texto_superior = (TextView) findViewById(R.id.tvCabeceraCampoRecuperar);

        Button btn=(Button) findViewById(R.id.btnEnviarRecuperar);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/obscura.otf");
        Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        texto_superior_entrada.setTypeface(tfl);
        texto_superior.setTypeface(tf);
        btn.setTypeface(tfm);

        //AYUDA
        ImageView btnAyuda = (ImageView) findViewById(R.id.imgAyudaRecuperar);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(recuperar.this,ayuda.class);
                startActivity(intent);
            }
        });
        //AYUDA


//Toolbar Menu

        //TitleSeccion
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
                Intent i = new Intent(recuperar.this, LoginActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });
        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

      /*  imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView titulo = (TextView) findViewById(R.id.TitleSeccion);
        titulo.setTypeface(tf);
        titulo.setText("RECUPERAR CONTRASEÑA");

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
                    Intent i = new Intent(recuperar.this, triviasActivity.class);
                    startActivity(i);
                }
            });

        }else
        {

        }

        //ToolBar Menu

        Button btnEnviarFalla;
        btnEnviarFalla = (Button) findViewById(R.id.btnEnviarRecuperar);

        // add button listener
        if (btnEnviarFalla != null) {
            btnEnviarFalla.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    JSONArray response;

                    TextView tvErrorRecuperar = (TextView) findViewById(R.id.tvErrorRecuperar);
                    EditText txtUsuario = (EditText) findViewById(R.id.txtUsuarioRecuperar);
                    txtUsuario.setInputType(InputType.TYPE_CLASS_NUMBER);
                    tvErrorRecuperar.setText("");


                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    Boolean isInternetPresent = cd.isConnectingToInternet();

                    try {
                        if(txtUsuario.length()==0)
                        {
                            tvErrorRecuperar.setText("Ingrese el número de Usuario");
                            txtUsuario.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        }else if (isInternetPresent) {

                            tvErrorRecuperar.setText("");

                            ArrayList<String> params = new ArrayList<String>();
                            final HashMap<String, String> user = session.getUserDetails();
                            String idUsuario = user.get(SessionManagement.KEY_PD_ID);
                            params.add("5");
                            params.add("RecoveryPassword.php");
                            params.add(tokenCTE);
                            params.add(idUsuario);

                            response = new Comunication(recuperar.this).execute(params).get();

                            Log.e("Response", "Falla: " + response);
                            if (response.getJSONObject(0).has("error")) {
                                Log.e("Response Falla: ", "ERROR");
                                tvErrorRecuperar.setText("Error al enviar reporte.");

                            } else if (response.getJSONObject(0).has("resp")) {
                                String resp = response.getJSONObject(0).get("resp").toString();
                                Log.e("Response Falla: ", resp);

                                if (resp.equals("true")) {
                                    Intent i = new Intent(recuperar.this, ActualizadosActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    tvErrorRecuperar.setText("Error al enviar reporte.");
                                    //Toast toast = Toast.makeText(falla.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                                    //toast.show();
                                }
                            }
                        } else {
                            tvErrorRecuperar.setText("No hay conexión a internet.");

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
                Intent i = new Intent(recuperar.this, ActualizarActivity.class);
                startActivity(i);

                // Handle the camera action
            } else if (id == R.id.nav_gallery) {
                Intent i = new Intent(recuperar.this, pinActivity.class);
                startActivity(i);


            } else if (id == R.id.nav_slideshow) {
                Intent i = new Intent(recuperar.this, preferencias.class);
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
       //if (id == R.id.action_favorite) {
       //    return true;
       //}

        return super.onOptionsItemSelected(item);




    }
}
