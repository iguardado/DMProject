package com.example.ivan.minibar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    private static final int PEDIDOS = 2;
    private static final int TICKETS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        DBManager gestorDB = new DBManager( this.getApplicationContext() );

        Button btPedidos = (Button) findViewById(R.id.Pedidos);
        Button btTickets = (Button) findViewById(R.id.Tickets);

        //Actividade 2 ao pulsar dito boton de accion
        btPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity2.class);
                Inicio.this.startActivityForResult(intent, PEDIDOS);
            }
        });

        btTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                Inicio.this.startActivityForResult(intent, TICKETS);
            }
        });
/*
        Button botonAct3 = (Button) findViewById(R.id.b);
        botonAct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                Inicio.this.startActivityForResult(intent, TICKETS);
            }
        });
        */
    }

    public void onActivityResult(int requestCode,  int resultCode, Intent intent){
        switch (requestCode){
            case PEDIDOS:
                if(resultCode == 2){
                    Toast toast = Toast.makeText(getApplicationContext(), "Boton Cancela", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if(resultCode == 1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Boton Ok", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if(resultCode == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Boton Volver", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case TICKETS:
                Toast toast = Toast.makeText(getApplicationContext(), "Boton Terceira Actividade", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}

