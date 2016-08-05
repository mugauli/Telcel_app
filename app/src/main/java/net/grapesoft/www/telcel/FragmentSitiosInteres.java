package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentSitiosInteres extends Fragment {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.activity_sitios, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();

        session = new SessionManagement(getActivity());


        ImageView img1 =  (ImageView) rootview.findViewById(R.id.imageView7);
        ImageView img2 =  (ImageView) rootview.findViewById(R.id.imageView8);
        ImageView img3 =  (ImageView) rootview.findViewById(R.id.imageView9);
        ImageView img4 =  (ImageView) rootview.findViewById(R.id.imageView10);
        ImageView img5 =  (ImageView) rootview.findViewById(R.id.imageView11);
        ImageView img6 =  (ImageView) rootview.findViewById(R.id.imageView12);
        ImageView img7 =  (ImageView) rootview.findViewById(R.id.imageView13);
        ImageView img8 =  (ImageView) rootview.findViewById(R.id.imageView14);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://aprende.org/pages.php?r=.index"));
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.claromusica.com/intro"));
                startActivity(intent);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.clarovideo.com/"));
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.clikisalud.net/"));
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://holatelcel.com/"));
                startActivity(intent);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://lanaturalezanosllama.com/"));
                startActivity(intent);
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://telcelracing.com/v2/"));
                startActivity(intent);
            }
        });

        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://telcelracing.com/v2/"));
                startActivity(intent);
            }
        });






        return rootview;
    }
}
