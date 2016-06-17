package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TabFragment5 extends Fragment {
    private ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_5, container, false);

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        datos.add(new Lista_entrada(R.drawable.revista0001, "Podcast 0001", "Duracion - Fecha"));
        datos.add(new Lista_entrada(R.drawable.pod2, "Prueba 03-06-16 Mariana y Marcos","Duracion - Fecha"));
        datos.add(new Lista_entrada(R.drawable.pod6, "Mejora tus ventas (audio de prueba)","Duracion - Fecha"));
        lista = (ListView) rootview.findViewById(R.id.listcomunicados);
        lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.entrada_comunicados, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.comunicadofecha);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.comunicadotitulo);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagencomunicados);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());




                    imagen_entrada.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {


                        }
                    });
                }
            }
        });

        return rootview;

    }
}
