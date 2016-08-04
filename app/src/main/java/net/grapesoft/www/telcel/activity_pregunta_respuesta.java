package net.grapesoft.www.telcel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Utitilies.PreguntaElement;

public class activity_pregunta_respuesta extends AppCompatActivity {

    JSONArray responseArray2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_respuesta);

        String pregunta = getIntent().getExtras().getString("pregunta","No hay pregunta del día");
        String result2 = getIntent().getExtras().getString("json","0");

        TextView txtPreguntaTrivia = (TextView)findViewById(R.id.txtPregunta);
        if(txtPreguntaTrivia!= null )
        {
            txtPreguntaTrivia.setText(pregunta);
        }

        try {
            if (result2.equals("true" + "\n")) {
                // Log.e("Response: ", "true Int");

                responseArray2 = new JSONArray("[{'resp':'true'}]");

            } else if (result2.equals("false" + "\n")) {
                //Log.e("Response: ", "false int");
                responseArray2 = new JSONArray("[{'resp':'false'}]");
            } else {
                //Log.e("Response: ", "JSON");
                if (result2.contains("["))
                    responseArray2 = new JSONArray(result2);
                else
                    responseArray2 = new JSONArray("[" + result2 + "]");
            }
            if (responseArray2.getJSONObject(0).has("resp")) {
                Log.e("Item Preguntas", "Error");

            } else if (responseArray2.getJSONObject(0).has("error")) {
                Log.e("Item Preguntas", "Error");
            } else {
                if (responseArray2.length() > 0) {

                    Log.e("Response Item: ", responseArray2.getJSONObject(0).toString());

                    String idPreg = responseArray2.getJSONObject(0).get("id").toString();
                    String preguntaPreg = responseArray2.getJSONObject(0).get("pregunta").toString();
                    JSONArray respuestas = responseArray2.getJSONObject(0).getJSONArray("respuestas");

                    RadioGroup group = new RadioGroup(this);
                    group.setOrientation(RadioGroup.VERTICAL);
                    LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);

                    lytRespuestas.addView(group);

                    ArrayList<PreguntaElement> preguntas = new ArrayList<PreguntaElement>();

                    for (int i = 0; i < respuestas.length(); i++) {

                        String idResp = respuestas.getJSONObject(i).get("idResp").toString();
                        String txtRespuesta = respuestas.getJSONObject(i).get("txtRespuesta").toString();
                        String valRespuesta = respuestas.getJSONObject(i).get("valRespuesta").toString();
                       // preguntas.add(new PreguntaElement(idResp, txtRespuesta, valRespuesta));

                        RadioButton btn1 = new RadioButton(this);
                        btn1.setText(txtRespuesta);
                        btn1.setTag(valRespuesta);
                        group.addView(btn1);

                    }

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
