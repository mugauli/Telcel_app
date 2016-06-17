package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */

import android.app.DownloadManager;
import android.app.ProgressDialog;
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
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.tab_fragment_3, container, false);

        ImageView imagen_entrada2 = (ImageView) rootview.findViewById(R.id.revista);
        imagen_entrada2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {




            }

        });

        return rootview;
    }



}