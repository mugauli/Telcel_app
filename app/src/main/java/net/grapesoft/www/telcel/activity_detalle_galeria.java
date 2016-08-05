package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class activity_detalle_galeria extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManagement session;

    //Slider

    private ImageSwitcher imageSwitcher;

    private int[] gallery = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f };

    private ArrayList<String> imagenes_slider = new ArrayList<String>();

    private ArrayList<Drawable> imagenes_slider_drawable = new ArrayList<Drawable>();

    private int position,descargado=0;

    private static final Integer DURATION = 2500;

    private Timer timer = null;

    private String idSiguiente2;

    //Slider End


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_galeria);

        TextView breadcrumComunicado = (TextView) findViewById(R.id.breadcrumComunicado);
        if(breadcrumComunicado != null) {
            breadcrumComunicado.setText("COMUNICACIÓN INTERNA > GALERÍA");

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

        startSlider();

        //Slider

        session = new SessionManagement(getApplicationContext());

        //String imagen = getIntent().getStringExtra("imagen");
        imagenes_slider = getIntent().getStringArrayListExtra("imagenes_slider");
        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");
        final String jsonSiguiente = getIntent().getStringExtra("json");
         idSiguiente2 = getIntent().getStringExtra("idSiguiente");


        String imgS = getIntent().getStringExtra("imagenSig");
        String textoS = getIntent().getStringExtra("textoSig");
        String tituloS = getIntent().getStringExtra("tituloSig");


        Log.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",imgS);

        TextView txtTituloSiguiente =  (TextView) findViewById(R.id.txtTituloSiguiente);
        txtTituloSiguiente.setText(tituloS);
        TextView  txtDuracionSiguente  = (TextView) findViewById(R.id.txtDuracionSiguente);
        txtDuracionSiguente.setText(Html.fromHtml(textoS));

        ImageView imagenGaleriaSiguiente = (ImageView) findViewById(R.id.imagenGaleriaSiguiente);

        try {
            Bitmap img = new GetNetImage().execute(imgS).get();
            if(img != null)
                imagenGaleriaSiguiente.setImageBitmap(img);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TextView titUG = (TextView) findViewById(R.id.titDNT);
        TextView descUG = (TextView) findViewById(R.id.descDNT);

        titUG.setText(titulo);
        if(descripcion != null)
        descUG.setText(Html.fromHtml(descripcion));


        Log.e("Numero de Imagenes Galeria detalle", ": " + imagenes_slider.size());

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


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final LinearLayout lnySiguienteGaleria = (LinearLayout) findViewById(R.id.lnySiguienteGaleria);
        if (lnySiguienteGaleria != null) {
            lnySiguienteGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                                    String idSiguiente = "0",tituloSig="",imagenSig="",textoSig ="";
                                    for (int i = 0; i < responseArray.length(); i++) {



                                        String id = responseArray.getJSONObject(i).get("id").toString();
                                        Log.e("item",responseArray.getJSONObject(i).toString());
                                        Log.e("Idsiguiente",id+" "+idSiguiente2);

                                        if(id.equals(idSiguiente2)) {
                                            Log.e("Idsiguiente2",id+" "+idSiguiente2);

                                            String titulo1 = responseArray.getJSONObject(i).get("titulo").toString();
                                            String img_previa = responseArray.getJSONObject(i).get("img_previa").toString();
                                            String url = responseArray.getJSONObject(i).get("url").toString();
                                            String texto = responseArray.getJSONObject(i).get("texto").toString();

                                            if (i+1  < responseArray.length()) {

                                                idSiguiente = responseArray.getJSONObject(i + 1).get("id").toString();

                                                tituloSig = responseArray.getJSONObject(i+1).get("titulo").toString();
                                                imagenSig = responseArray.getJSONObject(i+1).get("img_previa").toString();
                                                textoSig = responseArray.getJSONObject(i+1).get("texto").toString();


                                            } else {
                                                idSiguiente = responseArray.getJSONObject(0).get("id").toString();
                                                tituloSig = responseArray.getJSONObject(0).get("titulo").toString();
                                                imagenSig = responseArray.getJSONObject(0).get("img_previa").toString();
                                                textoSig = responseArray.getJSONObject(0).get("texto").toString();
                                            }
                                            JSONArray imagenes_slide = responseArray.getJSONObject(0).getJSONArray("imagenes_slide");

                                            ArrayList<String> imagenes_slider1 = new ArrayList<String>();


                                            for (int ii = 0; ii < imagenes_slide.length(); ii++) {

                                                imagenes_slider1.add(imagenes_slide.getJSONObject(ii).get("url_img").toString());
                                            }

                                       // TextView titUG2 = (TextView) findViewById(R.id.titDNT);
                                       // TextView descUG2 = (TextView) findViewById(R.id.descDNT);
                                       // imagenes_slider.clear();
                                       // imagenes_slider_drawable.clear();
                                       // position = 0;
                                       // imagenes_slider = getIntent().getStringArrayListExtra("imagenes_slider");
                                       // titUG2.setText(titulo1);
                                       // descUG2.setText(Html.fromHtml(texto));

                                            Log.e("Prubea",idSiguiente2 + " " + idSiguiente);

                                           Intent intent = new Intent(activity_detalle_galeria.this, activity_detalle_galeria.class);
//
                                           intent.putExtra("titulo", titulo1);
                                           intent.putExtra("descripcion", texto);
                                           intent.putStringArrayListExtra("imagenes_slider", imagenes_slider1);
                                           intent.putExtra("json", jsonSiguiente);
                                           intent.putExtra("idSiguiente", idSiguiente);

                                            intent.putExtra("imagenSig",imagenSig);
                                            intent.putExtra("tituloSig",tituloSig);
                                            intent.putExtra("textoSig",textoSig);

                                           startActivity(intent);

                                        }
                                    }

                                    //idSiguiente2 =  idSiguiente;



                                }



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        startSlider();
    }

    public void stop(View button) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        position++;
                        if (position == imagenes_slider.size()) {
                            position = 0;
                        }

                        if(imagenes_slider_drawable.size() > position) {
                            imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
//                            Log.e("Imagenes Del Objeto",imagenes_slider.get(position));
                        }
                        else {
                            try {
        //                        Log.e("Imagenes Galeria",imagenes_slider.get(position));
                                Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                                if (img != null) {
                                    BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                                    imagenes_slider_drawable.add(bmDraw);
                                    imageSwitcher.setImageDrawable(bmDraw);

                                }
                                else
                                    imageSwitcher.setImageResource(R.drawable.noimage);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                });
            }

        }, 0, DURATION);
    }

    public void backSlider(View button) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (position == 0) {
            position = imagenes_slider.size()-1;
        }
        else
        {
            position--;
        }

        if(imagenes_slider_drawable.size() > position) {
            imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
            Log.e("Imagenes Del Objeto",imagenes_slider.get(position));
        }
        else {
            try {
                Log.e("Imagenes Galeria",imagenes_slider.get(position));
                Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                if (img != null) {
                    BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                    imagenes_slider_drawable.add(bmDraw);
                    imageSwitcher.setImageDrawable(bmDraw);

                }
                else
                    imageSwitcher.setImageResource(R.drawable.noimage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }


    }

    public void fwdSlider(View button) {
        position++;
        if (position == imagenes_slider.size()) {
            position = 0;
        }
        if(imagenes_slider_drawable.size() > position) {
            imageSwitcher.setImageDrawable(imagenes_slider_drawable.get(position));
            Log.e("Imagenes Del Objeto",imagenes_slider.get(position));
        }
        else {
            try {
Log.e("Position",""+position);
                Bitmap img = new GetNetImage().execute(imagenes_slider.get(position)).get();
                if (img != null) {
                    BitmapDrawable bmDraw = new BitmapDrawable(getResources(), img);
                    imagenes_slider_drawable.add(bmDraw);
                    imageSwitcher.setImageDrawable(bmDraw);

                }
                else
                    imageSwitcher.setImageResource(R.drawable.noimage);
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
            startSlider();
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
            Intent i = new Intent(activity_detalle_galeria.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           /* Intent i = new Intent(activity_detalle_galeria.this, preferencias.class);
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
