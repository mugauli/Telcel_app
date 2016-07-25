package Utitilies;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Mugauli on 25/07/2016.
 */
public class SvaElement {

    private Bitmap img_previa;
    private String
            id,
            titulo,
            img_detalle,
            fecha,
            textoDebajo,
            img_mini;

    public SvaElement(){}

    public SvaElement(String id, Bitmap img_previa, String titulo, String img_detalle, String textoDebajo, String fecha, String img_mini) {

        this.id = id;
        this.img_previa = img_previa;
        this.titulo = titulo;
        this.img_detalle = img_detalle;
        this.textoDebajo = textoDebajo;
        this.fecha = fecha;
        this.img_mini= img_mini;

    }

    public Bitmap get_img_previaSva() {
        return img_previa;
    }
    public String get_img_detalleSva() {
        return img_detalle;
    }
    public String get_fechaSva() {
        return fecha;
    }
    public String get_idSva() {
        return id;
    }
    public String get_tituloSva() {
        return titulo;
    }
    public String get_textoDebajoSva() {
        return textoDebajo;
    }
    public String get_img_miniSva() {
        return img_mini;
    }
}
