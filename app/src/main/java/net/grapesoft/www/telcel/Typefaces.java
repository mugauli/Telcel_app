package net.grapesoft.www.telcel;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by memoHack on 06/06/2016.
 */

public class Typefaces{

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String name){
        synchronized(cache){
            if(!cache.containsKey(name)){
                Typeface t = Typeface.createFromAsset(
                        c.getAssets(),
                        String.format("fonts/%s.ttf", name)
                );
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }

}
