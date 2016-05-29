package Utitilies;

/**
 * Created by Mugauli on 28/05/2016.
 */
public class Campos {

    char id;
    String nombre;
    //Constructor
    public Campos(char id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
        //prueba de Git
        //prueba 2 git
        //prueba 3 2 merge
    }
    @Override
    public String toString() {
        return nombre;
    }
    public char getId() {
        return id;
    }
}
