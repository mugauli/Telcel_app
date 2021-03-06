package net.grapesoft.www.telcel;

import android.content.Intent;
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

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import Utitilies.CustomViewPager;
import Utitilies.SessionManagement;

public class ProductosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_productos);
        session = new SessionManagement(getApplicationContext());
        //Analytics
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Productos y servicios");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Set the log level to verbose.
        GoogleAnalytics.getInstance(this).getLogger()
                .setLogLevel(Logger.LogLevel.VERBOSE);
        //
        final int direccion = Integer.parseInt(getIntent().getStringExtra("direccion"));
        Log.e("direccion","" + direccion);

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductosActivity.this,ayuda.class);
                startActivity(intent);
            }
        });
        //boton ayuda
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
                Intent i = new Intent(ProductosActivity.this, MainActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ProductosActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProductosActivity.this, MainActivity.class);
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

        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductosActivity.this, triviasActivity.class);
                startActivity(i);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu

        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabs.addTab(tabs.newTab().setText("LANZAMIENTOS"));
        tabs.addTab(tabs.newTab().setText("CAMPAÑAS PUBLICITARIAS"));
        tabs.addTab(tabs.newTab().setText("PRODUCTO DEL MES"));
        tabs.addTab(tabs.newTab().setText("SVA"));

        //tabs.setTabGravity(TabLayout.GRAVITY_FILL);

       // final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        /*final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(adapter);*/

        final CustomViewPager mViewPager = (CustomViewPager) findViewById(R.id.pager);
        mViewPager.setPagingEnabled(false);

        final PageAdapterProductos adapter = new PageAdapterProductos
                (getSupportFragmentManager(), tabs.getTabCount());
        mViewPager.setAdapter(adapter);




        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                Log.e("Posicion",""+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if(direccion != 0) {
          //  TabLayout.Tab tabAt = tabs.getTabAt(direccion - 1);
          //  Log.e("Nombre Tab", tabAt.getText().toString());
            new Handler().postDelayed(
                    new Runnable(){
                        @Override
                        public void run() {
                            tabs.getTabAt(direccion - 1).select();
                        }
                    }, 100);
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent i = new Intent(ProductosActivity.this, MainActivity.class);
            i.putExtra("direccion","0");
            startActivity(i);
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
            Intent i = new Intent(ProductosActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(ProductosActivity.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           Intent i = new Intent(ProductosActivity.this, preferencias.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
           /*session.logoutUser();
            finish();
            System.exit(0);*/

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
