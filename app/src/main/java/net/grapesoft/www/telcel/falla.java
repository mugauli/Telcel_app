package net.grapesoft.www.telcel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;

import android.app.Activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());


                }
            }


        });

    }
}
