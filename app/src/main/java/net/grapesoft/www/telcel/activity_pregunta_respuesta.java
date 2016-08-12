package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Utitilies.PreguntaElement;
import Utitilies.SessionManagement;

public class activity_pregunta_respuesta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONArray responseArray2;
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_respuesta);

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_pregunta_respuesta.this,ayuda.class);
                startActivity(intent);
            }
        });
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        txtGhost4.setTypeface(tfm);
        txtGhost4.setText("PREGUNTA DEL DIA");

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
                    Intent i = new Intent(activity_pregunta_respuesta.this, MainActivity.class);
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
                    Intent i = new Intent(activity_pregunta_respuesta.this, triviasActivity.class);
                    startActivity(i);
                }
            });
        }

        final String pregunta = getIntent().getExtras().getString("pregunta","No hay pregunta del día");
        String result2 = getIntent().getExtras().getString("json","0");

        final TextView txtPreguntaTrivia = (TextView)findViewById(R.id.txtPregunta);
        if(txtPreguntaTrivia!= null )
        {
            txtPreguntaTrivia.setText(pregunta);
        }

        try {
            if (result2.equals("true" + "\n")) {
                // Log.e("Response: ", "true Int");

                responseArray2 = new JSONArray("[{'resp':'true'}]");

            } else if (result2.equals("false" + "\n")) {
                //Log.e("Response: ", "false int");
                responseArray2 = new JSONArray("[{'resp':'false'}]");
            } else {
                //Log.e("Response: ", "JSON");
                if (result2.contains("["))
                    responseArray2 = new JSONArray(result2);
                else
                    responseArray2 = new JSONArray("[" + result2 + "]");
            }
            if (responseArray2.getJSONObject(0).has("resp")) {
                Log.e("Item Preguntas", "Error");

            } else if (responseArray2.getJSONObject(0).has("error")) {
                Log.e("Item Preguntas", "Error");
            } else {
                if (responseArray2.length() > 0) {

                    Log.e("Response Item: ", responseArray2.getJSONObject(0).toString());

                    String idPreg = responseArray2.getJSONObject(0).get("id").toString();
                    String preguntaPreg = responseArray2.getJSONObject(0).get("pregunta").toString();
                    JSONArray respuestas = responseArray2.getJSONObject(0).getJSONArray("respuestas");
                    String RespuestaCorrecta = "";

                    final RadioGroup group = new RadioGroup(this);
                    group.setOrientation(RadioGroup.VERTICAL);
                    LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);

                    lytRespuestas.addView(group);

                    ArrayList<PreguntaElement> preguntas = new ArrayList<PreguntaElement>();

                    for (int i = 0; i < respuestas.length(); i++) {

                        String idResp = respuestas.getJSONObject(i).get("idResp").toString();
                        String txtRespuesta = respuestas.getJSONObject(i).get("txtRespuesta").toString();
                        String valRespuesta = respuestas.getJSONObject(i).get("valRespuesta").toString();
                       // preguntas.add(new PreguntaElement(idResp, txtRespuesta, valRespuesta));

                        if(valRespuesta.equals("1")) {

                            RespuestaCorrecta = txtRespuesta;
                            Log.e("RespuestaCorrecta",RespuestaCorrecta);
                        }

                        RadioButton btn1 = new RadioButton(this);
                        btn1.setText(txtRespuesta);
                        btn1.setTag(new PreguntaElement(idResp,txtRespuesta,valRespuesta));
                        btn1.setPadding(80,80,80,80);
                        btn1.setTextSize(15);
                        btn1.setTextColor(R.color.ColorPrimary);
                       // btn1.setHeight(150);
                        group.addView(btn1);

                    }

                    TextView btnEnviar = (TextView) findViewById(R.id.btnEnviar);
                    if(btnEnviar!= null)
                    {
                        final String finalRespuestaCorrecta = RespuestaCorrecta;


                        btnEnviar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                TextView txtError = (TextView)findViewById(R.id.txtError);
                                int radioButtonID = group.getCheckedRadioButtonId();
                                Log.e("elegido",""+radioButtonID);
                                if(radioButtonID != -1) {
                                    txtError.setText("");
                                    View radioButton = group.findViewById(radioButtonID);
                                    RadioButton rb = (RadioButton) radioButton;

                                    PreguntaElement element = (PreguntaElement) rb.getTag();

                                    Log.e("elegido", element.get_valRespuesta());

                                    Intent i = new Intent(activity_pregunta_respuesta.this, activity_respuesta.class);
                                    i.putExtra("val", element.get_valRespuesta());
                                    i.putExtra("pregunta", pregunta.toString());
                                    i.putExtra("respuesta", finalRespuestaCorrecta.toString());
                                    startActivity(i);
                                    finish();

                                }
                                else
                                {
                                  txtError.setText("Debe seleccionar una respuesta.");
                                }

                            }
                        });
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
            Intent i = new Intent(activity_pregunta_respuesta.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(activity_pregunta_respuesta.this, pin.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            /*Intent i = new Intent(activity_pregunta_respuesta.this, preferencias.class);
            startActivity(i);*/

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
