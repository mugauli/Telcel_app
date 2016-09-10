package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import Utitilies.SessionManagement;

public class acercade extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        session = new SessionManagement(getApplicationContext());

        //Fuentes
        TextView txtGhost = (TextView) findViewById(R.id.textView8);
        TextView txtGhost2 = (TextView) findViewById(R.id.textView9);
        TextView txtGhost3 = (TextView) findViewById(R.id.textView10);
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");

        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
       txtGhost4.setTypeface(tfm);
        txtGhost4.setText("ACERCA DE #NOSUNE");

//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

      /*  toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(acercade.this, LoginActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });*/
        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        /*imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });*/
        /*ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(acercade.this, triviasActivity.class);
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
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String idUsuario = user.get(SessionManagement.KEY_PD_ID);

        if(user.get(SessionManagement.KEY_PD_ID) != null) {
            if (id == R.id.nav_camera) {
                Intent i = new Intent(acercade.this, ActualizarActivity.class);
                startActivity(i);

                // Handle the camera action
            } else if (id == R.id.nav_gallery) {
                Intent i = new Intent(acercade.this, pinActivity.class);
                startActivity(i);


            } else if (id == R.id.nav_slideshow) {
                Intent i = new Intent(acercade.this, preferencias.class);
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
