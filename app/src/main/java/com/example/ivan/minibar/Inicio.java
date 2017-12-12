package com.example.ivan.minibar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.index_menu, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        Intent intent;
        switch( menuItem.getItemId() ) {
            case R.id.pedidos:
                intent = new Intent(getApplicationContext(), Activity2.class);
                Inicio.this.startActivityForResult(intent, PEDIDOS);
                toret = true;
                break;
            case R.id.tickets:
                intent = new Intent(getApplicationContext(), Activity3.class);
                Inicio.this.startActivityForResult(intent, TICKETS);
                toret = true;
                break;
        }
        return toret;
    }

    public void onActivityResult(int requestCode,  int resultCode, Intent intent){
        switch (requestCode){
            case PEDIDOS:
                if(resultCode == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Inicio", Toast.LENGTH_LONG);
                    toast.show();
                }
                if(resultCode == 1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Pedido Guardado", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case TICKETS:
                Toast toast = Toast.makeText(getApplicationContext(), "Inicio", Toast.LENGTH_LONG);
                toast.show();
                break;
        }
    }
}

