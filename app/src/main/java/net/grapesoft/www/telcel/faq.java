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
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_lista, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());


                }
            }
        });



    }



}
