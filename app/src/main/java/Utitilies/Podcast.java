package Utitilies;

/**
 * Created by Mugauli on 18/06/2016.
 */
public class Podcast {
    String id;
    String titulo;
    String img_previa;
    String url_podcast;
    String duracion;

    //Constructor
    public Podcast(String s, String id, String titulo, String img_previa, String url_podcast, String duracion) {
        super();
        this.id = id;
        this.titulo=titulo;
        this.img_previa=img_previa;
        this.url_podcast=url_podcast;
        this.duracion=duracion;
    }
    @Override
    public String toString() {
        return titulo;
    }
    public String getId() {
        return id;
    }
}
