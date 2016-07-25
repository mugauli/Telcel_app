package Utitilies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import net.grapesoft.www.telcel.R;

import java.util.ArrayList;

/**
 * Created by Mugauli on 26/06/2016.
 */
public abstract class List_adapted_Producto_Mes extends BaseAdapter {

    private ArrayList<?> entradas;
    private int R_layout_IdView;
    private Context context;

    public List_adapted_Producto_Mes(Context contexto, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.context = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, null);

        if (convertView == null) {
                 Log.e("Click", "View null");
                 LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = vi.inflate(R_layout_IdView, null);
             }
             onEntrada (entradas.get(position), convertView);

        return retval;
    }

   // int selectedPosition = 0;

   // @Override
   // public View getView(int posicion, View view, ViewGroup pariente) {
   //     if (view == null) {
   //         Log.e("Click", "View null");
   //         LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   //         view = vi.inflate(R_layout_IdView, null);
   //     }
   //     onEntrada (entradas.get(posicion), view);
//
//
   //     RadioButton r = (RadioButton)view.findViewById(R.id.rdfalla);
   //     if(r != null) {
//
   //         r.setChecked(posicion == selectedPosition);
   //         r.setTag(posicion);
   //         //  Log.e("RadioButton", "SetTag: "+ posicion);
   //         r.setOnClickListener(new View.OnClickListener() {
   //             @Override
   //             public void onClick(View view) {
   //                 RadioButton r = (RadioButton)view.findViewById(R.id.rdfalla);
   //                 selectedPosition = (Integer) r.getTag();
   //                 notifyDataSetChanged();
   //                 Log.e("Click", "RadioButton: "+ selectedPosition);
   //             }
   //         });
   //     }
   //     return view;
   // }
//
   // @Override
   // public int getCount() {
   //     return entradas.size();
   // }
//
   // @Override
   // public Object getItem(int posicion) {
   //     return entradas.get(posicion);
   // }
//
//
   // public int getRadioChecked() {
   //     return this.selectedPosition;
   // }
//
   // @Override
   // public long getItemId(int posicion) {
   //     return posicion;
   // }
//
//
    public abstract void onEntrada (Object entrada, View view);



}
