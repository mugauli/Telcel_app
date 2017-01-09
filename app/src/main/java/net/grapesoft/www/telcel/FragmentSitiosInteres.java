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
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
    private Tracker mTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.tab_fragment_sitios, container, false);
        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        tokenCTE = getText(R.string.tokenXM).toString();

        session = new SessionManagement(getActivity());

        ArrayList<Lista_Entrada> datos = new ArrayList<Lista_Entrada>();

        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.aprende,R.drawable.logoaprende,getString(R.string.aprende)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.claromusica,R.drawable.logoclaromusica,getString(R.string.clarom)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.clarovideo,R.drawable.logoclarovideo,getString(R.string.clarov)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.clickisalud,R.drawable.logoclickisalud,getString(R.string.salud)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.holatelcel,R.drawable.logoholatelcel,getString(R.string.holatel)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.naturaleza,R.drawable.logonaturaleza,getString(R.string.naturaleza)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.sitiomitelcel,R.drawable.mitelcel,getString(R.string.mitelcel)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.racing,R.drawable.logoracing,getString(R.string.racing)));
        datos.add(new Lista_Entrada(R.drawable.arrow,R.drawable.telcelred,R.drawable.logotelcelred,getString(R.string.prox)));


        //COdigo
        lista = (ListView) rootview.findViewById(R.id.sitioslista);


        lista.setAdapter(new List_adapted(rootview.getContext(), R.layout.entrada_sitios, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
               if (entrada != null) {
                    // Applying font


                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    Typeface tfi = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/ligera.otf");
                    texto_inferior_entrada.setTypeface(tfi);
                   texto_inferior_entrada.setTextSize(12);

                    if (texto_inferior_entrada != null) {
                        texto_inferior_entrada.setText(((Lista_Entrada) entrada).get_textoDebajo());

                    }


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.sitios);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_Entrada) entrada).get_idImagenDetalle());//

                    ImageView imagen_entradad = (ImageView) view.findViewById(R.id.sitioslogo);
                    if (imagen_entradad != null)
                        imagen_entradad.setImageResource(((Lista_Entrada) entrada).get_idImagen());//get_idImagenDetalle()
                    ImageView imagen_entradaa = (ImageView) view.findViewById(R.id.arrow);
                    if (imagen_entradaa != null)
                        imagen_entradaa.setImageResource(((Lista_Entrada) entrada).get_idImagenA());//get_idImagenDetalle()
                }
            }
        });

      lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_Entrada elegido = (Lista_Entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();

                if (elegido.get_textoDebajo() == getString(R.string.aprende)) {

                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://aprende.org/pages.php?r=.index"));
                    startActivity(intent);

                } else if (elegido.get_textoDebajo() == getString(R.string.clarom)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.claromusica.com/intro"));
                    startActivity(intent);


                } else if (elegido.get_textoDebajo() == getString(R.string.clarov)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.clarovideo.com/mexico/homeuser"));
                    startActivity(intent);

                } else if (elegido.get_textoDebajo() == getString(R.string.salud)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.clikisalud.net/"));
                    startActivity(intent);

                } else if (elegido.get_textoDebajo() == getString(R.string.holatel)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://holatelcel.com/"));
                    startActivity(intent);


                } else if (elegido.get_textoDebajo() == getString(R.string.naturaleza)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://lanaturalezanosllama.com/"));
                    startActivity(intent);
                } else if (elegido.get_textoDebajo() == getString(R.string.mitelcel)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.mitelcel.com"));
                    startActivity(intent);

                } else if (elegido.get_textoDebajo() == getString(R.string.racing)) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://telcelracing.com/v2/"));
                    startActivity(intent);
                } else if (elegido.get_textoDebajo() == getString(R.string.prox)) {
                    /*Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://internetencaja.com.mx/telcel-registro/"));
                    startActivity(intent);*/
                }
            }

      });

        //codigo







        return rootview;
    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Sitios de interes");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
