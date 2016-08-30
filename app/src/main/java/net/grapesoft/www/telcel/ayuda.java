package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by Mugauli on 01/06/2016.
 */
public class ayuda  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    final Context context = this;
    SessionManagement session;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        session = new SessionManagement(getApplicationContext());
        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(R.drawable.arrow, getString(R.string.preguntas), getString(R.string.preguntasdesc)));
        datos.add(new Lista_Entrada(R.drawable.arrow, getString(R.string.fallas),getString(R.string.fallasdesc)));
        datos.add(new Lista_Entrada(R.drawable.arrow, getString(R.string.sugerencias), getString(R.string.sugerenciasdesc)));
        datos.add(new Lista_Entrada(R.drawable.arrow, getString(R.string.acercade),""));
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        txtGhost4.setTypeface(tfi);
        txtGhost4.setText("AYUDA");

       // Drawable img = this.getResources().getDrawable( R.drawable.ayudat);
        //img.setBounds( 0, 0, 50, 50 );
        //txtGhost4.setCompoundDrawables( img, null, null, null );



        lista = (ListView) findViewById(R.id.ayuda);
        lista.setAdapter(new List_adapted(this, R.layout.entrada_lista, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/obscura.otf");
                    texto_superior_entrada.setTypeface(tf);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfi);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());
                }
            }
        });

        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_Entrada elegido = (Lista_Entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();

                if(elegido.get_textoEncima() == getString(R.string.preguntas) ) {
                    Intent i = new Intent(ayuda.this, FaqActivity.class);
                    startActivity(i);
                }else if(elegido.get_textoEncima() == getString(R.string.fallas))
                {
                    Intent i = new Intent(ayuda.this, FallaActivity.class);
                    startActivity(i);

                }else if(elegido.get_textoEncima() == getString(R.string.sugerencias))
                {
                    Intent i = new Intent(ayuda.this, SugerenciasActivity.class);
                    startActivity(i);
                }else if(elegido.get_textoEncima() == getString(R.string.acercade))
                {
                    Intent i = new Intent(ayuda.this,acercade.class );
                    startActivity(i);
                }else
                {
                    Toast toast = Toast.makeText(ayuda.this, texto, Toast.LENGTH_LONG);
                    toast.show();
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
                Intent i = new Intent(ayuda.this, LoginActivity.class);
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
       /* ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ayuda.this, triviasActivity.class);
                startActivity(i);
            }
        });*/

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
            Intent i = new Intent(ayuda.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(ayuda.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           Intent i = new Intent(ayuda.this, preferencias.class);
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
