package net.grapesoft.www.telcel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

/**
 * Created by memoHack on 23/06/2016.
 */
public class FragmentGrupo extends Fragment {

    String styledText = "This is <font color='red'>simple</font>.";

    private ListView lista;
    public String tokenCTE = "";
    SessionManagement session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.tab_fragment_grupo, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_REGION);

        params.add("6");
        params.add("GetCarso.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentGrupoAsync(getActivity()).execute(params);


        LinearLayout principal = (LinearLayout)rootview.findViewById(R.id.linearPrincipal);

        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getActivity(), activity_detalle_grupo.class);

                Lista_Entrada Entrada = (Lista_Entrada)arg0.getTag();

                i.putExtra("imagen",Entrada.get_img_detalle());
                i.putExtra("titulo",Entrada.get_titulo());
                i.putExtra("fecha",Entrada.get_fecha());
                i.putExtra("descripcion",Entrada.get_textoDebajo());

                startActivity(i);


            }
        });




        return rootview;
    }
}
