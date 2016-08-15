package Utitilies;

/**
 * Created by Mugauli on 13/08/2016.
 */
public class Respuestas {

    int idResp;
    String txtrespuesta;
    Boolean valRespuesta;

    public Respuestas(){}

    public Respuestas(int idResp,String txtrespuesta,Boolean valRespuesta){
        this.idResp = idResp;
        this.txtrespuesta = txtrespuesta;
        this.valRespuesta = valRespuesta;
    }

    public int get_idResp() {
        return idResp;
    }
    public String get_txtrespuesta() {
        return txtrespuesta;
    }
    public Boolean get_valRespuesta() {
        return valRespuesta;
    }
}
