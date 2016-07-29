package net.grapesoft.www.telcel;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import Utitilies.SessionManagement;

public class sitios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sitios);
        session = new SessionManagement(getApplicationContext());

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sitios.this,ayuda.class);
                startActivity(intent);
            }
        });
        //boton ayuda

        ImageView img1 =  (ImageView) findViewById(R.id.imageView7);
        ImageView img2 =  (ImageView) findViewById(R.id.imageView8);
        ImageView img3 =  (ImageView) findViewById(R.id.imageView9);
        ImageView img4 =  (ImageView) findViewById(R.id.imageView10);
        ImageView img5 =  (ImageView) findViewById(R.id.imageView11);
        ImageView img6 =  (ImageView) findViewById(R.id.imageView12);
        ImageView img7 =  (ImageView) findViewById(R.id.imageView13);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);


        tabs.addTab(tabs.newTab().setText(getString(R.string.tab6).toString()));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab1).toString()));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab2).toString()));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab3).toString()));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab5).toString()));


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAB",tab.getText().toString());

                //   if(tab.getText().toString().equals(getString(R.string.tab6).toString())) {

                //       Intent i = new Intent(MainActivity.this, bienvenidos.class);
                //       startActivity(i);
                //   }

                if(tab.getText().toString().equals(getString(R.string.tab1).toString())) {

                    Intent i = new Intent(sitios.this, ComunicacionInternaActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
                if(tab.getText().toString().equals(getString(R.string.tab2).toString())) {

                    Intent i = new Intent(sitios.this, ProductosActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
                if(tab.getText().toString().equals(getString(R.string.tab5).toString())) {

                    Intent i = new Intent(sitios.this, sitios.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            Intent i = new Intent(sitios.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(sitios.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(sitios.this, preferencias.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
            session.logoutUser();
            finish();
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
