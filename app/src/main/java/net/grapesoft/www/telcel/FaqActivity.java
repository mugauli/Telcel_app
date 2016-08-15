package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FaqActivity extends ActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    final Context context = this;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        session = new SessionManagement(getApplicationContext());
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        txtGhost4.setTypeface(tf);
        txtGhost4.setText("PREGUNTAS FRECUENTES");


        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(getString(R.string.uno), getString(R.string.unodesc)));
        datos.add(new Lista_Entrada(getString(R.string.dos),getString(R.string.dosdesc)));
        datos.add(new Lista_Entrada(getString(R.string.tres), getString(R.string.tresdesc)));
        datos.add(new Lista_Entrada(getString(R.string.cuatro),getString(R.string.cuatrodesc)));


        lista = (ListView) findViewById(R.id.faq);
        lista.setAdapter(new List_adapted(this, R.layout.entrada_faq, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
                    texto_superior_entrada.setTypeface(tf);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfl);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());


                }
            }
        });


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
                Intent i = new Intent(FaqActivity.this, MainActivity.class);
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
                Intent i = new Intent(FaqActivity.this, triviasActivity.class);
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
            Intent i = new Intent(FaqActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(FaqActivity.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           /* Intent i = new Intent(faq.this, preferencias.class);
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
