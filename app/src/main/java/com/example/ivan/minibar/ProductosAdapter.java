package com.example.ivan.minibar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.ivan.minibar.Activity2;


import java.util.ArrayList;

/**
 * Created by ivan on 11/22/17.
 */

public class ProductosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Producto> datos;


    public ProductosAdapter(Context context, ArrayList datos) {
        super(context, R.layout.listviewpedido, datos);

        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listviewpedido, null);

        final ImageButton imageButton = view.findViewById(R.id.btDelete);

        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Activity2.eliminar(datos.get(position));
            }
        });

        TextView cantidad = (TextView) view.findViewById(R.id.txtCantidad);
        cantidad.setText(Integer.toString(datos.get(position).getCantidad())+"x");

        TextView nombre = (TextView) view.findViewById(R.id.txtProducto);
        nombre.setText(datos.get(position).getNombre());

        // Devolvemos la vista para que se muestre en el ListView.
        return view;
    }

}
