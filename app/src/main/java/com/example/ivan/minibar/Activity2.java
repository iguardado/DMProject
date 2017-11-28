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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Activity2 extends Activity {

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
                AlertDialog.Builder builder = new AlertDialog.Builder( Activity2.this );
                builder.setTitle( "Pagar" );
                builder.setMessage("¿Desea descargar el ticket del pedido?");
                builder.setNegativeButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        descargarTicket();
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        guardarPedido(mapProductos, total);
                        finish();
                    }
                });
                builder.create().show();
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
        lblTotal.setText("TOTAL:  "+ String.format("%.2f", total));
    }

    public static void eliminar(Producto p){

        p.eliminar();
        Log.i("Producto:  ",p.getNombre());
        actualizaListView();
    }


    public void guardarPedido(Map<String, Producto> map, Double total){
        gestorDB.insertarTicket(map, total);
    }

    public void descargarTicket(){
        // open a new document
        PrintAttributes printAttributes = new PrintAttributes.Builder().
                setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME).
                setMediaSize(PrintAttributes.MediaSize.NA_LETTER.asLandscape()).
                setResolution(new PrintAttributes.Resolution("zooey", PRINT_SERVICE, 300, 300)).
                setMinMargins(PrintAttributes.Margins.NO_MARGINS).
                build();
        PrintedPdfDocument document = new PrintedPdfDocument(this,
                printAttributes);

        PdfDocument.Page page = document.startPage(0);

        View content = this.findViewById(android.R.id.content);
        content.draw(page.getCanvas());


        document.finishPage(page);

        //document.secontent.getContentDescription());

        document.close();
    }
}
