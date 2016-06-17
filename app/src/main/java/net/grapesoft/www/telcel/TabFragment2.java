package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TabFragment2 extends Fragment {
    private static String fileName = "Bridge-Slide-Video.mp4";
    private static final String file_url= "http://internetencaja.com.mx/telcel/videos/Bridge-Slide-Video.mp4";
    private ListView lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_2, container, false);

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        datos.add(new Lista_entrada(R.drawable.revista0001, "Podcast 0001", "Duracion - Fecha"));
        datos.add(new Lista_entrada(R.drawable.pod2, "Prueba 03-06-16 Mariana y Marcos","Duracion - Fecha"));
        datos.add(new Lista_entrada(R.drawable.pod6, "Mejora tus ventas (audio de prueba)","Duracion - Fecha"));

        lista = (ListView) rootview.findViewById(R.id.listvideo);
        lista.setAdapter(new Lista_adaptador(getActivity(), R.layout.entrada_video, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.videotitulo);

                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.videoduracion);

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagevideo);
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


        ImageView imagen_entrada2 = (ImageView) rootview.findViewById(R.id.video);
        download(rootview);
        imagen_entrada2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                view(rootview);


            }

        });

        return rootview;
    }

    public void download(View v)
    {
        new DownloadFile().execute("http://internetencaja.com.mx/telcel/videos/Bridge-Slide-Video.mp4", "Bridge-Slide-Video.mp4");
    }

    public void view(View v)
    {


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreevid/" + "Bridge-Slide-Video.mp4");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "video/*");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){

        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreevid");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.DownloadFile(fileUrl, pdfFile);
            return null;
        }
    }
}