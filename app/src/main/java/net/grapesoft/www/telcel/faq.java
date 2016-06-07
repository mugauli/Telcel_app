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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class faq extends ActionBarActivity {
    final Context context = this;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);






        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

        datos.add(new Lista_entrada(getString(R.string.uno), getString(R.string.unodesc)));
        datos.add(new Lista_entrada(getString(R.string.dos),getString(R.string.dosdesc)));
        datos.add(new Lista_entrada(getString(R.string.tres), getString(R.string.tresdesc)));
        datos.add(new Lista_entrada(getString(R.string.cuatro),getString(R.string.cuatrodesc)));


        lista = (ListView) findViewById(R.id.faq);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_faq, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
                    texto_superior_entrada.setTypeface(tf);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfl);
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
