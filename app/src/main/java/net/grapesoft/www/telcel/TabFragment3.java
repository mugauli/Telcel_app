package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */

import android.app.DownloadManager;
import android.app.ProgressDialog;
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

import java.io.File;
import java.io.IOException;


public class TabFragment3 extends Fragment {
    private static String fileName = "revista-demo.pdf";
    private static final String file_url= "http://internetencaja.com.mx/telcel/revistas/revista-demo.pdf";
    // Progress Dialog

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.tab_fragment_3, container, false);

        ImageView imagen_entrada2 = (ImageView) rootview.findViewById(R.id.revista);
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
        new DownloadFile().execute("http://internetencaja.com.mx/telcel/revistas/revista-demo.pdf", "revista-demo.pdf");
    }

    public void view(View v)
    {


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "revista-demo.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
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
            File folder = new File(extStorageDirectory, "testthreepdf");
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