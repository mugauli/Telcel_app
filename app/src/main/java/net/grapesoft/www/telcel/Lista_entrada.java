package net.grapesoft.www.telcel;

import android.graphics.Bitmap;

/**
 * Created by memoHack on 01/06/2016.
 */
public class Lista_entrada {
    private int idImagen,idImagen2;

    private String textoEncima;
    private String textoDebajo;
    private Bitmap urlImagen;
    private int idradio;

    public Lista_entrada (int idImagen, String textoEncima, String textoDebajo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public Lista_entrada (String textoEncima, String textoDebajo) {

        this.textoEncima =  textoEncima;
        this.textoDebajo = textoDebajo;
    }
    public Lista_entrada (int idradio, String textoDebajo) {

        this.idradio = idradio;
        this.textoDebajo = textoDebajo;
    }
    public Lista_entrada (int idImagen,String textoEncima, String textoDebajo,int idImagen2) {

        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
        this.idImagen2 = idImagen2;
    }

    public Lista_entrada (Bitmap urlImagen,String textoEncima, String textoDebajo,int idImagen2) {

        this.urlImagen = urlImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
        this.idImagen2 = idImagen2;
    }
    public Bitmap get_urlImagen() {
        return urlImagen;
    }
    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }
    public int get_idImagen2() {
        return idImagen2;
    }

}
