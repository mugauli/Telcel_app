package Utitilies;

/**
 * Created by Mugauli on 01/06/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import net.grapesoft.www.telcel.login;

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
    public static final String KEY_ID = "id";
    public static final String KEY_NUM_EMPLEADO = "num_empleado";
    public static final String KEY_NUM_CELULAR = "num_celular";
    public static final String KEY_REGION = "region";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_PATERNO = "paterno";
    public static final String KEY_MATERNO = "materno";
    public static final String KEY_INTERES_1 = "interes_1";
    public static final String KEY_INTERES_2 = "interes_2";

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
                                   String region, String nombre, String paterno, String materno, String interes_1, String interes_2) {

        //SHARED
        editor.putBoolean(IS_LOGIN, true);

        // INICIALES
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_DATO,dato);
        editor.putString(KEY_CAMPO,campo);
        editor.putString(KEY_PASS,pass);

        //RESPONSE
        editor.putString(KEY_ID,id);
        editor.putString(KEY_NUM_EMPLEADO,num_empelado);
        editor.putString(KEY_NUM_CELULAR,num_celular);
        editor.putString(KEY_REGION,region);
        editor.putString(KEY_NOMBRE,nombre);
        editor.putString(KEY_PATERNO, paterno);
        editor.putString(KEY_MATERNO,materno);
        editor.putString(KEY_INTERES_1,interes_1);
        editor.putString(KEY_INTERES_2,interes_2);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, login.class);
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
        user.put(KEY_ID, pref.getString(KEY_ID,null));
        user.put(KEY_NUM_EMPLEADO, pref.getString(KEY_NUM_EMPLEADO,null));
        user.put(KEY_NUM_CELULAR, pref.getString(KEY_NUM_CELULAR,null));
        user.put(KEY_REGION, pref.getString(KEY_REGION,null));
        user.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE,null));
        user.put(KEY_PATERNO, pref.getString(KEY_PATERNO, null));
        user.put(KEY_MATERNO, pref.getString(KEY_MATERNO,null));
        user.put(KEY_INTERES_1, pref.getString(KEY_INTERES_1,null));
        user.put(KEY_INTERES_2, pref.getString(KEY_INTERES_2,null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, login.class);
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