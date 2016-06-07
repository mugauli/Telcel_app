package net.grapesoft.www.telcel;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;

import android.app.Activity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class falla extends ActionBarActivity {
    final Context context = this;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falla);

        TextView txtGhost = (TextView) findViewById(R.id.textView_desc1);
        TextView txtGhost2 = (TextView) findViewById(R.id.textView_desc2);
        TextView txtGhost3 = (TextView) findViewById(R.id.textView_comentario);
        Button btn=(Button) findViewById(R.id.button);
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");

        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);
        btn.setTypeface(tf);

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

        datos.add(new Lista_entrada(R.id.rdfalla, getString(R.string.rfuno)));
        datos.add(new Lista_entrada(R.id.rdfalla,getString(R.string.rfdos)));
        datos.add(new Lista_entrada(R.id.rdfalla, getString(R.string.rftres)));
        datos.add(new Lista_entrada(R.id.rdfalla,getString(R.string.rfcuatro)));


        lista = (ListView) findViewById(R.id.falla);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_falla, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {


                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);

                    Typeface tfi = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");

                    texto_inferior_entrada.setTypeface(tfi);


                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());


                }
            }


        });

    }
    @Override
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
        if (id == R.id.action_favorite) {
            return true;
        }

        return super.onOptionsItemSelected(item);




    }
}