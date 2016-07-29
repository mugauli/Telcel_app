package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.SessionManagement;

public class activity_detalle_sva extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManagement session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_sva);

        session = new SessionManagement(getApplicationContext());

        String imagen = getIntent().getStringExtra("imagen");
        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");

        ImageView imagenUG = (ImageView) findViewById(R.id.imagenDNT);
        TextView titUG = (TextView) findViewById(R.id.titDNT);
        TextView descUG = (TextView) findViewById(R.id.descDNT);

        try {
            Bitmap img = new GetNetImage().execute(imagen).get();
            if(img != null)
                imagenUG.setImageBitmap(img);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
        if(breadcrumComunicado != null)
            breadcrumComunicado.setText("PRODUCTOS Y SERVICIOS > SVA");
        titUG.setText(titulo);
        if(descripcion != null)
        descUG.setText(Html.fromHtml(descripcion));


        Log.e("Imagen", imagen);

//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            Intent i = new Intent(activity_detalle_sva.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_detalle_sva.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(activity_detalle_sva.this, preferencias.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
            session.logoutUser();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
