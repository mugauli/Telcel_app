package net.grapesoft.www.telcel;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.SessionManagement;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManagement(getApplicationContext());

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ayuda.class);
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

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);
        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);

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

        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, trivias.class);
                startActivity(i);
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //ToolBar Menu
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

                    Intent i = new Intent(MainActivity.this, ComunicacionInternaActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
                if(tab.getText().toString().equals(getString(R.string.tab2).toString())) {

                    Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
                if(tab.getText().toString().equals(getString(R.string.tab5).toString())) {

                    Intent i = new Intent(MainActivity.this, sitios.class);
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

        //tabs.setTabGravity(TabLayout.GRAVITY_FILL);

      // final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
      // final PagerAdapter adapter = new PagerAdapter
      //         (getSupportFragmentManager(), tabs.getTabCount());
      // viewPager.setAdapter(adapter);
      // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
      // tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
       //     @Override
       //     public void onTabSelected(TabLayout.Tab tab) {

       //             viewPager.setCurrentItem(tab.getPosition());


       //     }

       //     @Override
       //     public void onTabUnselected(TabLayout.Tab tab) {

       //     }

       //     @Override
       //     public void onTabReselected(TabLayout.Tab tab) {

       //     }
       // });

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(MainActivity.this);

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetHome.php");
        params.add(tokenCTE);
        params.add(region);

        new MainActivityAsync(MainActivity.this).execute(params);
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
            Intent i = new Intent(MainActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(MainActivity.this, preferencias.class);
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
