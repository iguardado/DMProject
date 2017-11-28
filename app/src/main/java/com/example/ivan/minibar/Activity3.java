package com.example.ivan.minibar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 27/11/2017.
 */

public class Activity3 extends Activity {

    private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private static TicketsAdapter adapter ;
    private DBManager gestorDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        gestorDB = new DBManager( this.getApplicationContext() );

        tickets = gestorDB.getTickets();

        adapter = new TicketsAdapter(this, tickets);
        ListView lvtickets = (ListView) findViewById( R.id.lvTickets );
        lvtickets.setAdapter(adapter);

    }

    /*
    public  static void viewTicket(){
        Intent intent = new Intent(  , Activity2.class);
        Activity3.this.startActivityForResult(intent, 4);
    }
    */
}
