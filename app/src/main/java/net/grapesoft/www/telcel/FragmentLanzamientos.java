package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentLanzamientos extends Fragment {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;
    private Tracker mTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_lanzamientos, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();
        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetSmartphone.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentLanzamientosAsync(getActivity()).execute(params);

        LinearLayout principal = (LinearLayout)rootview.findViewById(R.id.linearPrincipalLZ);
        if(principal != null)
        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getActivity(), activity_detalle_lanzamientos.class);
                Lista_Entrada Entrada = (Lista_Entrada)arg0.getTag();

                //    i.putExtra("imagen",Entrada.get_img_previa());
                i.putExtra("titulo",Entrada.get_titulo());
                i.putExtra("descripcion",Entrada.get_textoDebajo());
                i.putStringArrayListExtra("imagenes_slider",Entrada.get_imagenesSlide());

                startActivity(i);

            }
        });
        return rootview;
    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Lanzamientos");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
