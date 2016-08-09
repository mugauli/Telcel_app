package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.List_adapted;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class FragmentSitiosInteres extends Fragment {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;
    private ListView lista;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.activity_sitios, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();

        session = new SessionManagement(getActivity());

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.logoaprende,getString(R.string.aprende)));
       /* datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.claromusica,R.drawable.logoclaromusica, getString(R.string.clarom)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.clarovideo,R.drawable.logoclarovideo, getString(R.string.clarov)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.clickisalud,R.drawable.clickisalud, getString(R.string.salud)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.holatelcel,R.drawable.holatelcel, getString(R.string.holatel)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.naturaleza,R.drawable.logonaturaleza, getString(R.string.naturaleza)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.racing,R.drawable.logoracing, getString(R.string.racing)));
        datos.add(new Lista_Entrada(R.drawable.arrow, R.drawable.telcelred,R.drawable.logotelcelred, getString(R.string.prox)));*/
        TextView txtGhost4 = (TextView) rootview.findViewById(R.id.textView_inferior);
        Typeface tfi = Typeface.createFromAsset(getActivity().getAssets(), "fonts/media.otf");
        txtGhost4.setTypeface(tfi);

        lista = (ListView) rootview.findViewById(R.id.ayuda);
        lista.setAdapter(new List_adapted(activity, R.layout.entrada_lista, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    // Applying font

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    Typeface tfi = Typeface.createFromAsset(activity.getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfi);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.sitios);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagen());
                    ImageView imglogo = (ImageView) view.findViewById(R.id.sitioslogo);
                    if (imglogo != null)
                        imglogo.setImageResource(((Lista_Entrada) entrada).get_idImagenDetalle());
                    ImageView imgarrow = (ImageView) view.findViewById(R.id.arrow);
                    if (imgarrow != null)
                        imgarrow.setImageResource(((Lista_Entrada) entrada).get_idImagenA());
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_Entrada elegido = (Lista_Entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();

                if(elegido.get_textoDebajo() == getString(R.string.aprende) ) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                    startActivity(intent);

                }else if(elegido.get_textoDebajo() == getString(R.string.clarom))
                {


                }else if(elegido.get_textoDebajo() == getString(R.string.clarov))
                {

                }else if(elegido.get_textoDebajo() == getString(R.string.salud))
                {

                }else
                {

                }

            }
        });







        return rootview;
    }
}
