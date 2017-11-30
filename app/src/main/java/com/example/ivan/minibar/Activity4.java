package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 28/11/2017.
 */

public class Activity4 extends Activity {
    private static DBManager gestorDB;
    private static Ticket ticket;
    private ArrayList<LineaTicket> lineasTicket;
    private static LineaTicketAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        DBManager gestorDB = new DBManager(getApplicationContext());
        System.out.print(Integer.toString(ticket.getNumTicket()));
        lineasTicket = gestorDB.getLineasTicket(ticket.getNumTicket());

        adapter = new LineaTicketAdapter(this, lineasTicket);
        ListView listaLineasTicket = (ListView) findViewById(R.id.listlineasticket);

        listaLineasTicket.setAdapter(adapter);

        TextView numticket = (TextView) findViewById(R.id.lblnumticket);
        TextView fechaticket = (TextView) findViewById(R.id.lblfechaticket);
        TextView total = (TextView) findViewById(R.id.lblTotal);
        TextView ivaticket = (TextView) findViewById(R.id.lblIVA);

        numticket.setText( "NumTicket: " + Integer.toString( ticket.getNumTicket() ) );
        String Fecha = ticket.getFechaTicket().split(" ")[0];
        Log.i("HPRAAA",Fecha);
        Fecha = Fecha.split("-")[2]+"/"+Fecha.split("-")[1]+"/"+Fecha.split("-")[0];
        String Hora = ticket.getFechaTicket().split(" ")[1].substring(0,5);
        fechaticket.setText( "Fecha: "+ Hora+" "+Fecha  );
        total.setText("TOTAL: " + Double.toString(ticket.getTotal())+ "€");

        ivaticket.setText( "IVA: " + Integer.toString( ticket.getIvaTicket())+"%" );

    }

    public static void setTicket(Ticket t){
        ticket = t;
    }
}
