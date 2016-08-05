package net.grapesoft.www.telcel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by memoHack on 23/06/2016.
 */
public class FragmentSitios extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_fragment_sitios, container, false);

        ImageView img1 = (ImageView)rootview.findViewById(R.id.imageView7);
        ImageView img2 = (ImageView)rootview.findViewById(R.id.imageView8);
        ImageView img3 = (ImageView)rootview.findViewById(R.id.imageView9);
        ImageView img4 = (ImageView)rootview.findViewById(R.id.imageView10);
        ImageView img5 = (ImageView)rootview.findViewById(R.id.imageView11);
        ImageView img6 = (ImageView)rootview.findViewById(R.id.imageView12);
        ImageView img7 = (ImageView)rootview.findViewById(R.id.imageView13);


        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });
        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });

        img3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });
        img5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });
        img6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });
        img7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                startActivity(intent);


            }
        });


        return rootview;
    }
}
