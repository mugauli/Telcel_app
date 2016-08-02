package Utitilies;


/**
 * Created by Mugauli on 25/07/2016.
 */
public class DescElement {
    private int id;
    private String
            tipo,
            url_pdf,
            img_previa;

    public DescElement(){}

    public DescElement(int id,String img_previa, String tipo, String url_pdf) {
        this.id= id;
        this.img_previa = img_previa;
        this.tipo = tipo;
        this.url_pdf = url_pdf;
    }
    public int get_idDesc() {
        return id;
    }
    public String get_url_pdfDesc() {
        return url_pdf;
    }
    public String get_img_previaDesc() {
        return img_previa;
    }
    public String get_tipoDesc() {
        return tipo;
    }
    @Override
    public String toString() {
        return tipo;
    }
    public int getId() {
        return id;
    }

}
