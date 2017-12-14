package com.example.ivan.minibar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Activity2 extends AppCompatActivity {

    private static ArrayList<Producto> productos = new ArrayList<Producto>();;
    private static Map<String,Producto> mapProductos = new LinkedHashMap<>();
    private static ProductosAdapter adapter ;
    private static ListView listaPedidos ;
    private static TextView lblTotal;
    private static Double total;
    private static DBManager gestorDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Log.i("","Iniciamos DB");
        gestorDB = new DBManager( this.getApplicationContext() );

        final Button btPagar = findViewById(R.id.btPagar);
        final ImageButton btRefrescos = findViewById(R.id.btRefresco);
        final ImageButton btCervezas = findViewById(R.id.btCervexa);
        final ImageButton btCafes = findViewById(R.id.btCafe);
        final ImageButton btCombinados = findViewById(R.id.btCombinado);
        final ImageButton btZumos = findViewById(R.id.btZumos);
        final ImageButton btChupitos = findViewById(R.id.btChupitos);
        final Producto refresco = gestorDB.getProducto(1);
        final Producto cerveza = gestorDB.getProducto(2);
        final Producto zumo = gestorDB.getProducto(3);
        final Producto cafe = gestorDB.getProducto(4);
        final Producto combinado = gestorDB.getProducto(5);
        final Producto chupito = gestorDB.getProducto(6);


        lblTotal = (TextView) findViewById(R.id.txtTotal);

        adapter = new ProductosAdapter(this, productos);
        listaPedidos = (ListView) findViewById(R.id.listPedidos);

        listaPedidos.setAdapter(adapter);


        listaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                refresco.eliminar();
            }
        });
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

        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!productos.isEmpty()) {
                    guardarPedido(mapProductos, total);
                    setResult(1);
                    finish();
                }
            }
        });
    }

    private static void actualizaListView(){
        productos.clear();

        Iterator it = mapProductos.keySet().iterator();
        Producto producto;
        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);
            if(producto.getCantidad()>0) {
                productos.add(producto);
            }
        }

        adapter.notifyDataSetChanged();
        actualizarTotal();
    }

    private static void actualizarTotal(){
        Iterator it = mapProductos.keySet().iterator();
        total = 0.0;
        Producto producto;

        while(it.hasNext()){
            String clave = (String) it.next();
            producto = (Producto) mapProductos.get(clave);

            total += (producto.getCantidad() * producto.getPrecio());
        }
        lblTotal.setText("TOTAL:  "+ String.format("%.2f", total)+" €");
    }

    public static void eliminar(Producto p){

        p.eliminar();
        Log.i("Producto:  ",p.getNombre());
        actualizaListView();
    }


    public void guardarPedido(Map<String, Producto> map, Double total){
        gestorDB.insertarTicket(map, total);
        mapProductos.clear();
        productos.clear();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.pedido_menu, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        switch( menuItem.getItemId() ) {
            case R.id.btDeletePedido:
                Iterator it = mapProductos.keySet().iterator();
                Producto producto;

                while(it.hasNext()){
                    String clave = (String) it.next();
                    producto = (Producto) mapProductos.get(clave);
                    producto.reset();
                }
                mapProductos.clear();
                productos.clear();
                actualizaListView();
                toret = true;
                break;
        }
        return toret;
    }
}
