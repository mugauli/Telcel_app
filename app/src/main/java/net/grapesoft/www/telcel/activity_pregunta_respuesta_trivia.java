package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Interpolator;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Utitilies.PreguntaElement;
import Utitilies.SessionManagement;

public class activity_pregunta_respuesta_trivia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONArray responseArray2;
    SessionManagement session;
    Boolean ultimo = false;
    String preguntasJSON;
    String siguiente;
    String puntos;
    String trivia;
    String imagen;

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

        preguntasJSON = getIntent().getExtras().getString("preguntas","0");
        siguiente = getIntent().getExtras().getString("siguiente","0");
        puntos = getIntent().getExtras().getString("puntos","0");
        trivia = getIntent().getExtras().getString("trivia","0");
        imagen = getIntent().getExtras().getString("imagen","0");

        try {
            //Log.e("Response: ", "JSON");
            if (preguntasJSON.substring(0,3).contains("["))
                responseArray2 = new JSONArray(preguntasJSON);
            else
                responseArray2 = new JSONArray("[" + preguntasJSON + "]");

            if (responseArray2.getJSONObject(0).has("resp")) {
                Log.e("Item Preguntas_Trivias", "Error");

            } else if (responseArray2.getJSONObject(0).has("error")) {
                Log.e("Item Preguntas_Trivias", "Error");
            } else {
                if (responseArray2.length() > 0) {


                    for (int a = 0; a < responseArray2.length(); a++) {
                        Log.e("IF: ", ""+a + "--" +siguiente);

                        if (a == Integer.parseInt(siguiente)) {

                            Log.e("Response Item: ", responseArray2.getJSONObject(a).toString());

                            String idPreg = responseArray2.getJSONObject(a).get("idPreg").toString();
                            String txtPregunta = responseArray2.getJSONObject(a).get("txtPregunta").toString();

                            TextView pregunta = (TextView) findViewById(R.id.txtPreguntaTrivia);
                            if (pregunta != null) {
                                pregunta.setText(txtPregunta);
                            }

                            JSONArray respuestas = responseArray2.getJSONObject(a).getJSONArray("respuestas");

                            final RadioGroup group = new RadioGroup(this);
                            group.setOrientation(RadioGroup.VERTICAL);
                            LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);

                            lytRespuestas.addView(group);

                            for (int i = 0; i < respuestas.length(); i++) {

                                String idResp = respuestas.getJSONObject(i).get("idResp").toString();
                                String txtRespuesta = respuestas.getJSONObject(i).get("txtRespuesta").toString();
                                String valRespuesta = respuestas.getJSONObject(i).get("valRespuesta").toString();

                                RadioButton btn1 = new RadioButton(this);
                                btn1.setText(txtRespuesta);
                                btn1.setTag(new PreguntaElement(idResp, txtRespuesta, valRespuesta));
                                btn1.setPadding(80,80,80,80);
                                btn1.setTextSize(15);
                                btn1.setTextColor(R.color.ColorPrimary);
                                group.addView(btn1);
                            }



                            TextView btnEnviar = (TextView) findViewById(R.id.btnEnviar);
                            if (btnEnviar != null) {
                                if(Integer.parseInt(siguiente)+1 == respuestas.length())
                                {
                                    ultimo= true;
                                    btnEnviar.setText("ENVIAR >");
                                }
                                btnEnviar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TextView txtError = (TextView) findViewById(R.id.txtError);
                                        int radioButtonID = group.getCheckedRadioButtonId();
                                        Log.e("elegido", "" + radioButtonID);
                                        if (radioButtonID != -1) {
                                            txtError.setText("");
                                            View radioButton = group.findViewById(radioButtonID);
                                            RadioButton rb = (RadioButton) radioButton;

                                            PreguntaElement element = (PreguntaElement) rb.getTag();

                                            Log.e("elegido", element.get_valRespuesta());
                                            puntos = String.valueOf (Integer.parseInt(puntos) + Integer.parseInt(element.get_valRespuesta()));

                                            if(ultimo) {

                                                Intent i = new Intent(activity_pregunta_respuesta_trivia.this, activity_respuesta_trivia.class);
                                                i.putExtra("puntos",""+puntos);
                                                i.putExtra("trivia", trivia);
                                                i.putExtra("imagen", imagen);
                                                startActivity(i);
                                                finish();
                                            }else
                                            {
                                                Intent i = new Intent(activity_pregunta_respuesta_trivia.this, activity_pregunta_respuesta_trivia.class);
                                                i.putExtra("preguntas", preguntasJSON);
                                                i.putExtra("puntos",""+puntos);
                                                i.putExtra("trivia", trivia);
                                                i.putExtra("imagen", imagen);
                                                i.putExtra("siguiente",""+(Integer.parseInt(siguiente)+1));
                                                startActivity(i);
                                                finish();

                                            }
Log.e("imagenbnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", imagen);


                                        } else {
                                            txtError.setText("Debe seleccionar una respuesta.");
                                        }

                                    }
                                });
                            }
                        }
                    }
                }


            }
        } catch (JSONException e) {
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
            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_pregunta_respuesta_trivia.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            /*Intent i = new Intent(activity_pregunta_respuesta.this, preferencias.class);
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
