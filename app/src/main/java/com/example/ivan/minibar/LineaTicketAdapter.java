package com.example.ivan.minibar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ivan on 11/22/17.
 */

public class LineaTicketAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<LineaTicket> datos;


    public LineaTicketAdapter(Context context, ArrayList datos) {
        super(context, R.layout.listviewticket, datos);

        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listviewlineaticket, null);

        Producto prod = datos.get(position).getProducto();

        TextView unidades = (TextView) view.findViewById(R.id.lblunidades);
        unidades.setText( Integer.toString( datos.get(position).getUnidades() ) );

        TextView concepto = (TextView) view.findViewById(R.id.lblconcepto);
        concepto.setText( prod.getNombre() );

        TextView precio = (TextView) view.findViewById(R.id.lblprecio);
        precio.setText(Double.toString( prod.getPrecio() ));

        TextView subtotal = (TextView) view.findViewById(R.id.lblsubtotal);
        subtotal.setText(Double.toString( prod.getPrecio() * datos.get(position).getUnidades() ));

        // Devolvemos la vista para que se muestre en el ListView.
        return view;
    }

}