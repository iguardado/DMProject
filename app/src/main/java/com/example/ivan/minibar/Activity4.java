package com.example.ivan.minibar;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 28/11/2017.
 */

public class Activity4 extends AppCompatActivity {
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
        Fecha = Fecha.split("-")[2]+"/"+Fecha.split("-")[1]+"/"+Fecha.split("-")[0];
        String Hora = ticket.getFechaTicket().split(" ")[1].substring(0,5);
        fechaticket.setText( "Fecha: "+ Fecha+" "+Hora );
        total.setText("TOTAL: " + String.format("%.2f", ticket.getTotal())+ "€");

        ivaticket.setText( "IVA: " + Integer.toString( ticket.getIvaTicket())+"%" );

        final ImageButton btPdf = (ImageButton) findViewById(R.id.btPdf);

        btPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Activity4.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    btPdf.setVisibility(View.GONE);
                }

                 int result = PdfCreator.GenerarPdf( findViewById(android.R.id.content).getRootView() ,
                        Activity4.this, "Ticket"+ticket.getNumTicket());
                if(result == 1){
                    File file = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS)+"/Minibar/" +"Ticket"+ticket.getNumTicket()+".pdf");
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file),"application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, "Open File");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                    finish();
                }
            }
        });

    }

    public static void setTicket(Ticket t){
        ticket = t;
    }
}
