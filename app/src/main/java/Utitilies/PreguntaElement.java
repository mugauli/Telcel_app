package Utitilies;


/**
 * Created by Mugauli on 25/07/2016.
 */
public class PreguntaElement {

    private String
            idPreg,
            txtRespuesta,
            valRespuesta;

    public PreguntaElement(){}

    public PreguntaElement(String idPreg,String txtRespuesta, String valRespuesta) {
        this.idPreg= idPreg;
        this.txtRespuesta = txtRespuesta;
        this.valRespuesta = valRespuesta;

    }
    public String get_idPreg() {
        return idPreg;
    }
    public String get_txtRespuesta() {
        return txtRespuesta;
    }
    public String get_valRespuesta() {
        return valRespuesta;
    }

}
