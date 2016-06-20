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

import Utitilies.Lista_Entrada;

public class TabFragment4 extends Fragment {
    String styledText = "This is <font color='red'>simple</font>.";
    private ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_4, container, false);

        TextView descUN = (TextView) rootview.findViewById(R.id.descUN);
        descUN.setText(Html.fromHtml(styledText));


        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();
        datos.add(new Lista_Entrada(R.drawable.revista0001, "Podcast 0001", "Duracion - Fecha"));
        datos.add(new Lista_Entrada(R.drawable.pod2, "Prueba 03-06-16 Mariana y Marcos","Duracion - Fecha"));
        datos.add(new Lista_Entrada(R.drawable.pod6, "Mejora tus ventas (audio de prueba)","Duracion - Fecha"));
        lista = (ListView) rootview.findViewById(R.id.listnoticias);
        lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.entrada_noticias, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.noticiafecha);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_Entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.noticiatitulo);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagenoticias);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());




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
