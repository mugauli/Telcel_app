package Utitilies;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.grapesoft.www.telcel.R;
/**
 * Created by Mugauli on 26/06/2016.
 */
public abstract class List_adapted_Video extends BaseAdapter {

    private ArrayList<?> entradas;
    private int R_layout_IdView;
    private Context contexto;
    TextView tv;

    public List_adapted_Video(Context contexto, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }
    int selectedPosition = 0;
    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (entradas.get(posicion), view);


        RadioButton r = (RadioButton)view.findViewById(R.id.rdfalla);
        if(r != null) {

            r.setChecked(posicion == selectedPosition);
            r.setTag(posicion);
            //  Log.e("RadioButton", "SetTag: "+ posicion);
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton r = (RadioButton)view.findViewById(R.id.rdfalla);
                    selectedPosition = (Integer) r.getTag();
                    notifyDataSetChanged();
                    Log.e("Click", "RadioButton: "+ selectedPosition);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entradas.get(posicion);
    }


    public int getRadioChecked() {
        return this.selectedPosition;
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }


    public abstract void onEntrada (Object entrada, View view);

}
