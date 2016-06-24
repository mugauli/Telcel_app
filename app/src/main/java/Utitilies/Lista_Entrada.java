package Utitilies;

import android.graphics.Bitmap;

import org.json.JSONArray;

/**
 * Created by Mugauli on 20/06/2016.
 */
public class Lista_Entrada {

    private int idImagen,idImagen2,idradio;
    private String textoEncima;
    private String textoDebajo;

    private String id;
    private String titulo;
    private Bitmap img_previa;
    private String img_detalle;
    private String url;
    private String duracion;
    private String fecha;
    private JSONArray  json;

    public Lista_Entrada (int idImagen, String textoEncima, String textoDebajo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada (String textoEncima, String textoDebajo) {

        this.textoEncima =  textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada (int idradio, String textoDebajo) {

        this.idradio = idradio;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada (int idImagen,String textoEncima, String textoDebajo,int idImagen2) {

        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
        this.idImagen2 = idImagen2;
    }

    public Lista_Entrada (String id,Bitmap img_previa,String titulo, String url,String duracion, int idImagen2) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.duracion = duracion;
        this.idImagen2 = idImagen2;


    }

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String url, String duracion, JSONArray json) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.duracion = duracion;
        this.json = json;
    }

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
    }

    public Bitmap get_img_previa() {
        return img_previa;
    }
    public String get_img_detalle() {
        return img_detalle;
    }
    public String get_fecha() {
        return fecha;
    }
    public String get_id() {
        return id;
    }
    public String get_titulo() {
        return titulo;
    }
    public String get_url() {
        return url;
    }
    public String get_duracion() {
        return duracion;
    }
    public JSONArray get_json() {
        return json;
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
