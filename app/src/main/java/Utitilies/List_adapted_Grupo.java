package Utitilies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import net.grapesoft.www.telcel.R;

import java.util.ArrayList;

/**
 * Created by Mugauli on 28/06/2016.
 */
public abstract class List_adapted_Grupo extends BaseAdapter {

    private ArrayList<?> entradas;
    private int R_layout_IdView;
    private Context context;

    public List_adapted_Grupo(Context contexto, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.context = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }
    int selectedPosition = 0;

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            Log.e("Click", "View null");
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

