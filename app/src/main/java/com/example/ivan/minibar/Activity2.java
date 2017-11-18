package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Activity2 extends Activity {

    private ArrayList<String> productos = new ArrayList<String>();;
    private Map<String,Producto> mapProductos = new LinkedHashMap<>();
    private ArrayAdapter<String> adapter ;
    private ListView listaPedidos ;
    private TextView lblTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        final ImageButton btRefrescos = findViewById(R.id.btRefresco);
        final ImageButton btCervezas = findViewById(R.id.btCervexa);
        final ImageButton btCafes = findViewById(R.id.btCafe);
        final ImageButton btCombinados = findViewById(R.id.btCombinado);
        final ImageButton btZumos = findViewById(R.id.btZumos);
        final ImageButton btChupitos = findViewById(R.id.btChupitos);
        final Producto refresco = new Producto("Refresco", 1.7);
        final Producto cerveza = new Producto("Cerveza", 1.7);
        final Producto zumo = new Producto("Zumo", 2.0);
        final Producto cafe = new Producto("Café", 1.0);
        final Producto combinado = new Producto("Combinado", 3.5);
        final Producto chupito = new Producto("Chupito", 1.5);

        lblTotal = (TextView) findViewById(R.id.txtTotal);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,productos);
        listaPedidos = (ListView) findViewById(R.id.listPedidos);

        listaPedidos.setAdapter(adapter);

        btRefrescos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresco.añadir();
                mapProductos.put(refresco.getNombre(), refresco);
                actualizaListView();
            }
        });

        btCervezas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerveza.añadir();
                mapProductos.put(cerveza.getNombre(), cerveza);
                actualizaListView();
            }
        });

        btCafes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cafe.añadir();
                mapProductos.put(cafe.getNombre(), cafe);
                actualizaListView();
            }
        });

        btZumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zumo.añadir();
                mapProductos.put(zumo.getNombre(), zumo);
                actualizaListView();
            }
        });

        btCombinados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combinado.añadir();
                mapProductos.put(combinado.getNombre(), combinado);
                actualizaListView();
            }
        });

        btChupitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupito.añadir();
                mapProductos.put(chupito.getNombre(), chupito);
                actualizaListView();
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

    private void actualizaListView(){
        productos.clear();

        Iterator it = mapProductos.keySet().iterator();
        Producto producto;
        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);

            productos.add(String.format(producto.getCantidad() +
                    "x    " + producto.getNombre() +
                    "       " + String.format("%.2f", producto.getCantidad() * producto.getPrecio())));
        }

        adapter.notifyDataSetChanged();
        actualizarTotal();
    }

    private void actualizarTotal(){
        Iterator it = mapProductos.keySet().iterator();
        double total = 0.0;
        Producto producto;

        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);

            total += (producto.getCantidad() * producto.getPrecio());
        }
        lblTotal.setText("TOTAL:  "+ String.format("%.2f", total));
    }

}
