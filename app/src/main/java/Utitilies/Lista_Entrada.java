package Utitilies;

import android.graphics.Bitmap;
import org.json.JSONArray;
import java.util.ArrayList;

/**
 * Created by Mugauli on 20/06/2016.
 */

public class Lista_Entrada {

    private ArrayList<String> imagenesSlide;
    private int idImagen,idImagen2,idradio,type,idImagenDetalle, idImagenA,idSiguiente;
    private Bitmap img_previa;
    private SvaElement svaElement1,svaElement2;
    private ArrayList<DescElement> pdf;
    private String
            contenido,
            contenidos,
            tipo,
            id,
            titulo,
            img_detalle,
            url,
            duracion,
            fecha,
            mes,
            textoEncima,
            textoDebajo,
            img_mini,
            seccion,
            jsonStr,

            tituloSig,
            imagenSig,
            textoSig,
            preguntas;

    private JSONArray  json;

    public Lista_Entrada (int idImagen, String textoEncima, String textoDebajo) {
        this.idImagen = idImagen;
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada (int idImagen, int idImagen2, String textoDebajo) {
        this.idImagen = idImagen;
        this.idImagen2 = idImagen2;
        this.textoDebajo = textoDebajo;
    }

    //Sitios de interes

    public Lista_Entrada (int idImagenA, int idImagenDetalle,int idImagen, String textoDebajo) {
        this.idImagen = idImagen;
        this.idImagenA = idImagenA;
        this.idImagenDetalle = idImagenDetalle;
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

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha, String img_mini,SvaElement svaElement1,int type) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
        this.img_mini= img_mini;
        this.type = type;
        this.svaElement1 = svaElement1;

    }

    public Lista_Entrada(SvaElement svaElement1,SvaElement svaElement2,int type) {

        this.svaElement1 = svaElement1;
        this.svaElement2 = svaElement2;
        this.type = type;
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
    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha,String tipo) {
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
        this.tipo = tipo;


    }

    public Lista_Entrada(String id, Bitmap img_previa, String titulo, String url, String textoDebajo,  ArrayList<String> imagenesSlide,String jsonStr, int idSiguiente,String tituloSig,String imagenSig, String textoSig ) {
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.textoDebajo = textoDebajo;
        this.imagenesSlide = imagenesSlide;
        this.jsonStr = jsonStr;
        this.idSiguiente = idSiguiente;
        this.tituloSig = tituloSig;
        this.imagenSig = imagenSig;
        this.textoSig = textoSig;
    }

    public Lista_Entrada(String mes, String id, Bitmap img_previa, String titulo, String url, String textoDebajo,  ArrayList<String> imagenesSlide) {

        this.mes = mes;
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.textoDebajo = textoDebajo;
        this.imagenesSlide = imagenesSlide;
    }

    public Lista_Entrada(String id,Bitmap img_previa,String titulo,String url,String textoDebajo,String jsonStr,int idSiguiente)    {
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.textoDebajo = textoDebajo;
        this.jsonStr = jsonStr;
        this.idSiguiente = idSiguiente;
    }

    public Lista_Entrada (String id,Bitmap img_previa,String titulo, String tipo,String textoDebajo,int ids,String img_detalle) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.tipo = tipo;
        this.textoDebajo = textoDebajo;
        this.img_detalle = img_detalle;
    }

    public Lista_Entrada (String id,String titulo, String tipo,String textoDebajo,String img_detalle,String preguntas) {

        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.textoDebajo = textoDebajo;
        this.img_detalle = img_detalle;
        this.preguntas = preguntas;
    }
    //Home
    public Lista_Entrada (String seccion,String id,Bitmap img_previa,String titulo, String url,String duracion, int idImagen2) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.duracion = duracion;
        this.idImagen2 = idImagen2;
        this.seccion = seccion;
    }

    public Lista_Entrada (String seccion,String id,Bitmap img_previa,String titulo, String img_detalle,String textoDebajo) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.seccion = seccion;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada(String seccion,String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha) {
        this.seccion = seccion;
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
    }

    public Lista_Entrada (String seccion,String id,Bitmap img_previa,String titulo, String img_mini,String img_detalle,String textoDebajo,String fecha) {
        this.seccion = seccion;
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_mini = img_mini;
        this.img_detalle = img_detalle;
        this.fecha = fecha;
        this.textoDebajo = textoDebajo;
    }

    public Lista_Entrada(String seccion,String id, Bitmap img_previa, String titulo, String url, String duracion, JSONArray json) {
        this.seccion= seccion;
        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.url = url;
        this.duracion = duracion;
        this.json = json;
    }

    public Lista_Entrada (int idImagen, String titulo, ArrayList<DescElement> pdf) {
        this.idImagen = idImagen;
        this.titulo = titulo;
        this.pdf = pdf;
    }

    //End Home




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
    public ArrayList<String> get_imagenesSlide() {
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
    public String get_contenidos() {
        return contenidos;
    }
    public String get_tipo() {
        return tipo;
    }
    public String get_img_mini() {
        return img_mini;
    }
    public SvaElement get_svaelement1() {
        return svaElement1;
    }
    public SvaElement get_svaelement2() {
        return svaElement2;
    }
    public int get_type() {
        return type;
    }
    public String get_seccion() {
        return seccion;
    }
    public String get_jsonStr() { return jsonStr; }
    public int get_idSiguiente() { return idSiguiente; }
    public ArrayList<DescElement> get_pdf() {
        return pdf;
    }
    public String get_imagenSig() { return imagenSig; }
    public String get_tituloSig() { return tituloSig; }
    public String get_textoSig() {
        return textoSig;
    }
    public int get_idImagenDetalle(){return idImagenDetalle; }
    public int get_idImagenA(){return idImagenA;}
    public String get_preguntas() {
        return preguntas;
    }

}
