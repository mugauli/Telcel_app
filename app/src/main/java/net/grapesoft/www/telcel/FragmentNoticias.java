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

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentNoticias extends Fragment {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_noticias, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetNews.php");
        params.add(tokenCTE);
        params.add(region);

        try {
            new FragmentNoticiasAsync(getActivity()).execute(params);
        } catch (Exception e) {
            Log.e("abc","");
            e.printStackTrace();
        }

        LinearLayout principal = (LinearLayout) rootview.findViewById(R.id.linearPrincipalNT);

        principal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getActivity(), activity_detalle_noticia.class);

                Lista_Entrada Entrada = (Lista_Entrada) arg0.getTag();
                if (Entrada != null) {
                    i.putExtra("imagen", Entrada.get_img_detalle());
                    i.putExtra("titulo", Entrada.get_titulo());
                    i.putExtra("fecha", Entrada.get_fecha());
                    i.putExtra("descripcion", Entrada.get_textoDebajo());

                    startActivity(i);
                }

            }
        });
        return rootview;
    }
}
