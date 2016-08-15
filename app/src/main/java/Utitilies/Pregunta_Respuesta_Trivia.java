package Utitilies;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Mugauli on 13/08/2016.
 */
public class Pregunta_Respuesta_Trivia {

    int idTrivia;
    String tipo;
    String texto;
    String titulo;
    String duracion;
    String img_previa;
    ArrayList<Pregunta> elementos;

    public Pregunta_Respuesta_Trivia(){}

    public Pregunta_Respuesta_Trivia(int idTrivia, String tipo, String texto, String titulo, String duracion, String img_previa, ArrayList<Pregunta> elementos){
        this.idTrivia = idTrivia;
        this.tipo = tipo;
        this.texto = texto;
        this.titulo = titulo;
        this.duracion = duracion;
        this.img_previa = img_previa;
        this.elementos = elementos;
    }

    public int get_idTrivia() {
        return idTrivia;
    }
    public String get_tipo() {
        return tipo;
    }
    public String get_texto() {
        return texto;
    }
    public String get_titulo() {
        return titulo;
    }
    public String get_img_previa() {
        return img_previa;
    }
    public String get_duracion() {
        return duracion;
    }
    public ArrayList<Pregunta> get_elementos() {
        return elementos;
    }
}
