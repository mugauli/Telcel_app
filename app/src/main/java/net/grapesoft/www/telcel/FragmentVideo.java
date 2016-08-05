package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.SessionManagement;

public class FragmentVideo extends Fragment {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "http://internetencaja.com.mx/telcel/videos/Grafica%20Informativa.mp4";

    public String tokenCTE = "";
    SessionManagement session;


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_video, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetVideo.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentVideoAsync(getActivity()).execute(params);

        ImageView imagen_play = (ImageView) rootview.findViewById(R.id.play);

       imagen_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //TextView idVideo = (TextView) rootview.findViewById(R.id.idVideo);
               String id_video = arg0.getTag().toString();
               Intent i = new Intent(getActivity(), activity_detalle_video.class);
               i.putExtra("video_id",id_video);
               startActivity(i);
               //Log.e("ID Video", "ID: "+ id_video);
            }

        });
      /* ImageView imagen_descarga = (ImageView) rootview.findViewById(R.id.descarga);
       imagen_descarga.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //download(rootview,arg0.getTag().toString());
                String id_video = arg0.getTag().toString();
                Intent i = new Intent(getActivity(), activity_detalle_video.class);
                i.putExtra("video_id",id_video);
                startActivity(i);
            }

        });*/


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