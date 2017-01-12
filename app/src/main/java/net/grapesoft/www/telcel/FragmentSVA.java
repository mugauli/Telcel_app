package net.grapesoft.www.telcel;

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
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;
import Utitilies.SvaElement;

public class FragmentSVA extends Fragment {
    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "http://internetencaja.com.mx/telcel/videos/Grafica%20Informativa.mp4";

    public String tokenCTE = "";
    SessionManagement session;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_sva, container, false);
        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetSVA");
        params.add(tokenCTE);
        params.add(region);

        new FragmentSVAAsync(getActivity()).execute(params);

        LinearLayout principal = (LinearLayout) rootview.findViewById(R.id.linearPrincipalSva);

        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getActivity(), activity_detalle_sva.class);

                SvaElement Entrada = (SvaElement)arg0.getTag();

                i.putExtra("imagen",Entrada.get_img_detalleSva());
                i.putExtra("titulo",Entrada.get_tituloSva());
                i.putExtra("descripcion",Entrada.get_textoDebajoSva());
                //i.putStringArrayListExtra("imagenes_slider",Entrada.get_imagenesSlide());

                startActivity(i);


            }
        });

        return rootview;
    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("SVA");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

}
