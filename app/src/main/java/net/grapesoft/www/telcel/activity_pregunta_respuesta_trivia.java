package net.grapesoft.www.telcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Interpolator;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.Pregunta;
import Utitilies.PreguntaElement;
import Utitilies.Respuestas;
import Utitilies.SessionManagement;

public class activity_pregunta_respuesta_trivia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONArray responseArray2;
    SessionManagement session;
    Boolean ultimo = false;
    String preguntasJSON;
    int siguiente=0;
    int puntos=0;
    String trivia;
    String imagen;
    ArrayList<Pregunta> preguntas = new ArrayList<>();
    RadioGroup group;
    ProgressDialog pDialog;
    private Timer timer = null;
    int DURATION=1000,tTranscurrido=0,tTotal=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_respuesta_trivia);

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_pregunta_respuesta_trivia.this,ayuda.class);
                startActivity(intent);
            }
        });
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        txtGhost4.setTypeface(tfm);
        txtGhost4.setText("TRIVIA");

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
                    Intent i = new Intent(activity_pregunta_respuesta_trivia.this, MainActivity.class);
                    i.putExtra("direccion","0");
                    startActivity(i);
                }
            });
        }

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
                    Intent i = new Intent(activity_pregunta_respuesta_trivia.this, triviasActivity.class);
                    startActivity(i);
                }
            });
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int height = metrics.heightPixels; // alto absoluto en pixels

        Log.e("Alto",""+height);
        if(height<1000) {
            TextView tiempo = (TextView) findViewById(R.id.txtTiempo);
            tiempo.setTextSize(17);
        }


        preguntasJSON = getIntent().getExtras().getString("preguntas","0");
        trivia = getIntent().getExtras().getString("trivia","0");
        imagen = getIntent().getExtras().getString("imagen","0");
        tTotal = Integer.parseInt(getIntent().getExtras().getString("duracion","0"))*60;
        group =  new RadioGroup(this);


        try {
            //Log.e("Response: ", "JSON");
            if (preguntasJSON.substring(0,3).contains("["))
                responseArray2 = new JSONArray(preguntasJSON);
            else
                responseArray2 = new JSONArray("[" + preguntasJSON + "]");

            if (responseArray2.getJSONObject(0).has("error")) {
                Log.e("Item Preguntas_Trivias", "Error");
            } else {
                if (responseArray2.length() > 0) {


                    for (int a = 0; a < responseArray2.length(); a++) {

                        int idPreg = Integer.parseInt(responseArray2.getJSONObject(a).get("idPreg").toString());
                        String txtPregunta = responseArray2.getJSONObject(a).get("txtPregunta").toString();
                        JSONArray respuestas = responseArray2.getJSONObject(a).getJSONArray("respuestas");

                        ArrayList<Respuestas> respuestaslts = new ArrayList<>();

                        for (int i = 0; i < respuestas.length(); i++) {

                            int idResp = Integer.parseInt(respuestas.getJSONObject(i).get("idResp").toString());
                            String txtRespuesta = respuestas.getJSONObject(i).get("txtRespuesta").toString();
                            Boolean valRespuesta = respuestas.getJSONObject(i).get("valRespuesta").toString().equals("1");
                            respuestaslts.add(new Respuestas(idResp,txtRespuesta,valRespuesta));
                        }
                        preguntas.add(new Pregunta(idPreg,txtPregunta,respuestaslts));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(preguntas.size() > 0)
        {
            group = cargaPregunta(preguntas.get(siguiente),imagen,activity_pregunta_respuesta_trivia.this);
            group.setOrientation(RadioGroup.VERTICAL);
            LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);
            lytRespuestas.removeAllViews();
            lytRespuestas.addView(group);

            final TextView btnEnviar = (TextView) findViewById(R.id.btnEnviar);
            if (btnEnviar != null) {

                btnEnviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                     //   Log.e("Enviar Siguiente",""+siguiente);
                     //   Log.e("Enviar Aciertos",""+puntos);
                     //   Log.e("Enviar Preguntas",""+preguntas.size());
                     //   Log.e("Enviar Ultimo",""+ultimo);

                        ultimo = (siguiente == preguntas.size());

                        TextView txtError = (TextView) findViewById(R.id.txtError);

                        int radioButtonID = group.getCheckedRadioButtonId();

                        Log.e("elegido", "" + radioButtonID);
                        if (radioButtonID != -1) {
                            txtError.setText("");
                            View radioButton = group.findViewById(radioButtonID);
                            RadioButton rb = (RadioButton) radioButton;

                            Boolean acierto = (Boolean) rb.getTag();

                            if(acierto)
                            {
                                puntos = puntos +1;
                            }

                            if(ultimo) {

                                Intent i = new Intent(activity_pregunta_respuesta_trivia.this, activity_respuesta_trivia.class);
                                i.putExtra("puntos",""+puntos);
                                i.putExtra("trivia", trivia);
                                i.putExtra("imagen", imagen);
                                i.putExtra("tipo", "1");
                                startActivity(i);
                                finish();

                            }else
                            {

                                group = cargaPregunta(preguntas.get(siguiente),imagen,activity_pregunta_respuesta_trivia.this);
                                LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);
                                lytRespuestas.removeAllViews();
                                lytRespuestas.addView(group);

                                if(siguiente == preguntas.size())
                                {
                                    ultimo= true;
                                    btnEnviar.setText("ENVIAR >");
                                }
                                else
                                {
                                    ultimo= false;
                                }
                            }

                        } else {
                            txtError.setText("Debe seleccionar una respuesta.");
                        }

                    }
                });
            }

        }
        else
        {
            ImageView imagenTriviaRespuesta = (ImageView) findViewById(R.id.imagenTrivia);
            Log.e("imagen cargaPregunta",imagen);


            try {
                Bitmap img = new GetNetImage().execute(imagen).get();
                if (img != null)
                    imagenTriviaRespuesta.setImageBitmap(img);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            TextView preg = (TextView) findViewById(R.id.txtPreguntaTrivia);
            if (preg != null) {
                preg.setText("No se encontraron preguntas para esta trivia.");
            }
        }
        startTimer();
    }

    public RadioGroup cargaPregunta(Pregunta pregunta,String img_previa, Activity activity)
    {
        RadioGroup response = new RadioGroup(this);
        siguiente = siguiente + 1;
        ImageView imagenTriviaRespuesta = (ImageView) activity.findViewById(R.id.imagenTrivia);
        Log.e("imagen cargaPregunta",img_previa);


            try {
                Bitmap img = new GetNetImage().execute(img_previa).get();
                if (img != null)
                    imagenTriviaRespuesta.setImageBitmap(img);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        TextView preg = (TextView) activity.findViewById(R.id.txtPreguntaTrivia);
        if (preg != null) {
            preg.setText(pregunta.get_txtPregunta());
        }

        ArrayList<Respuestas> respuestas = pregunta.get_respuestasLts();
        for (int i = 0; i < respuestas.size(); i++) {

            String idResp = respuestas.get(i).get_idResp()+"";
            String txtRespuesta = respuestas.get(i).get_txtrespuesta();
            Boolean valRespuesta = respuestas.get(i).get_valRespuesta();

            RadioButton btn1 = new RadioButton(this);
            btn1.setText(txtRespuesta);
            btn1.setTag(valRespuesta);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            int height = metrics.heightPixels; // alto absoluto en pixels

            Log.e("Alto",""+height);
            if(height<1000) {
                btn1.setPadding(50, 20, 50, 20);
                btn1.setTextSize(12);
                preg.setTextSize(13);
                TextView btnEnviar = (TextView) findViewById(R.id.btnEnviar);
                if (btnEnviar != null) {
                        btnEnviar.setTextSize(13);
                }
            }
            else
            {
                btn1.setPadding(70, 50, 70, 50);
                btn1.setTextSize(15);
            }

            btn1.setTextColor(R.color.ColorPrimary);
            response.addView(btn1);
        }

        return response;

    }

    public void startTimer() {


        Log.e("inicio", "Start");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {

                        if(tTranscurrido < tTotal)
                        {
                            tTranscurrido +=1;

                            TextView tiempo = (TextView)findViewById(R.id.txtTiempo);
                            tiempo.setText((tTotal-tTranscurrido)+"''");

                        }
                        else
                        {
                            tTranscurrido +=1;

                            timer.cancel();

                            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, activity_respuesta_trivia.class);
                            i.putExtra("puntos",""+puntos);
                            i.putExtra("trivia", trivia);
                            i.putExtra("imagen", imagen);
                            i.putExtra("tipo", "0");
                            startActivity(i);
                            finish();
                        }

                    }
                });
            }

        }, 0, DURATION);
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
            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, preferencias.class);
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
