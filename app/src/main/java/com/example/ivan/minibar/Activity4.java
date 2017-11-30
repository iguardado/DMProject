package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ivan on 28/11/2017.
 */

public class Activity4 extends Activity {
    private static DBManager gestorDB;
    private static Ticket ticket;
    private List<LineaTicket> lineasTicket;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        DBManager gestorDB = new DBManager(getApplicationContext());
        System.out.print(Integer.toString(ticket.getNumTicket()));
        gestorDB.getLineasTicket(ticket.getNumTicket());
        TextView txtTicket  = (TextView) findViewById(R.id.txtTicket);

        txtTicket.setText(Integer.toString(ticket.getNumTicket()));
    }

    public static void setTicket(Ticket t){
        ticket = t;
    }
}
