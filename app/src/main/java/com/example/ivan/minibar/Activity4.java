package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Ivan on 28/11/2017.
 */

public class Activity4 extends Activity
{
    private static DBManager gestorDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void getTicketDetalle(Ticket ticket){
        gestorDB = new DBManager( getApplicationContext() );
        ticket.setLineasTicket( gestorDB.getLineasTicket(  ticket.getNumTicket() ) );

    }
}
