package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.SessionManagement;

public class activity_detalle_galeria extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManagement session;
    private Tracker mTracker;
    //Slider

    private ImageSwitcher imageSwitcher;
    private ArrayList<String> imagenes_slider = new ArrayList<String>();
    private ArrayList<Drawable> imagenes_slider_drawable = new ArrayList<Drawable>();
    private int position = 0,descargado=0;
    private static final Integer DURATION = 6000;
    private Timer timer = null;
    private String idSiguiente2 = "0";
    private String idSiguienteR;
    private boolean soloUna = true,soloUna2 = true;
    //Slider End


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_galeria);
        //Analytics
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("GALERIA - detalle Link");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Set the log level to verbose.
        GoogleAnalytics.getInstance(this).getLogger()
                .setLogLevel(Logger.LogLevel.VERBOSE);
        //

        TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
        if(breadcrumComunicado != null) {
            breadcrumComunicado.setText("   COMUNICACIÓN INTERNA > GALERÍA");
            Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/media.otf");
            Typeface tfl = Typeface.createFromAsset(this.getAssets(), "fonts/ligera.otf");
            breadcrumComunicado.setTypeface(tfl);

            breadcrumComunicado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.breadci, 0, 0, 0);

            breadcrumComunicado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity_detalle_galeria.this, ComunicacionInternaActivity.class);
                    i.putExtra("direccion","7");
                    startActivity(i);
                }
            });
        }

        //Slider
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView imageView = new ImageView(activity_detalle_galeria.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                ImageSwitcher.LayoutParams params = new ImageSwitcher.LayoutParams(
                        ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT);

                imageView.setLayoutParams(params);
                return imageView;

               // return new ImageView(activity_detalle_galeria.this);
            }
        });
        /*TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
        if(breadcrumComunicado != null)
            breadcrumComunicado.setText("COMUNICACIÓN INTERNA > GALERIA");*/
        // Set animations
        // http://danielme.com/2013/08/18/diseno-android-transiciones-entre-activities/
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);


        //Slider

        session = new SessionManagement(getApplicationContext());


        final String jsonSiguiente = getIntent().getStringExtra("json");
        idSiguiente2 = getIntent().getStringExtra("idSiguiente");


        try {
            JSONArray responseArray = null;


            if (jsonSiguiente.contains("["))
                responseArray = new JSONArray(jsonSiguiente);
            else
                responseArray = new JSONArray("[" + jsonSiguiente + "]");

            if (responseArray.getJSONObject(0).has("resp")) {
                Log.e("Item Galeria", "Error");
            } else {

                if (responseArray.length() > 0) {
                    Log.e("Item Galeria JSON siguiente", idSiguiente2);
                    String idSiguiente = "0", tituloSig = "", imagenSig = "", textoSig = "",url = "";
                    for (int i = 0; i < responseArray.length(); i++) {
                        String id = responseArray.getJSONObject(i).get("id").toString();
                        if (id.equals(idSiguiente2)) {

                            Log.e("Idsiguiente2", id + " " + idSiguiente2);

                            String titulo1 = responseArray.getJSONObject(i).get("titulo").toString();
                            String texto = responseArray.getJSONObject(i).get("texto").toString();
                            url = responseArray.getJSONObject(i).get("url").toString();

                            JSONArray imagenes_slide = responseArray.getJSONObject(i).getJSONArray("imagenes_slide");

                            if (i + 1 == responseArray.length()) {
                                idSiguiente = responseArray.getJSONObject(0).get("id").toString();
                                tituloSig = responseArray.getJSONObject(0).get("titulo").toString();
                                imagenSig = responseArray.getJSONObject(0).get("img_previa").toString();
                                textoSig = responseArray.getJSONObject(0).get("texto").toString();
                            } else {
                                idSiguiente = responseArray.getJSONObject(i + 1).get("id").toString();
                                tituloSig = responseArray.getJSONObject(i + 1).get("titulo").toString();
                                imagenSig = responseArray.getJSONObject(i + 1).get("img_previa").toString();
                                textoSig = responseArray.getJSONObject(i + 1).get("texto").toString();
                            }


                            ArrayList<String> imagenes_slider1 = new ArrayList<String>();

                            for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                                imagenes_slider1.add(imagenes_slide.getJSONObject(ii).get("url_img").toString());
                            }

                            Log.e("Imagenes", imagenes_slide.toString());
//                                            Log.e("Prubea", idSiguiente2 + " " + idSiguiente);


                            TextView txtTituloSiguiente = (TextView) findViewById(R.id.txtTituloSiguiente);
                            txtTituloSiguiente.setText(Html.fromHtml(tituloSig));
                            ImageView imagenGaleriaSiguiente = (ImageView) findViewById(R.id.imagenGaleriaSiguiente);

                            idSiguienteR = idSiguiente;
                            imagenes_slider = imagenes_slider1;

                            try {
                                Bitmap img = new GetNetImage().execute(imagenSig).get();
                                if (img != null)
                                    imagenGaleriaSiguiente.setImageBitmap(img);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }


                            TextView titUG = (TextView) findViewById(R.id.titDNTG);
                            TextView descUG = (TextView) findViewById(R.id.descDNTG);
                            TextView txtDNT = (TextView) findViewById(R.id.textDNTG);


                            titUG.setText(Html.fromHtml(titulo1));
                            if(txtDNT != null)
                                txtDNT.setText(Html.fromHtml(texto.replace("<p>","").replace("</p>","").trim()));
                            if(descUG != null)
                                descUG.setText("Si deseas ver más fotos de esta galería visita nuestra página: "+ url);

                        }
                    }
                    //idSiguiente2 =  idSiguiente;
                }
            }

            startSlider();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Log.e("Numero de Imagenes Galeria detalle", ": " + imagenes_slider.size());
        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_detalle_galeria.this,ayuda.class);
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
                Intent i = new Intent(activity_detalle_galeria.this, MainActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

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

        ImageButton imgButton2 = (ImageButton) findViewById(R.id.btnTrivia);
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_detalle_galeria.this, triviasActivity.class);
                startActivity(i);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final LinearLayout lnySiguienteGaleria = (LinearLayout) findViewById(R.id.lnySiguienteGaleria);
        if (lnySiguienteGaleria != null) {
            lnySiguienteGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity_detalle_galeria.this, activity_detalle_galeria.class);
                    intent.putExtra("json", jsonSiguiente);
                    intent.putExtra("idSiguiente", idSiguienteR);
                    startActivity(intent);
                    finish();

                }

            });
        }

        //ToolBar Menu
    }

        //Slider

    public void start(View button) {
        if (timer != null) {
            timer.cancel();
        }
        position = 0;
       // startSlider();
    }

    public void stop(View button) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void startSlider() {

        if (soloUna) {




            soloUna = false;

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    // avoid exception:
                    // "Only the original thread that created a view hierarchy can touch its views"
                    runOnUiThread(new Runnable() {
                        public void run() {

                            if(soloUna2)
                            {
                                soloUna2 = false;
                                if (imagenes_slider_drawable.size() > position) {
                                    imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
                                    Log.e("Imagenes Del Objeto", imagenes_slider.get(position));
                                } else {
                                    try {
                                        Log.e("Galeria ps", position+"");
                                        Log.e("Imagenes Galeria", imagenes_slider.get(position));
                                        Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                                        if (img != null) {
                                            BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                                            imagenes_slider_drawable.add(bmDraw);
                                            imageSwitcher.setImageDrawable(bmDraw);

                                        } else
                                            imageSwitcher.setImageResource(R.drawable.noimage);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }else {

                                position++;
                                if (position == imagenes_slider.size()) {
                                    position = 0;
                                }

                                if (imagenes_slider_drawable.size() > position) {
                                    imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
                                    Log.e("Imagenes Del Objeto", imagenes_slider.get(position));
                                } else {
                                    try {
                                        Log.e("Imagenes Galeria", imagenes_slider.get(position));
                                        Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                                        if (img != null) {
                                            BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                                            imagenes_slider_drawable.add(bmDraw);
                                            imageSwitcher.setImageDrawable(bmDraw);

                                        } else
                                            imageSwitcher.setImageResource(R.drawable.noimage);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }

                    });
                }

            }, 0, DURATION);
        }
    }

    public void backSlider(View button) {

        if((imagenes_slider_drawable.size() == imagenes_slider_drawable.size()) || position > 1)
        {
            position--;

            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            if (position  < 0) {
                position = imagenes_slider.size() - 1;
            }

            if (imagenes_slider_drawable.size() > position) {
                try {
                    imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
                    Log.e("Imagenes Del Objeto", imagenes_slider.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Log.e("Imagenes Galeria", imagenes_slider.get(position));
                    Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                    if (img != null) {
                        BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                        imagenes_slider_drawable.add(bmDraw);
                        imageSwitcher.setImageDrawable(bmDraw);
                    } else
                        imageSwitcher.setImageResource(R.drawable.noimage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void fwdSlider(View button) {

        position++;
        if (position >= imagenes_slider.size()) {
            position = 0;
        }

        if (imagenes_slider_drawable.size() > (position)) {

            Log.e("Position", "" + position);
            Log.e("Imagenes Del Objeto", imagenes_slider.get(position));
            Log.e("Imagenes Del Objeto Drawable", imagenes_slider_drawable.get(position).toString());
            imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));

        } else {
            try {

                Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                if (img != null) {
                    BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                    imagenes_slider_drawable.add(bmDraw);
                    imageSwitcher.setImageDrawable(bmDraw);
                } else
                    imageSwitcher.setImageResource(R.drawable.noimage);
                Log.e("Position", "" + position);
                Log.e("Imagenes Del Agregada", img.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }



    }


    // Stops the slider when the Activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
           // startSlider();
        }

    }

    //Slider End

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
            Intent i = new Intent(activity_detalle_galeria.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_detalle_galeria.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(activity_detalle_galeria.this, preferencias.class);
            startActivity(i);

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
