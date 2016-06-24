package net.grapesoft.www.telcel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.GetNetImage;
import Utitilies.Lista_Entrada;
import Utitilies.SessionManagement;

public class Detalle_noticia extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);




        String[] ltEntrada = getIntent().getStringArrayExtra("noticia_selected");

        String noticiaId = ltEntrada[0].toString();
        String noticiaTitulo = ltEntrada[1].toString();
        String noticiaFecha = ltEntrada[2].toString();
        String noticiaTexto = ltEntrada[3].toString();
        String noticiaImagen = ltEntrada[4].toString();

        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();

        params.add(imageHttpAddress + noticiaImagen);


        ImageView imagen_noticias = (ImageView) findViewById(R.id.imagenUN);
        if (imagen_noticias != null) {
            URL imageUrl = null;

            Bitmap loadedImage = null;
            try {

                loadedImage = new GetNetImage().execute().get();
                imagen_noticias.setImageBitmap(loadedImage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }



        }
        TextView noticiafecha = (TextView) findViewById(R.id.fechaUN);
        if (noticiafecha != null)
            noticiafecha.setText(noticiaFecha);

        TextView noticiatitulo = (TextView) findViewById(R.id.titUN);

        if (noticiatitulo != null)
            noticiatitulo.setText(noticiaTitulo);

        TextView noticiaDescripcion= (TextView) findViewById(R.id.descUN);

        if (noticiaDescripcion != null) {
            noticiaDescripcion.setText(noticiaTexto);
        }



    }
}
