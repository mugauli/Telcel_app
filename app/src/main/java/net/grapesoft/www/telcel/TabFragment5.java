package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Utitilies.Lista_Entrada;

public class TabFragment5 extends Fragment {
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_5, container, false);

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        datos.add(new Lista_Entrada(R.drawable.mas, "18-12-2015", "Desarrollando nuestro potencial, Proceso de realimentacion al desempeño."));
        datos.add(new Lista_Entrada(R.drawable.mas, "10-12-2015", "Comunicado especial. Proxima evaluacion al desempeño 2016, Preparate."));
        datos.add(new Lista_Entrada(R.drawable.mas, "18-12-2015", "Desarrollando nuestro potencial, Proceso de realimentacion al desempeño."));

        lista = (ListView) rootview.findViewById(R.id.listcomunicados);

        lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.entrada_comunicados, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.comunicadofecha);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.comunicadotitulo);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagencomunicados);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());




                    imagen_entrada.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            Intent myIntent = new Intent(getActivity(), Detalle_noticia.class);
                            getActivity().startActivity(myIntent);
                        }
                    });
                }
            }
        });

        return rootview;

    }

}
