package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.GetNetImage;
import Utitilies.SessionManagement;

public class activity_detalle_video extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;
    SessionManagement session;
    JSONArray responseArray;
    private Bitmap loadedImage;
    Boolean siguiente = false,primero = true;
    String tokenCTE = "";

    // Insert your Video URL
    String VideoURL = "";
    String s_id = "";
    String s_titulo = "";
    String s_img_previa = "";
    String s_url_video = "";
    String s_duracion = "";
    String result11 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_video);

        TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
        if(breadcrumComunicado != null) {
            breadcrumComunicado.setText("   COMUNICACIÓN INTERNA > VIDEO");
            Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/media.otf");
            Typeface tfl = Typeface.createFromAsset(this.getAssets(), "fonts/ligera.otf");
            breadcrumComunicado.setTypeface(tfl);

            breadcrumComunicado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.breadci, 0, 0, 0);
            breadcrumComunicado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity_detalle_video.this, ComunicacionInternaActivity.class);
                    i.putExtra("direccion","5");
                    startActivity(i);
                }
            });
        }

        session = new SessionManagement(getApplicationContext());
        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        TextView tvTituloSiguiente = (TextView) findViewById(R.id.tvTituloSiguiente);
        TextView tvTiempoSiguiente = (TextView) findViewById(R.id.tvTiempoSiguiente);
        ImageView imgPreviaSiguiente = (ImageView) findViewById(R.id.imgPreviaSiguiente);
        TextView txtTituloVideo = (TextView) findViewById(R.id.tvTituloVideo);
        LinearLayout btnSiguiente = (LinearLayout) findViewById(R.id.btnSiguiente);
        ImageView btnPause = (ImageView) findViewById(R.id.btnPause);
        ImageView btnPlay = (ImageView) findViewById(R.id.btnPlay);


        String id = "";
        String titulo = "";
        String img_previa = "";
        String url_video = "";
        String duracion = "";


        String video_id = getIntent().getStringExtra("video_id");

        Log.e("id_video_DETALLE",video_id);

       try {
           String video = session.getVideoDetails();

           if (video == null || video == "") {

               ProgressBar pBar = (ProgressBar) findViewById(R.id.loadingPanelVideoDetalle);
               if (pBar != null) pBar.setVisibility(View.VISIBLE);

               params.add("6");
               params.add("GetVideo.php");
               params.add(tokenCTE);
               params.add(region);

               responseArray = new Comunication(activity_detalle_video.this).execute(params).get();
               if (pBar != null) pBar.setVisibility(View.GONE);

           } else {
               Log.e("Con session VIDEO", video);
               result11 = video;
               if (result11.equals("true" + "\n")) {
                   responseArray = new JSONArray("[{'resp':'true'}]");
               } else if (result11.equals("false" + "\n")) {
                   responseArray = new JSONArray("[{'resp':'false'}]");
               } else {
                   if (result11.contains("["))
                       responseArray = new JSONArray(result11);
                   else
                       responseArray = new JSONArray("[" + result11 + "]");
               }
           }


           if (responseArray.getJSONObject(0).has("resp")) {
               Log.e("Item Video Detalle", "Error");
           } else {

               for (int i = 0; i < responseArray.length(); i++) {

                   id = responseArray.getJSONObject(i).get("id").toString();
                   titulo = responseArray.getJSONObject(i).get("titulo").toString();
                   img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                   url_video = responseArray.getJSONObject(i).get("url_video").toString();
                   duracion = responseArray.getJSONObject(i).get("duracion").toString();

                   Log.e(i + "_a_id", id);
                   Log.e(i + "_a_titulo", titulo);
                   Log.e(i + "_a_img_previa", img_previa);
                   Log.e(i + "_a_url_video", url_video);
                   Log.e(i + "_a_duracion", duracion);

                   if (siguiente) {
                       siguiente = false;
                       Log.e("Siguiente", "Entro");
                       s_id = id;
                       s_titulo = titulo;
                       s_img_previa = img_previa;
                       s_url_video = url_video;
                       s_duracion = duracion;

                   } else if (primero) {
                       primero = false;
                       Log.e("Primero", "Entro");
                       s_id = id;
                       s_titulo = titulo;
                       s_img_previa = img_previa;
                       s_url_video = url_video;
                       s_duracion = duracion;
                   }

                   if (id.equals(video_id)) {
                       Log.e("Elegido", titulo);
                       siguiente = true;

                       if (txtTituloVideo != null)
                           txtTituloVideo.setText(titulo);
                       VideoURL = url_video;
                   }
               }
           }


       } catch (JSONException e) {
           Log.e("Error Async 0 Video", e.getMessage());
           e.printStackTrace();
       } catch (InterruptedException e) {
           Log.e("Error Async 1 Video", e.getMessage());
           e.printStackTrace();
       } catch (ExecutionException e) {
           Log.e("Error Async 2 Video", e.getMessage());
           e.printStackTrace();
       }


        Log.e ("URL Activity", VideoURL);
        VideoURL = VideoURL.replace(" ","%20");

        // Create a progressbar
        pDialog = new ProgressDialog(activity_detalle_video.this);
        // Set progressbar title

        // Set progressbar message
        pDialog.setMessage("Cargando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);

        if(tvTituloSiguiente!=null)
            tvTituloSiguiente.setText(s_titulo);

        if(tvTiempoSiguiente!=null)
            tvTiempoSiguiente.setText(s_duracion);

        if(btnSiguiente != null)
            btnSiguiente.setTag(s_id);

        if(imgPreviaSiguiente!=null) {
            Bitmap imagen = null;
            try {
                imagen = new GetNetImage().execute(s_img_previa).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            imgPreviaSiguiente.setImageBitmap(imagen);
        }

        // Execute StreamVideo AsyncTask


        try {
            // Start the MediaController
         MediaController mediacontroller = new MediaController(
                 activity_detalle_video.this);
         mediacontroller.setAnchorView(videoview);
         // Get the URL from String VideoURL
         videoview.setMediaController(mediacontroller);

            Uri video = Uri.parse(VideoURL);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();


        }
        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

        if (btnSiguiente != null) {
            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_video = v.getTag().toString();
                    Log.e("Id_videpo_clic aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", id_video);
                    Intent i = new Intent(activity_detalle_video.this, activity_detalle_video.class);
                    i.putExtra("video_id",id_video);
                    startActivity(i);
                }
            });
        }
    //    if (btnPause != null) {
    //        btnPause.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
//
//
    //            }
    //        });
    //    }
    //    if (btnPlay != null) {
    //        btnPlay.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                videoview.resume();
//
    //            }
    //        });
    //    }


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
                Intent i = new Intent(activity_detalle_video.this, MainActivity.class);
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
                Intent i = new Intent(activity_detalle_video.this, triviasActivity.class);
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
            Intent i = new Intent(activity_detalle_video.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_detalle_video.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           /* Intent i = new Intent(activity_detalle_video.this, preferencias.class);
            startActivity(i);*/

        } else if (id == R.id.nav_send) {
            session.logoutUser();
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}