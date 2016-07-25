package Utitilies;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.Dictionary;

/**
 * Created by Mugauli on 20/06/2016.
 */
public class Lista_Entrada {


    private  Dictionary<String,String> imagenesSlide;
    private int idImagen,idImagen2,idradio;
    private Bitmap img_previa;
    private String
            contenido,
            tipo,
            id,
            titulo,
            img_detalle,
            url,
            duracion,
            fecha,
            mes,
            textoEncima,
            textoDebajo;

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

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha,String tipo,String contenido) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public Lista_Entrada(String mes, String id, Bitmap img_previa, String titulo, String url, String textoDebajo,  Dictionary<String,String> imagenesSlide) {

        this.mes = mes;
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.textoDebajo = textoDebajo;
        this.imagenesSlide = imagenesSlide;
    }

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String url,String textoDebajo,  Dictionary<String,String> imagenesSlide) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.textoDebajo = textoDebajo;
        this.imagenesSlide = imagenesSlide;
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
    public String get_mes() {
        return mes;
    }
    public Dictionary<String,String> get_imagenesSlide() {
        return imagenesSlide;
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
    public String get_contenido() {
        return contenido;
    }
    public String get_tipo() {
        return tipo;
    }
}
