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

import Utitilies.PreguntaElement;

public class activity_respuesta extends AppCompatActivity {

    JSONArray responseArray2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);

        String pregunta = getIntent().getExtras().getString("pregunta", "No hay pregunta del día");
        String respuesta = getIntent().getExtras().getString("respuesta", "No hay respuesta del día");
        String val = getIntent().getExtras().getString("val", "0");

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
