package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class activity_detalle_comunicado extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

        SessionManagement session;



@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comunicado);

    TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
    Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/media.otf");
    Typeface tfl = Typeface.createFromAsset(this.getAssets(), "fonts/ligera.otf");
    breadcrumComunicado.setTypeface(tfl);
    if(breadcrumComunicado != null) {
        breadcrumComunicado.setText("   COMUNICACIÓN INTERNA > COMUNICADOS");
        breadcrumComunicado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.breadci, 0, 0, 0);

        breadcrumComunicado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_detalle_comunicado.this, ComunicacionInternaActivity.class);
                i.putExtra("direccion","1");
                startActivity(i);
            }
        });
    }

        session = new SessionManagement(getApplicationContext());

        String imagen = getIntent().getStringExtra("imagen");
        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");

        ImageView imagenUG = (ImageView) findViewById(R.id.imagenDUC);
        TextView titUG = (TextView) findViewById(R.id.titDUC);
        TextView descUG = (TextView) findViewById(R.id.descDUC);

        try {
            Bitmap img = new GetNetImage().execute(imagen).get();
            if(img != null)
                imagenUG.setImageBitmap(img);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        titUG.setText(titulo);
        descUG.setText(Html.fromHtml(descripcion));


        Log.e("Imagen", imagen);
    /*TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
    if(breadcrumComunicado != null)
        breadcrumComunicado.setText("COMUNICACIÓN INTERNA > COMUNICADOS");*/
//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    toolbar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(activity_detalle_comunicado.this, MainActivity.class);
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
            Intent i = new Intent(activity_detalle_comunicado.this, triviasActivity.class);
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
            Intent i = new Intent(activity_detalle_comunicado.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_detalle_comunicado.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            /*Intent i = new Intent(activity_detalle_comunicado.this, preferencias.class);
            startActivity(i);*/

        } else if (id == R.id.nav_send) {
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
