package net.grapesoft.www.telcel;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.annotation.SuppressLint;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import org.json.JSONArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Utitilies.Comunication;

public class TabFragment1 extends Fragment {
    public String tokenCTE = "";
    private ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_fragment_1, container, false);


        //
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        datos.add(new Lista_entrada(R.drawable.revista0001, "Podcast 0001", "Duracion - Fecha",R.drawable.downloadcircle));
        datos.add(new Lista_entrada(R.drawable.pod2, "Prueba 03-06-16 Mariana y Marcos","Duracion - Fecha",R.drawable.downloadcircle));
        datos.add(new Lista_entrada(R.drawable.pod6, "Mejora tus ventas (audio de prueba)","Duracion - Fecha",R.drawable.downloadcircle));


        lista = (ListView) rootview.findViewById(R.id.podcast);
        lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.entrada_podcast, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView5);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());

                    ImageView imagen_entrada2 = (ImageView) view.findViewById(R.id.imageView6);
                    if (imagen_entrada2 != null)
                        imagen_entrada2.setImageResource(((Lista_entrada) entrada).get_idImagen2());


                    imagen_entrada2.setOnClickListener(new View.OnClickListener() {

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