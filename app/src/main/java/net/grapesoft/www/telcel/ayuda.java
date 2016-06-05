package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Mugauli on 01/06/2016.
 */
public class ayuda  extends ActionBarActivity {

    final Context context = this;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

        datos.add(new Lista_entrada(R.drawable.arrow, getString(R.string.preguntas), getString(R.string.preguntasdesc)));
        datos.add(new Lista_entrada(R.drawable.arrow, getString(R.string.fallas),getString(R.string.fallasdesc)));
        datos.add(new Lista_entrada(R.drawable.arrow, getString(R.string.sugerencias), getString(R.string.sugerenciasdesc)));
        datos.add(new Lista_entrada(R.drawable.arrow, getString(R.string.acercade),""));


        lista = (ListView) findViewById(R.id.ayuda);
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

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
                }
            }
        });

        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();

                if(elegido.get_textoEncima() == getString(R.string.preguntas) ) {
                    Intent i = new Intent(ayuda.this, faq.class);
                    startActivity(i);
                }else if(elegido.get_textoEncima() == getString(R.string.fallas))
                {
                    Intent i = new Intent(ayuda.this, falla.class);
                    startActivity(i);

                }else if(elegido.get_textoEncima() == getString(R.string.sugerencias))
                {
                    Intent i = new Intent(ayuda.this, sugerencias.class);
                    startActivity(i);
                }else if(elegido.get_textoEncima() == getString(R.string.acercade))
                {
                    Intent i = new Intent(ayuda.this,acercade.class );
                    startActivity(i);
                }else
                {
                    Toast toast = Toast.makeText(ayuda.this, texto, Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }





}
