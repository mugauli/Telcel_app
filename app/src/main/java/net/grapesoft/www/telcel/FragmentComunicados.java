package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentComunicados extends Fragment {
    private ListView lista;
    public String tokenCTE = "";
    SessionManagement session;
    private Tracker mTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_comunicados, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetRelease.php");
        params.add(tokenCTE);
        params.add(region);
        try {
            new FragmentComunicadosAsync(getActivity()).execute(params);
        } catch (Exception e) {
            Log.e("abc", "");
            e.printStackTrace();
        }
        LinearLayout principal = (LinearLayout) rootview.findViewById(R.id.linearPrincipalUC);

        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Lista_Entrada Entrada = (Lista_Entrada) arg0.getTag();
                //if(Entrada.get_tipo().toString() == "I"){
                Intent i = new Intent(getActivity(), activity_detalle_comunicados_imagen.class);
                Intent p = new Intent(getActivity(), activity_detalle_comunicado.class);
                String nada = "I";
                String todo = Entrada.get_tipo();


                //Toast toast2 = Toast.makeText(getActivity(), nada + todo, Toast.LENGTH_SHORT);
                //toast2.show();

                if (todo.compareTo("I") == 0) {

                    i.putExtra("imagen", Entrada.get_img_detalle());

                    startActivity(i);
                } else {

                    p.putExtra("imagen", Entrada.get_img_detalle());
                    p.putExtra("titulo", Entrada.get_titulo());
                    p.putExtra("fecha", Entrada.get_fecha());
                    p.putExtra("descripcion", Entrada.get_textoDebajo());
                    p.putExtra("tipo", Entrada.get_tipo());
                    p.putExtra("contenido", Entrada.get_contenido());
                    startActivity(p);
                }

                /*}else{
                    Intent i = new Intent(getActivity(), activity_detalle_comunicado.class);

                    i.putExtra("imagen",Entrada.get_img_detalle());
                    i.putExtra("titulo",Entrada.get_titulo());
                    i.putExtra("fecha",Entrada.get_fecha());
                    i.putExtra("descripcion",Entrada.get_textoDebajo());
                    i.putExtra("tipo",Entrada.get_tipo());
                    i.putExtra("contenido",Entrada.get_tipo());
                    startActivity(i);

                }*/


            }
        });

        return rootview;
    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Comunicados");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
