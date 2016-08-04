package net.grapesoft.www.telcel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.Comunication;
import Utitilies.PreguntaElement;
import Utitilies.SessionManagement;

public class activity_respuesta extends AppCompatActivity {

    JSONArray responseArray2;
    public String tokenCTE = "";
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);

        session = new SessionManagement(getApplicationContext());
        tokenCTE = getText(R.string.tokenXM).toString();
        final HashMap<String, String> user = session.getUserDetails();


        String pregunta = getIntent().getExtras().getString("pregunta", "No hay pregunta del día");
        String respuesta = getIntent().getExtras().getString("respuesta", "No hay respuesta del día");
     //   String idTrivia = getIntent().getExtras().getString("idTrivia", "0
        // ");
        String val = getIntent().getExtras().getString("val", "0");
   //  JSONArray response;

   //  ArrayList<String> params = new ArrayList<String>();

   //  //-- PARAMETROS PETICION LOGIN-----//
   //  params.add("9");
   //  params.add("SaveWinner.php");
   //  params.add(tokenCTE);
   //  params.add(idTrivia);
   //  params.add(user.get(SessionManagement.KEY_PD_ID));
   //  params.add(user.get(SessionManagement.KEY_PD_REGION));

   //    try {
   //        response = new Comunication(activity_respuesta.this).execute(params).get();
   //    } catch (InterruptedException e) {
   //        e.printStackTrace();
   //    } catch (ExecutionException e) {
   //        e.printStackTrace();
   //    }

        String title = "";
        int image = 0;
        if (val.equals("0")) {
           title = "¡No has acertado!";
            image = R.drawable.error;


        } else {
            title = "¡Bien contestado!";
            image = R.drawable.acierto;
        }


        ImageView icono = (ImageView) findViewById(R.id.icono);
        icono.setImageResource(image);
        TextView titulo = (TextView) findViewById(R.id.titulo);
        if (titulo != null)
            titulo.setText(title);

        TextView txtPreguntaTrivia = (TextView) findViewById(R.id.pregunta);
        if (txtPreguntaTrivia != null)
            txtPreguntaTrivia.setText(pregunta);

        TextView txtRespuesta = (TextView) findViewById(R.id.respuesta);

        if (txtRespuesta != null)
            txtRespuesta.setText("R: "+respuesta);




    }
}
