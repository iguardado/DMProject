package com.example.ivan.minibar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ivan on 11/21/17.
 */

public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NOMBRE = "minibar";
    public static final int DB_VERSION = 2;

    public static final String TABLA_COMPRA = "compra";
    public static final String COMPRA_COL_NOMBRE = "_id";
    public static final String COMPRA_COL_NUM = "num";

    public DBManager(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager",
                "Creando BBDD " + DB_NOMBRE + " v" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE PRODUCTO (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre varchar(25) NOT NULL," +
                    "precio REAL NOT NULL);");

            db.execSQL("CREATE TABLE TICKET ( " +
                    "numTicket INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "importe REAL NOT NULL, " +
                    "iva INTEGER NOT NULL, " +
                    "fecha DATETIME NOt NULL);");

            db.execSQL("CREATE TABLE LINEA_TICKET (" +
                    "numLinea INTEGER NOT NULL," +
                    "numTicket INTEGER NOT NULL," +
                    "unidades INTEGER NOT NULL," +
                    "producto INTEGER NOT NULL," +
                    "primary KEY(numLinea, numTicket), " +
                    "FOREIGN KEY (producto) REFERENCES PRODUCTO(ID)," +
                    "FOREIGN KEY (numTicket) REFERENCES TICKET(numTicket));");

            db.execSQL( "INSERT INTO PRODUCTO(nombre,precio) values"+
                        "('Refresco',1.7),"+
                        "('Cerveza',1.7),"+
                        "('Zumo',2.0),"+
                        "('Café',1.0),"+
                        "('Combinado',3.5),"+
                        "('Chupito',1.5);");
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onCreate", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager",
                "DB: " + DB_NOMBRE + ": v" + oldVersion + " -> v" + newVersion);

        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_COMPRA);
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onUpgrade", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        this.onCreate(db);
    }

    public Producto getProducto(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCTO WHERE ID="+Integer.toString(id) , null);

        if(cursor != null && cursor.moveToFirst()){
            return new Producto(cursor.getInt(0),cursor.getString(1), cursor.getDouble(2));
        }
        return new Producto(id,Integer.toString(id),0.0);

    }

    public String getTicket(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TICKET ", null);

        db.beginTransaction();
        if(cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0) + " " + cursor.getDouble(1) + ", IVA: " + cursor.getInt(2) + ", " + cursor.getString(3);
        }
        db.setTransactionSuccessful();
        return "";

    }

    public ArrayList<Ticket> getTickets(){
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TICKET ", null);

        db.beginTransaction();
        if(cursor != null && cursor.moveToFirst())
        {

            do{


                tickets.add( new Ticket(cursor.getInt(0), cursor.getDouble(1),
                        cursor.getString(3), cursor.getInt(2),
                        new ArrayList<LineaTicket>()) );

            }while(cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return tickets;

    }
    public ArrayList<LineaTicket> getLineasTicket(int numticket){
        ArrayList<LineaTicket> lineasticket = new ArrayList<LineaTicket>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LINEA_TICKET WHERE numTicket="+Integer.toString(numticket)+";", null);

        db.beginTransaction();
        if(cursor != null && cursor.moveToFirst())
        {
            do{
                Cursor cursor2 = db.rawQuery("SELECT * FROM PRODUCTO WHERE ID="+Integer.toString(cursor.getInt(3))+";", null);

                lineasticket.add( new LineaTicket(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        new Producto(cursor2.getInt(0), cursor2.getString(1), cursor2.getDouble(2)) ) );
            }while(cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return lineasticket;

    }

    public void insertarTicket(Map<String,Producto> map, Double total){
        SQLiteDatabase db1 = this.getReadableDatabase();
        Iterator it = map.keySet().iterator();
        int numTicket = 0;
        int numLinea = 1;
        db1.beginTransaction();
        Cursor cursor = db1.rawQuery("SELECT COUNT(*) FROM TICKET ", null);

        if(cursor != null && cursor.moveToFirst()){
            numTicket = cursor.getInt(0)+1;
        }
        db1.setTransactionSuccessful();
        db1.endTransaction();

        SQLiteDatabase db2 = this.getWritableDatabase();

        db2.beginTransaction();

        String sqlQuery = "INSERT INTO TICKET(importe,iva,fecha) values";
        sqlQuery += "(" + String.format("%.2f", total) + ", 21, datetime() );";

        db2.execSQL(sqlQuery);

        sqlQuery = "INSERT INTO LINEA_TICKET(numLinea,numTicket,unidades,producto) values";
        while(it.hasNext()){
            String clave = (String) it.next();
            Producto producto = (Producto) map.get(clave);
            sqlQuery += "(" + String.format("%d", numLinea) +
                    "," + String.format("%d", numTicket) +
                    "," + producto.getCantidad() +
                    "," + String.format("%d",producto.getId())+")";
            numLinea++;
            if(it.hasNext()){
                sqlQuery +=",";
            }
        }
        sqlQuery += ";";

        db2.execSQL(sqlQuery);

        db2.setTransactionSuccessful();
        db2.endTransaction();

    }
}
