package net.grapesoft.www.telcel;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;

import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class preferencias extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";
    private ListView listaAccesorio,listaAutos;
    private String ch="nada";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        txtGhost4.setTypeface(tfi);
        txtGhost4.setText("TEMAS DE INTERÉS");

        session = new SessionManagement(getApplicationContext());
        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        datos.add(new Lista_Entrada(ch));

        //FUncionalidad Expandable
       final ExpandableRelativeLayout listOne =
                (ExpandableRelativeLayout) findViewById(R.id.list_one);

        final ExpandableRelativeLayout list_autos =
                (ExpandableRelativeLayout) findViewById(R.id.list_autos);


       View listOneToggler = findViewById(R.id.list_one_toggler);
       listOneToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOne.toggle();
                list_autos.collapse();

            }
        });
        listaAccesorio = (ListView) findViewById(R.id.mobile_list);
        listaAccesorio.setAdapter(new List_adapted(this, R.layout.entrada_preferencias, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font

                    CheckBox imagen_entrada = (CheckBox) view.findViewById(R.id.list_one_a);
                    if (imagen_entrada != null)
                       // imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());
                        imagen_entrada.setText(((Lista_Entrada) entrada).get_CheckBox());
                }
            }
        });

        //auto

        View listOneTogglerAutos = findViewById(R.id.autos);
        listOneTogglerAutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOne.collapse();
                list_autos.toggle();

            }
        });
        listaAccesorio = (ListView) findViewById(R.id.mobile_list_autos);
        listaAccesorio.setAdapter(new List_adapted(this, R.layout.entrada_preferencias, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font

                    CheckBox imagen_entrada = (CheckBox) view.findViewById(R.id.list_one_a);
                    if (imagen_entrada != null)
                        // imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());
                        imagen_entrada.setText(((Lista_Entrada) entrada).get_CheckBox());
                }
            }
        });

        //expandable


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
            Intent i = new Intent(preferencias.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           /* Intent i = new Intent(ayuda.this, preferencias.class);
            startActivity(i);*/

        } else if (id == R.id.nav_send) {
            session.logoutUser();
            Intent intent = new Intent(getApplicationContext(), login.class);
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

