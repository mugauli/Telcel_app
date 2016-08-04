package net.grapesoft.www.telcel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        final String pregunta = getIntent().getExtras().getString("pregunta","No hay pregunta del día");
        String result2 = getIntent().getExtras().getString("json","0");

        final TextView txtPreguntaTrivia = (TextView)findViewById(R.id.txtPregunta);
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
                    String RespuestaCorrecta = "";

                    final RadioGroup group = new RadioGroup(this);
                    group.setOrientation(RadioGroup.VERTICAL);
                    LinearLayout lytRespuestas = (LinearLayout) findViewById(R.id.lytRespuestas);

                    lytRespuestas.addView(group);

                    ArrayList<PreguntaElement> preguntas = new ArrayList<PreguntaElement>();

                    for (int i = 0; i < respuestas.length(); i++) {

                        String idResp = respuestas.getJSONObject(i).get("idResp").toString();
                        String txtRespuesta = respuestas.getJSONObject(i).get("txtRespuesta").toString();
                        String valRespuesta = respuestas.getJSONObject(i).get("valRespuesta").toString();
                       // preguntas.add(new PreguntaElement(idResp, txtRespuesta, valRespuesta));

                        if(valRespuesta.equals("1")) {

                            RespuestaCorrecta = txtRespuesta;
                            Log.e("RespuestaCorrecta",RespuestaCorrecta);
                        }

                        RadioButton btn1 = new RadioButton(this);
                        btn1.setText(txtRespuesta);
                        btn1.setTag(new PreguntaElement(idResp,txtRespuesta,valRespuesta));
                        group.addView(btn1);

                    }

                    TextView btnEnviar = (TextView) findViewById(R.id.btnEnviar);
                    if(btnEnviar!= null)
                    {
                        final String finalRespuestaCorrecta = RespuestaCorrecta;


                        btnEnviar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                TextView txtError = (TextView)findViewById(R.id.txtError);
                                int radioButtonID = group.getCheckedRadioButtonId();
                                Log.e("elegido",""+radioButtonID);
                                if(radioButtonID != -1) {
                                    txtError.setText("");
                                    View radioButton = group.findViewById(radioButtonID);
                                    RadioButton rb = (RadioButton) radioButton;

                                    PreguntaElement element = (PreguntaElement) rb.getTag();

                                    Log.e("elegido", element.get_valRespuesta());

                                    Intent i = new Intent(activity_pregunta_respuesta.this, activity_respuesta.class);
                                    i.putExtra("val", element.get_valRespuesta());
                                    i.putExtra("pregunta", pregunta.toString());
                                    i.putExtra("respuesta", finalRespuestaCorrecta.toString());
                                    startActivity(i);
                                }
                                else
                                {
                                  txtError.setText("Debe seleccionar una respuesta.");
                                }

                            }
                        });
                    }

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
