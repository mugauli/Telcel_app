package net.grapesoft.www.telcel;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;

import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class preferencias extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";
    CheckBox rb1,rb2,rb3,rb4,rb5;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        txtGhost4.setTypeface(tfi);
        txtGhost4.setText("TEMAS DE INTERÉS");

        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        final String int1 = user.get(SessionManagement.KEY_PD_INTERES_1);
        final String int2 = user.get(SessionManagement.KEY_PD_INTERES_2);
        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preferencias.this,ayuda.class);
                startActivity(intent);
            }
        });
        //boton ayuda

        //ListView
        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(1, getString(R.string.Accesorios)));
        datos.add(new Lista_Entrada(2,getString(R.string.Autos)));
        datos.add(new Lista_Entrada(3, getString(R.string.Vivienda)));
        datos.add(new Lista_Entrada(4,getString(R.string.Educacion)));
        datos.add(new Lista_Entrada(5,getString(R.string.Entretenimiento)));
        datos.add(new Lista_Entrada(6,getString(R.string.Restaurantes)));
        datos.add(new Lista_Entrada(7,getString(R.string.Salud)));
        datos.add(new Lista_Entrada(8,getString(R.string.Tiendas)));
        datos.add(new Lista_Entrada(9,getString(R.string.Viajes)));

        lista = (ListView) findViewById(R.id.sitioslista);
        lista.setAdapter(new List_adapted(this, R.layout.entrada_preferencias, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    int cont = 0;
                    int s = ((Lista_Entrada) entrada).get_idradio();
                    String ss = Integer.toString(s);
                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.list_one_toggler);
                    Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfi);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());
                    CheckBox ch = (CheckBox) view.findViewById(R.id.checkBox1);
                    //Toast.makeText(view.getContext(), ss + ".. "+ int1 + "..." + int2  , Toast.LENGTH_SHORT).show();
                    if(int1.compareTo(ss) == 0 || int2.compareTo(ss) == 0){

                        ch.setChecked(true);
                        cont=2;

                    }


                }
            }
        });

        //

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
                Intent i = new Intent(preferencias.this, MainActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });

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
        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(preferencias.this, triviasActivity.class);
                startActivity(i);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu

        Button btnEnviarFalla;
        btnEnviarFalla = (Button) findViewById(R.id.btnGuardar);

        // add button listener
        if (btnEnviarFalla != null) {
            btnEnviarFalla.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    JSONArray response;

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
            Intent i = new Intent(preferencias.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(preferencias.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           Intent i = new Intent(preferencias.this, preferencias.class);
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

