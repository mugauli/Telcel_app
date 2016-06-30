package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.GetNetImage;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class VideoDetalleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;
    SessionManagement session;
    JSONArray responseArray;
    private Bitmap loadedImage;
    Boolean siguiente = false;
    String tokenCTE = "";

    // Insert your Video URL
    String VideoURL = "http://internetencaja.com.mx/telcel/videos/Grafica%20Informativa.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManagement(getApplicationContext());
        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_REGION);

        String video_id = getIntent().getStringExtra("video_id");

        Log.e("id_video",video_id);

        String result11 = "";
       try{
            String video = session.getVideoDetails();

            if(video == null || video == "") {

                ProgressBar pBar = (ProgressBar)findViewById(R.id.loadingPanelVideoDetalle);
                if(pBar != null) pBar.setVisibility(View.VISIBLE);

                params.add("6");
                params.add("GetVideo.php");
                params.add(tokenCTE);
                params.add(region);

                responseArray = new Comunication(VideoDetalleActivity.this).execute(params).get();

                if(pBar != null) pBar.setVisibility(View.GONE);

            } else
            {
                Log.e("Con session VIDEO",video);
                result11 = video;
                if(result11.equals("true"+"\n")) {
                    responseArray = new JSONArray("[{'resp':'true'}]");
                }else if(result11.equals("false"+"\n")) {
                    responseArray = new JSONArray("[{'resp':'false'}]");
                } else
                {
                    if(result11.contains("["))
                        responseArray = new JSONArray(result11);
                    else
                        responseArray = new JSONArray("[" + result11 + "]");
                }
            }



        if(responseArray.getJSONObject(0).has("resp")) {
            Log.e("Item Video Detalle" ,  "Error");
        }
        else {

            for (int i = 0; i < responseArray.length(); i++) {

                String id = responseArray.getJSONObject(i).get("id").toString();
                String titulo = responseArray.getJSONObject(i).get("titulo").toString();
                String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                String url_video = responseArray.getJSONObject(i).get("url_video").toString();
                String duracion = responseArray.getJSONObject(i).get("duracion").toString();

                if(siguiente)
                {
                    siguiente = false;
                    Log.e("Siguiente","Entro");
                    TextView tvTituloSiguiente = (TextView) findViewById(R.id.tvTituloSiguiente);
                    if(tvTituloSiguiente!=null)
                        tvTituloSiguiente.setText(titulo);
                    TextView tvTiempoSiguiente = (TextView) findViewById(R.id.tvTiempoSiguiente);
                    if(tvTiempoSiguiente!=null)
                        tvTiempoSiguiente.setText(duracion);
                    ImageView imgPreviaSiguiente = (ImageView) findViewById(R.id.imgPreviaSiguiente);
                    if(imgPreviaSiguiente!=null) {
                        Bitmap imagen = new GetNetImage().execute(img_previa).get();
                        imgPreviaSiguiente.setImageBitmap(imagen);
                    }
                }
                if(id.equals(video_id))
                {
                    siguiente = true;
                    TextView txtTituloVideo = (TextView) findViewById(R.id.tvTituloVideo);
                    Log.e("titulo",titulo);
                    if(txtTituloVideo != null)
                    txtTituloVideo.setText(titulo);
                   VideoURL = url_video;
                }
            }
        }


    } catch (JSONException e) {
        Log.e("Error Async 0 Video", e.getMessage());
        e.printStackTrace();
    } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (ExecutionException e) {
           e.printStackTrace();
       }


        Log.e ("URL Activity", VideoURL);
        VideoURL = VideoURL.replace(" ","%20");
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view);


        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(VideoDetalleActivity.this);
        // Set progressbar title

        // Set progressbar message
        pDialog.setMessage("Cargando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoDetalleActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
            Log.e ("URL Activity", "1");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        Log.e ("URL Activity", "2");
        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });


//Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
            Intent i = new Intent(VideoDetalleActivity.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(VideoDetalleActivity.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(VideoDetalleActivity.this, preferencias.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}