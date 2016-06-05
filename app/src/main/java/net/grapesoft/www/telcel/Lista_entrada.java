package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 01/06/2016.
 */
public class Lista_entrada {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
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

    public String get_textoEncima() {
        return textoEncima;
    }

    public String get_textoDebajo() {
        return textoDebajo;
    }

    public int get_idImagen() {
        return idImagen;
    }

}
