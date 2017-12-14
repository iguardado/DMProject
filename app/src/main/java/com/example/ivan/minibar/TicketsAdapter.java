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

public class TicketsAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Ticket> datos;


    public TicketsAdapter(Context context, ArrayList datos) {
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
        View view = inflater.inflate(R.layout.listviewticket, null);


        final ImageButton imageButton = view.findViewById(R.id.btnverticket);

        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Activity4.setTicket(datos.get(position));
                Intent intent = new Intent( getContext() , Activity4.class);
                getContext().startActivity(intent);

            }
        });

        TextView numticket = (TextView) view.findViewById(R.id.numticket);
        numticket.setText( Integer.toString( datos.get(position).getNumTicket() ) );

        TextView fecha = (TextView) view.findViewById(R.id.fechaticket);
        fecha.setText(datos.get(position).getFechaTicket());

        TextView importe = (TextView) view.findViewById(R.id.importeticket);
        importe.setText(String.format("%.2f", datos.get(position).getTotal())+"€");

        // Devolvemos la vista para que se muestre en el ListView.
        return view;
    }

}