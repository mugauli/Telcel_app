package Utitilies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Mugauli on 05/10/2016.
 */
public class FileCache implements Serializable {

    private static final long serialVersionUID = 1L;

    public FileCache() { }

    public boolean saveObject(Context c,Bitmap obj,String name) {

        String nombre = getNombre(name);
        Log.e("cache_saveObject",nombre);

        final File suspend_f = new File(c.getCacheDir(),nombre );

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean keep = true;

        try {
           fos = new FileOutputStream(suspend_f);
           oos = new ObjectOutputStream(fos);
           //oos.writeObject(obj);
            if(obj!=null){
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                boolean success = obj.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
                if(success){
                    oos.writeObject(byteStream.toByteArray());
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            keep = false;
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
                if (keep == false) suspend_f.delete();
            } catch (Exception e) { /* do nothing */ }
        }

        return keep;
    }

    public byte[] getObject(Context c,String name) {

        String nombre = getNombre(name);
        Log.e("cache_getObjet",nombre);

        final File suspend_f = new File(c.getCacheDir(),nombre);
        byte[] simpleClass = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {
            fis = new FileInputStream(suspend_f);
            is = new ObjectInputStream(fis);

            //is.defaultReadObject();
            // All other fields that you serialized
            byte[] image = (byte[]) is.readObject();
           simpleClass = image;

            //simpleClass = (Bitmap) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return simpleClass;
    }

    public String getNombre(String url)
    {
        String name= "";

        Log.e("cache_getNombre",url);

        String[] nombre = url.split("/");
        if(nombre.length > 1) {
            Log.e("cache_getNombre_return",nombre[nombre.length - 2]+ "_" +nombre[nombre.length - 1]);
            return nombre[nombre.length - 2]+ "_" +nombre[nombre.length - 1];
        }
        else{
            Log.e("cache_getNombre_return","error");
            return "error";
        }


    }

}
