package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.SessionManagement;

public class activity_respuesta_trivia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONArray responseArray2;
    SessionManagement session;
    public String tokenCTE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_trivia);
        tokenCTE = getText(R.string.tokenXM).toString();


        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();


        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_respuesta_trivia.this,ayuda.class);
                startActivity(intent);
            }
        });
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        txtGhost4.setTypeface(tfm);
        txtGhost4.setText("TRIVIAOK");
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
                    Intent i = new Intent(activity_respuesta_trivia.this, MainActivity.class);
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

        if (imgButton2 != null) {
            imgButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity_respuesta_trivia.this, triviasActivity.class);
                    startActivity(i);
                }
            });
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        String puntos = getIntent().getExtras().getString("puntos", "0");
        String trivia = getIntent().getExtras().getString("trivia", "0");


        ArrayList<String> params = new ArrayList<String>();

        params.add("9");
        params.add("SaveWinner.php");
        params.add(tokenCTE);
        params.add(trivia);
        params.add(user.get(SessionManagement.KEY_PD_ID));
        params.add(user.get(SessionManagement.KEY_PD_REGION));
        params.add(puntos);

        TextView tyxtErrorTrivias = (TextView) findViewById(R.id.tyxtErrorTrivias);

        tyxtErrorTrivias.setText("");

        try {
            JSONArray response = new Comunication(activity_respuesta_trivia.this).execute(params).get();

            if(response.getJSONObject(0).has("error")) {
                Log.e("Response Actualizar: ", "ERROR");
                //Toast toast = Toast.makeText(activity_respuesta_trivia.this, "Error al actualizar la información de trivia", Toast.LENGTH_LONG);
                //toast.show();
                tyxtErrorTrivias.setText("Error al actualizar la información de trivia");
            }

        } catch (InterruptedException e) {
            tyxtErrorTrivias.setText("Error al actualizar la información de trivia");
            e.printStackTrace();
        } catch (ExecutionException e) {
            tyxtErrorTrivias.setText("Error al actualizar la información de trivia");
            e.printStackTrace();
        } catch (JSONException e) {
            tyxtErrorTrivias.setText("Error al actualizar la información de trivia");
            e.printStackTrace();
        }

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
            Intent i = new Intent(activity_respuesta_trivia.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_respuesta_trivia.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           /* Intent i = new Intent(activity_respuesta.this, preferencias.class);
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
}
