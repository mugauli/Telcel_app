package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.SessionManagement;

public class activity_detalle_galeria extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManagement session;

    //Slider

    private ImageSwitcher imageSwitcher;

    private int[] gallery = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f };

    private ArrayList<String> imagenes_slider = new ArrayList<String>();

    private int position,descargado=0;

    private static final Integer DURATION = 2500;

    private Timer timer = null;

    //Slider End


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_galeria);

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

        ImageView imagenUG = (ImageView) findViewById(R.id.imagenDNT);
        TextView titUG = (TextView) findViewById(R.id.titDNT);
        TextView descUG = (TextView) findViewById(R.id.descDNT);

    //  try {
    //      Bitmap img = new GetNetImage().execute(imagen).get();
    //      if(img != null)
    //          imagenUG.setImageBitmap(img);

    //  } catch (InterruptedException e) {
    //      e.printStackTrace();
    //  } catch (ExecutionException e) {
    //      e.printStackTrace();
    //  }


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
                        //position++;
                        //if (position == imagenes_slider.size()) {
                        //    position = 0;
                        //}
                        //Uri imgUri = Uri.parse(imagenes_slider.get(position));
                        //imageSwitcher.setImageURI(imgUri);

                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                        imageSwitcher.setImageResource(gallery[position]);
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
      // if (position == 0) {
      //     position = imagenes_slider.size()-1;
      // }
      // else
      // {
      //     position--;
      // }
      // Uri imgUri = Uri.parse(imagenes_slider.get(position));
      // imageSwitcher.setImageURI(imgUri);

        if (position == 0) {
            position = gallery.length-1;
        }
        else
        {
            position--;
        }
        imageSwitcher.setImageResource(gallery[position]);

    }

    public void fwdSlider(View button) {
        position++;
        if (position == gallery.length) {
            position = 0;
        }
        imageSwitcher.setImageResource(gallery[position]);
       // position++;
       // if (position == imagenes_slider.size()) {
       //     position = 0;
       // }
       // Uri imgUri = Uri.parse(imagenes_slider.get(position));
       // imageSwitcher.setImageURI(imgUri);
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
            Intent i = new Intent(activity_detalle_galeria.this, preferencias.class);
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
