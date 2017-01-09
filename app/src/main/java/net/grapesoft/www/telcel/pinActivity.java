package net.grapesoft.www.telcel;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class pinActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        SessionManagement session;
    public String tokenCTE = "";
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        //Analytics
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Cambiar el pin de acceso");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Set the log level to verbose.
        GoogleAnalytics.getInstance(this).getLogger()
                .setLogLevel(Logger.LogLevel.VERBOSE);
        //
        session = new SessionManagement(getApplicationContext());
        tokenCTE = getText(R.string.tokenXM).toString();

        TextView texto_superior_entrada = (TextView) findViewById(R.id.tit1);
        TextView texto_superior = (TextView) findViewById(R.id.tit2);

        int colorBlue = getResources().getColor(R.color.ColorPrimaryDark);

        // here we set the color

        String uss = this.getResources().getString(R.string.acceso1);
                Button btn=(Button) findViewById(R.id.btnGenerar);
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/obscura.otf");
        Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        texto_superior_entrada.setTypeface(tfl);
        texto_superior.setTypeface(tfl);


        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableStringBuilder builderg = new SpannableStringBuilder();
        String us = this.getResources().getString(R.string.acceso3);
        /*SpannableString redSpannable= new SpannableString(us);
        SpannableString gSpannable= new SpannableString(uss);
        redSpannable.setSpan(new ForegroundColorSpan(colorBlue), 0, us.length(), 0);
        builder.append(redSpannable);
        builderg.append(gSpannable);
        texto_superior_entrada.setText( builder,TextView.BufferType.SPANNABLE );*/
        String text = "Al generar un nuevo pin o contraseña este te llegará a tu <font color=\"#3F51B5\"><bold>correo electrónico corporativo.</bold></font>";
        texto_superior_entrada.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        btn.setTypeface(tfm);
        txtGhost4.setTypeface(tfm);
        txtGhost4.setText("CAMBIAR PIN");

       /* ImageView btnAyuda = (ImageView) findViewById(R.id.ayudaint);
        if(btnAyuda != null)
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(pin.this,ayuda.class);
                startActivity(intent);
            }
        });*/
        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pinActivity.this,ayuda.class);
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
                Intent i = new Intent(pinActivity.this, MainActivity.class);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pinActivity.this, triviasActivity.class);
                startActivity(i);
            }
        });
        //ToolBar Menu

        Button btnGenerar;
        btnGenerar = (Button) findViewById(R.id.btnGenerar);

        // add button listener
        if (btnGenerar != null) {
            btnGenerar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    JSONArray response;

                    TextView tvErrorPin = (TextView) findViewById(R.id.tvErrorPin);
                    tvErrorPin.setText("");

                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    Boolean isInternetPresent = cd.isConnectingToInternet();

                    try {
                        if (isInternetPresent) {
                            tvErrorPin.setText("");

                            ArrayList<String> params = new ArrayList<String>();
                            final HashMap<String, String> user = session.getUserDetails();
                            String idUsuario = user.get(SessionManagement.KEY_PD_ID);
                            String reg = user.get(SessionManagement.KEY_PD_REGION);
                            String pass = user.get(SessionManagement.KEY_PD_PATERNO);


                            params.add("7");
                            params.add("ChangePassword.php");
                            params.add(tokenCTE);
                            params.add(reg);
                            params.add(idUsuario);


                            response = new Comunication(pinActivity.this).execute(params).get();

                            Log.e("Response", "PIN: " + response);
                            if (response.getJSONObject(0).has("error")) {
                                Log.e("Response Falla: ", "ERROR");
                                tvErrorPin.setText("Error al recuperar contraseña.");

                            } else if (response.getJSONObject(0).has("respuesta")) {
                                String resp = response.getJSONObject(0).get("respuesta").toString();
                                Log.e("Response Falla: ", resp);

                                if (resp.equals("1")) {

                                    new AlertDialog.Builder(pinActivity.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Telcel #Nosune")
                                            .setMessage("PIN de acceso cambiado correctamente")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    session.logoutUser();
                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }

                                            })
                                            .show();


                                } else {
                                    tvErrorPin.setText("Error al enviar reporte.");
                                    //Toast toast = Toast.makeText(falla.this, "Error al actualiar los datos.", Toast.LENGTH_LONG);
                                    //toast.show();
                                }
                            }
                        } else {
                            tvErrorPin.setText("No hay conexión a internet.");
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


        if (id == R.id.nav_camera) {
            Intent i = new Intent(pinActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(pinActivity.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(pinActivity.this, preferencias.class);
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
    }


}
