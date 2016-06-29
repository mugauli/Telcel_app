package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentVideo extends Fragment {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "http://internetencaja.com.mx/telcel/videos/Grafica%20Informativa.mp4";

    public String tokenCTE = "";
    private ListView lista;
    private ImageView imageView;
    private Bitmap loadedImage;
    private String imageHttpAddress = "";
    SessionManagement session;


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_video, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_REGION);

        params.add("6");
        params.add("GetVideo.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentVideoAsync(getActivity()).execute(params);

        ImageView imagen_entrada2 = (ImageView) rootview.findViewById(R.id.play);

        imagen_entrada2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getActivity(), VideoDetalleActivity.class);

                Lista_Entrada ltEntrada = (Lista_Entrada) arg0.getTag();

                ArrayList<String> datos = new ArrayList<String>();
                datos.add(ltEntrada.get_id());


                datos.add("id");
                datos.add("id");
                datos.add("id");

                i.putExtra("video_selected",datos);


                startActivity(i);
            }

        });
        ImageView imagen_entrada3 = (ImageView) rootview.findViewById(R.id.descarga);

        imagen_entrada3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                download(rootview,arg0.getTag().toString());
            }

        });


        return rootview;
    }

    public void download(View v , String URL)
    {

        new DownloadFile().execute(URL);
    }

    public void view(View v,String URL)
    {
        Log.e("URL Abrir", URL);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreevid/" + URL);
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
            Log.e("URL Guardado",fileUrl);
            Log.e("URL Guardado",pdfFile.toString());
            FileDownloader.DownloadFile(fileUrl, pdfFile);
            return null;
        }
    }


}