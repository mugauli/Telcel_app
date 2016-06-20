package Utitilies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import net.grapesoft.www.telcel.Lista_entrada;
import net.grapesoft.www.telcel.R;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mugauli on 19/06/2016.
 */
public class GetNetImage extends AsyncTask<String,Void,Bitmap> {

    private Exception exception;

    ProgressDialog dialog;

    Activity activity;

    ImageView img;


    @Override

    protected void onPreExecute() { //Este método es el primero que se ejecuta al llamar a la clase, después pasa al doInBackground que le da una respuesta onPostExecute. En este caso el doInBackground devuelve un Bitmap pero puede devolver cualquier tipo de variable, indicando el tipo en el último parámetros del extends AsyncTask

    }




    @Override
    protected Bitmap doInBackground(String... params) {
        URL imageUrl = null;
        Bitmap imageRT= null;
        try {

            imageUrl = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imageRT = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();

            return imageRT;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }


    @Override

    protected void onPostExecute(Bitmap result) { //Este método recibe el Bitmap que ha generado el método doInBackground
        super.onPostExecute(result);

    }
}
