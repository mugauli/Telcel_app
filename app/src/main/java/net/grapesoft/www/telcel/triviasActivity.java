package net.grapesoft.www.telcel;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.liuguangqiang.swipeback.SwipeBackLayout;

//import java.sql.Date;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import Utitilies.SessionManagement;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class triviasActivity extends SwipeBackActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int totalCount=0;
    private Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivias);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        session = new SessionManagement(getApplicationContext());
        //Analytics
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Trivias - Preguntas del día");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Set the log level to verbose.
        GoogleAnalytics.getInstance(this).getLogger()
                .setLogLevel(Logger.LogLevel.VERBOSE);
        //

       //Dates

        String valid_until = "08:00";
        Calendar d = Calendar.getInstance();
        SimpleDateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDated = dfd.format(d.getTime());
        String fechaini = formattedDated + " " + valid_until;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        //int seconds = dt.getSeconds();
        String curTime = hours + ":" + minutes;

        /*SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("ID",totalCount);
        editor.putString("Fecha",fechaini);
        editor.putString("FechaHoy",formattedDate);
        editor.apply();*/



       // SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //Integer restoredText = prefs.getInt("Contador",0);
       // totalCount++;
        //prefs.edit().putInt("ID",totalCount).commit();
       // String restoredFecha = prefs.getString("Fecha", null);
        /*if (restoredText != null)
        {*/
           // Toast.makeText(this, totalCount, Toast.LENGTH_SHORT).show();
        //}

        // formattedDate have current date/time

        //Toast.makeText(this, formattedDate + "..." + fechaini, Toast.LENGTH_SHORT).show();
        //Data base
       // SQLiteDatabase myDB= null;
        //String TableName = "fecha";

        //String Data="";
        /* Create a Database. */
        //try {
        //    myDB = this.openOrCreateDatabase("trivias", MODE_PRIVATE, null);

        /* Create a Table in the Database. */
        //    myDB.execSQL("CREATE TABLE IF NOT EXISTS "
        //            + TableName
        //            + " (Field1 VARCHAR, Field2 VARCHAR);");

        /* Insert data to a Table*/
         /*   myDB.execSQL("INSERT INTO "
                    + TableName
                    + " (Field1, Field2)"
                    + " VALUES (" + fechaini + ", '" + formattedDate + "');");*/

        //    ContentValues values = new ContentValues();
        //    values.put(formattedDate, fechaini);


        //    myDB.insert(TableName, null, values);

        /*retrieve data from database */
        //    Cursor co = myDB.rawQuery("SELECT * FROM " + TableName , null);

        //    int Column1 = co.getColumnIndex("Field1");
        //    int Column2 = co.getColumnIndex("Field2");


            // Check if our result was valid.
        //    co.moveToFirst();
        //    if (co != null) {
                // Loop through all Results
        //        do {
        //            String Name = co.getString(Column1);
        //            String Names = co.getString(Column2);
                   // Toast.makeText(this, Column1 + Column2, Toast.LENGTH_SHORT).show();
        //        }while(co.moveToNext());
        //    }

        //}
        //catch(Exception e) {
        //    Log.e("Error", "Error", e);
        //} finally {
        //    if (myDB != null)
        //        myDB.close();
        //}





            //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(triviasActivity.this,ayuda.class);
                startActivity(intent);
            }
        });
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        txtGhost4.setTypeface(tfm);
        TextView txtGhost5 = (TextView) findViewById(R.id.textView25);
        txtGhost5.setTypeface(tfm);
        txtGhost4.setText("PREGUNTA DEL DIA");
        //boton ayuda

        //Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (toolbar != null) {
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(triviasActivity.this, MainActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
            });
        }
// Find logo

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);
        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (imgButton != null) {
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
        }

       /* if (imgButton2 != null) {
            imgButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(triviasActivity.this, triviasActivity.class);
                    startActivity(i);
                }
            });
        }*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);


        params.add("GetTrivia.php");
        params.add("GetQuestion.php");
        params.add(tokenCTE);
        params.add(region);
        params.add(user.get(SessionManagement.KEY_PD_ID));

        new triviasActivityAsync(triviasActivity.this).execute(params);
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
            Intent i = new Intent(triviasActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(triviasActivity.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(triviasActivity.this, preferencias.class);
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

}
