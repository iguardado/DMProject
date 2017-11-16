package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ivan on 11/9/17.
 */

public class Activity2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //Lista de Pedidos
        final ArrayList<String> productos;
        final ArrayAdapter<String> adapter;

        productos=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,productos);
        final ListView listaPedidos = (ListView) findViewById(R.id.listPedidos);
        listaPedidos.setAdapter(adapter);

        ImageButton btRefrescos = (ImageButton) findViewById(R.id.btRefresco);

        btRefrescos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productos.add("Refresco : 1.7");
                adapter.notifyDataSetChanged();
            }
        });
/*
        Button boton_ok = (Button) findViewById(R.id.button_Ok);
        boton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1);
                finish();
            }
        });

        Button boton_cancel = (Button) findViewById(R.id.button_Cancel);
        boton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(2);
                finish();
            }
        });

        */
    }
}
