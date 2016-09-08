package net.grapesoft.www.telcel;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;

import Utitilies.Comunication;
import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class preferencias extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManagement session;
    public String tokenCTE = "";
    int rb1=0,rb2=0;
    private ListView lista;
    String usuario = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        tokenCTE = getText(R.string.tokenXM).toString();

        TextView txtGhost4 = (TextView) findViewById(R.id.TitleSeccion);
        Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        txtGhost4.setTypeface(tfi);
        txtGhost4.setText("TEMAS DE INTERÉS");

        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        usuario = user.get(SessionManagement.KEY_PD_ID);

        if(user.get(SessionManagement.KEY_PD_INTERES_1) != null)
        {
            rb1 = Integer.parseInt(user.get(SessionManagement.KEY_PD_INTERES_1));
        }
        if(user.get(SessionManagement.KEY_PD_INTERES_2) != null)
        {
            rb2 = Integer.parseInt(user.get(SessionManagement.KEY_PD_INTERES_2));
        }
        checkedRadios();

        //boton ayuda
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preferencias.this,ayuda.class);
                startActivity(intent);
            }
        });
        //boton ayuda

        //ListView
        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

       TextView textView01 = (TextView) findViewById(R.id.textView01);
       TextView textView02 = (TextView) findViewById(R.id.textView02);
       TextView textView03 = (TextView) findViewById(R.id.textView03);
       TextView textView04 = (TextView) findViewById(R.id.textView04);
       TextView textView05 = (TextView) findViewById(R.id.textView05);
       TextView textView06 = (TextView) findViewById(R.id.textView06);
       TextView textView07 = (TextView) findViewById(R.id.textView07);
       TextView textView08 = (TextView) findViewById(R.id.textView08);
       TextView textView09 = (TextView) findViewById(R.id.textView09);

        textView01.setText(( getString(R.string.Accesorios)));
        textView02.setText((getString(R.string.Autos)));
        textView03.setText(( getString(R.string.Vivienda)));
        textView04.setText((getString(R.string.Educacion)));
        textView05.setText((getString(R.string.Entretenimiento)));
        textView06.setText((getString(R.string.Restaurantes)));
        textView07.setText((getString(R.string.Salud)));
        textView08.setText((getString(R.string.Tiendas)));
        textView09.setText((getString(R.string.Viajes)));


        CheckBox checkBox01 = (CheckBox) findViewById(R.id.checkBox01);
        CheckBox checkBox02 = (CheckBox) findViewById(R.id.checkBox02);
        CheckBox checkBox03 = (CheckBox) findViewById(R.id.checkBox03);
        CheckBox checkBox04 = (CheckBox) findViewById(R.id.checkBox04);
        CheckBox checkBox05 = (CheckBox) findViewById(R.id.checkBox05);
        CheckBox checkBox06 = (CheckBox) findViewById(R.id.checkBox06);
        CheckBox checkBox07 = (CheckBox) findViewById(R.id.checkBox07);
        CheckBox checkBox08 = (CheckBox) findViewById(R.id.checkBox08);
        CheckBox checkBox09 = (CheckBox) findViewById(R.id.checkBox09);

        checkBox01.setTag(new Lista_Entrada(1, getString(R.string.Accesorios)));
        checkBox02.setTag(new Lista_Entrada(2,getString(R.string.Autos)));
        checkBox03.setTag(new Lista_Entrada(3, getString(R.string.Vivienda)));
        checkBox04.setTag(new Lista_Entrada(4,getString(R.string.Educacion)));
        checkBox05.setTag(new Lista_Entrada(5,getString(R.string.Entretenimiento)));
        checkBox06.setTag(new Lista_Entrada(6,getString(R.string.Restaurantes)));
        checkBox07.setTag(new Lista_Entrada(7,getString(R.string.Salud)));
        checkBox08.setTag(new Lista_Entrada(8,getString(R.string.Tiendas)));
        checkBox09.setTag(new Lista_Entrada(9,getString(R.string.Viajes)));


        checkBox01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });
        checkBox09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista_Entrada a = (Lista_Entrada) v.getTag();
                rb2 = rb1;
                rb1 = a.get_idradio();

                checkedRadios();

            }
        });

        //get_idradio

   //  lista = (ListView) findViewById(R.id.sitioslista);
   //  lista.setAdapter(new List_adapted(this, R.layout.entrada_preferencias, datos){
   //      @Override
   //      public void onEntrada(Object entrada, View view) {
   //          if (entrada != null) {
   //              // Applying font
   //              int cont = 0;
   //              int s = ((Lista_Entrada) entrada).get_idradio();
   //              String ss = Integer.toString(s);
   //              TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.list_one_toggler);
   //              Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
   //              texto_inferior_entrada.setTypeface(tfi);
   //              if (texto_inferior_entrada != null)
   //                  texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());
   //              CheckBox ch = (CheckBox) view.findViewById(R.id.checkBox1);
   //              //Toast.makeText(view.getContext(), ss + ".. "+ int1 + "..." + int2  , Toast.LENGTH_SHORT).show();
   //              if(int1.compareTo(ss) == 0 || int2.compareTo(ss) == 0){

   //                  ch.setChecked(true);
   //                  cont=2;

   //              }


   //          }
   //      }
   //  });

        //

        //Toolbar Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.telcelnosune);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Toolbar title clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(preferencias.this, MainActivity.class);
                i.putExtra("direccion","0");
                startActivity(i);
            }
        });

        ImageButton imgButton = (ImageButton) findViewById(R.id.btnMenu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


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
                Intent i = new Intent(preferencias.this, triviasActivity.class);
                startActivity(i);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //ToolBar Menu

        Button btnEnviarFalla;
        btnEnviarFalla = (Button) findViewById(R.id.btnGuardar);

        // add button listener
        if (btnEnviarFalla != null) {
            btnEnviarFalla.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    TextView txtErrorInteres = (TextView) findViewById(R.id.txtErrorInteres);
                    txtErrorInteres.setText("");
                    if (rb1 == 0 || rb2 == 0 || rb1 == rb2) {
                        txtErrorInteres.setText("Debe selecciona al menos dos temas de interés.");
                    } else {

                        try {
                            JSONArray response;

                            ArrayList<String> params = new ArrayList<String>();

                            //-- PARAMETROS PETICION LOGIN-----//
                            params.add("10");
                            params.add("ChangeInterests.php");
                            params.add(tokenCTE);
                            params.add(usuario);
                            params.add(rb1 + "");
                            params.add(rb2 + "");

                            response = new Comunication(preferencias.this).execute(params).get();


                            //{"id":"5","num_empleado":"ANDROID","num_celular":"ANDROID","region":"1","nombre":"ANDROID","paterno":"ANDROID","materno":"ANDROID","interes_1":null,"interes_2":null}

                            if (!response.getJSONObject(0).has("error")) {

                                session.createInteresSession(rb1 + "", rb2 + "");
                                Intent i = new Intent(preferencias.this, ActualizadosActivity.class);
                                i.putExtra("titulo", "TEMAS DE INTERÉS");
                                i.putExtra("mensaje", "Tus temas de interés han sido actualizados.");
                                startActivity(i);
                                finish();

                            } else {
                                txtErrorInteres.setText("Hubo un error al actualizar los temas de interés.");


                                // Toast toast = Toast.makeText(login.this, "Contraseña incorrecta", Toast.LENGTH_LONG);
                                // toast.show();

                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }
    public void checkedRadios()
    {
        CheckBox checkBox01 = (CheckBox) findViewById(R.id.checkBox01);
        CheckBox checkBox02 = (CheckBox) findViewById(R.id.checkBox02);
        CheckBox checkBox03 = (CheckBox) findViewById(R.id.checkBox03);
        CheckBox checkBox04 = (CheckBox) findViewById(R.id.checkBox04);
        CheckBox checkBox05 = (CheckBox) findViewById(R.id.checkBox05);
        CheckBox checkBox06 = (CheckBox) findViewById(R.id.checkBox06);
        CheckBox checkBox07 = (CheckBox) findViewById(R.id.checkBox07);
        CheckBox checkBox08 = (CheckBox) findViewById(R.id.checkBox08);
        CheckBox checkBox09 = (CheckBox) findViewById(R.id.checkBox09);

        checkBox01.setChecked(Boolean.FALSE);
        checkBox02.setChecked(Boolean.FALSE);
        checkBox03.setChecked(Boolean.FALSE);
        checkBox04.setChecked(Boolean.FALSE);
        checkBox05.setChecked(Boolean.FALSE);
        checkBox06.setChecked(Boolean.FALSE);
        checkBox07.setChecked(Boolean.FALSE);
        checkBox08.setChecked(Boolean.FALSE);
        checkBox09.setChecked(Boolean.FALSE);

        if(rb1 == 1 || rb2 == 1)checkBox01.setChecked(Boolean.TRUE);
        if(rb1 == 2 || rb2 == 2)checkBox02.setChecked(Boolean.TRUE);
        if(rb1 == 3 || rb2 == 3)checkBox03.setChecked(Boolean.TRUE);
        if(rb1 == 4 || rb2 == 4)checkBox04.setChecked(Boolean.TRUE);
        if(rb1 == 5 || rb2 == 5)checkBox05.setChecked(Boolean.TRUE);
        if(rb1 == 6 || rb2 == 6)checkBox06.setChecked(Boolean.TRUE);
        if(rb1 == 7 || rb2 == 7)checkBox07.setChecked(Boolean.TRUE);
        if(rb1 == 8 || rb2 == 8)checkBox08.setChecked(Boolean.TRUE);
        if(rb1 == 9 || rb2 == 9)checkBox09.setChecked(Boolean.TRUE);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            Intent i = new Intent(preferencias.this, ActualizarActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(preferencias.this, pinActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
           Intent i = new Intent(preferencias.this, preferencias.class);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_favorite) {
        //     return true;
        // }

        return super.onOptionsItemSelected(item);




    }






}

