package Utitilies;

import java.util.ArrayList;

/**
 * Created by Mugauli on 13/08/2016.
 */
public class Pregunta {

    int idPreg;
    String txtPregunta;
    ArrayList<Respuestas> respuestasLts;

    public Pregunta (){}

    public Pregunta (int idPreg, String txtPregunta, ArrayList<Respuestas> respuestasLts){
        this.idPreg = idPreg;
        this.txtPregunta = txtPregunta;
        this.respuestasLts = respuestasLts;
    }

    public int get_idPreg() {
        return idPreg;
    }
    public String get_txtPregunta() {
        return txtPregunta;
    }
    public ArrayList<Respuestas> get_respuestasLts() {
        return respuestasLts;
    }
}
