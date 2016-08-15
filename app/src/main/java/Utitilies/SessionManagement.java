package Utitilies;

/**
 * Created by Mugauli on 01/06/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import net.grapesoft.www.telcel.LoginActivity;

import java.util.HashMap;

public class SessionManagement {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;






    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "TELCEL";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //INICIALES
    public static final String KEY_TOKEN = "token";
    public static final String KEY_DATO = "dato";
    public static final String KEY_CAMPO = "campo";
    public static final String KEY_PASS = "pass";

    //RESPONSE
    // {"id":"5","num_empleado":"ANDROID","num_celular":"ANDROID","region":"1","nombre":"ANDROID","paterno":"ANDROID","materno":"ANDROID","interes_1":null,"interes_2":null}
    public static final String KEY_PD_ID = "id";
    public static final String KEY_PD_NUM_EMPLEADO = "num_empleado";
    public static final String KEY_PD_NUM_CELULAR = "num_celular";
    public static final String KEY_PD_REGION = "region";
    public static final String KEY_PD_NOMBRE = "nombre";
    public static final String KEY_PD_PATERNO = "paterno";
    public static final String KEY_PD_MATERNO = "materno";
    public static final String KEY_PD_INTERES_1 = "interes_1";
    public static final String KEY_PD_INTERES_2 = "interes_2";
    public static final String KEY_PD_CORREO = "correo";

    public static final String KEY_COMUNICACION_INTERNA_PODCAST = "comunicacion_interna_podcast";
    public static final String KEY_COMUNICACION_INTERNA_VIDEO = "comunicacion_interna_video";
    public static final String KEY_COMUNICACION_INTERNA_REVISTA = "comunicacion_interna_revista";
    public static final String KEY_COMUNICACION_INTERNA_NOTICIAS = "comunicacion_interna_noticias";
    public static final String KEY_COMUNICACION_INTERNA_COMUNICADOS = "comunicacion_interna_comunicados";
    public static final String KEY_COMUNICACION_INTERNA_GRUPO_CARSO = "comunicacion_interna_grupo_carso";
    public static final String KEY_COMUNICACION_INTERNA_CAMPANAS_INTERNAS = "comunicacion_interna_campanas_internas";
    public static final String KEY_COMUNICACION_INTERNA_GALERIA = "comunicacion_interna_galeria";
    public static final String KEY_PRODUCTOS_CAMPANAS = "productos_campana";
    public static final String KEY_PRODUCTOS_LANZAMIENTOS = "productos_lanzamientos";
    public static final String KEY_PRODUCTOS_MES = "productos_mes";
    public static final String KEY_PRODUCTOS_SVA = "productos_sva";
    public static final String KEY_HOME = "home";
    public static final String KEY_PROMOS = "promos";
    public static final String KEY_DESCUENTOS = "descuentos";
    public static final String KEY_PREGUNTA = "pregunta";
    public static final String KEY_TRIVIAS = "trivias";
    public static final String KEY_TRIVIAS_CONTESTADO = "trivias_contestado";





    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String token, String dato, String campo, String pass, String id, String num_empelado, String num_celular,
                                   String region, String nombre, String paterno, String materno, String interes_1, String interes_2,String correo) {
        editor.clear();
        //SHARED
        editor.putBoolean(IS_LOGIN, true);

        // INICIALES
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_DATO,dato);
        editor.putString(KEY_CAMPO,campo);
        editor.putString(KEY_PASS,pass);

        //RESPONSE
        editor.putString(KEY_PD_ID,id);
        editor.putString(KEY_PD_NUM_EMPLEADO,num_empelado);
        editor.putString(KEY_PD_NUM_CELULAR,num_celular);
        editor.putString(KEY_PD_REGION,region);
        editor.putString(KEY_PD_NOMBRE,nombre);
        editor.putString(KEY_PD_PATERNO, paterno);
        editor.putString(KEY_PD_MATERNO,materno);
        editor.putString(KEY_PD_INTERES_1,interes_1);
        editor.putString(KEY_PD_INTERES_2,interes_2);
        editor.putString(KEY_PD_CORREO,correo);


        // commit changes
        editor.commit();
    }

    public void createPodcastSession(String podcast){ editor.putString(KEY_COMUNICACION_INTERNA_PODCAST,podcast);editor.commit();}

    public void createVideoSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_VIDEO,value); editor.commit();  }

    public void createRevistaSession(String value) { editor.putString(KEY_COMUNICACION_INTERNA_REVISTA,value); editor.commit();  }

    public void createNoticiasSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_NOTICIAS,value); editor.commit(); }

    public void createComunicadosSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_COMUNICADOS,value); editor.commit(); }

    public void createGrupoCarsoSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_GRUPO_CARSO,value); editor.commit(); }

    public void createCampanasInternasSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_CAMPANAS_INTERNAS,value);  editor.commit(); }

    public void createGaleriaSession(String value){ editor.putString(KEY_COMUNICACION_INTERNA_GALERIA,value); editor.commit(); }

    //productos
    public void createCampanasProductosSession(String value){ editor.putString(KEY_PRODUCTOS_CAMPANAS,value);  editor.commit(); }

    public void createLanzamientosProductosSession(String value){ editor.putString(KEY_PRODUCTOS_LANZAMIENTOS,value);  editor.commit(); }

    public void createMesProductosSession(String value){ editor.putString(KEY_PRODUCTOS_MES,value);  editor.commit(); }

    public void createSVAProductosSession(String value){ editor.putString(KEY_PRODUCTOS_SVA,value);  editor.commit(); }

    //HOME
    public void createHomeSession(String value){ editor.putString(KEY_HOME,value);  editor.commit(); }

    //PROMOS
    public void createPromosSession(String value){ editor.putString(KEY_PROMOS,value);  editor.commit(); }

    //Descuentos
    public void createDescuentosSession(String value){ editor.putString(KEY_DESCUENTOS,value);  editor.commit(); }

    //Trivias
    public void createTriviasSession(String value){ editor.putString(KEY_TRIVIAS,value);  editor.commit(); }

    public void createTriviasContestadoSession(String value){ editor.putString(KEY_TRIVIAS_CONTESTADO,value);  editor.commit(); }

    //Descuentos
    public void createPreguntaSession(String value){ editor.putString(KEY_PREGUNTA,value);  editor.commit(); }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){


        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_PD_ID, pref.getString(KEY_PD_ID,null));
        user.put(KEY_PD_NUM_EMPLEADO, pref.getString(KEY_PD_NUM_EMPLEADO,null));
        user.put(KEY_PD_NUM_CELULAR, pref.getString(KEY_PD_NUM_CELULAR,null));
        user.put(KEY_PD_REGION, pref.getString(KEY_PD_REGION,null));
        user.put(KEY_PD_NOMBRE, pref.getString(KEY_PD_NOMBRE,null));
        user.put(KEY_PD_PATERNO, pref.getString(KEY_PD_PATERNO, null));
        user.put(KEY_PD_MATERNO, pref.getString(KEY_PD_MATERNO,null));
        user.put(KEY_PD_INTERES_1, pref.getString(KEY_PD_INTERES_1,null));
        user.put(KEY_PD_INTERES_2, pref.getString(KEY_PD_INTERES_2,null));
        user.put(KEY_PD_CORREO, pref.getString(KEY_PD_CORREO,null));

        // return user
        return user;
    }

    public String getPodcastDetails(){return pref.getString(KEY_COMUNICACION_INTERNA_PODCAST,null);}

    public String getVideoDetails(){
        return pref.getString(KEY_COMUNICACION_INTERNA_VIDEO,null);
    }

    public String getRevistaDetails() { return pref.getString(KEY_COMUNICACION_INTERNA_REVISTA, null); }

    public String getNoticiasDetails(){ return pref.getString(KEY_COMUNICACION_INTERNA_NOTICIAS,null); }

    public String getComunicadosDetails(){ return pref.getString(KEY_COMUNICACION_INTERNA_COMUNICADOS,null); }

    public String getGrupoCarsoDetails() { return pref.getString(KEY_COMUNICACION_INTERNA_GRUPO_CARSO, null); }

    public String getCampanasInternasDetails() { return pref.getString(KEY_COMUNICACION_INTERNA_CAMPANAS_INTERNAS, null); }

    public String getGaleriaDetails() {  return pref.getString(KEY_COMUNICACION_INTERNA_GALERIA, null); }

    public String getCampanaProductosDetails() { return pref.getString(KEY_PRODUCTOS_CAMPANAS, null); }

    public String getLanzamientosProductosDetails() { return pref.getString(KEY_PRODUCTOS_LANZAMIENTOS, null); }

    public String getMesProductosDetails() { return pref.getString(KEY_PRODUCTOS_MES, null); }

    public String getSVAProductosDetails() { return pref.getString(KEY_PRODUCTOS_SVA, null); }

    public String getHomeDetails() { return pref.getString(KEY_HOME, null); }

    public String getPromosDetails() { return pref.getString(KEY_PROMOS, null); }

    public String getDescuentosDetails() { return pref.getString(KEY_DESCUENTOS, null); }

    public String getTriviasDetails() { return pref.getString(KEY_TRIVIAS, null); }

    public String getTriviasContestadoDetails() { return pref.getString(KEY_TRIVIAS_CONTESTADO, null); }

    public String getPreguntaDetails() { return pref.getString(KEY_PREGUNTA, null); }



    //productos
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}