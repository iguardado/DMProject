package com.example.ivan.minibar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            /*
            db.execSQL("CREATE TABLE TICKET ( " +
                    "numTicket INTEGER NOT NULL AUTO_INCREMENT, " +
                    "importe REAL NOT NULL, " +
                    "iva INTEGER NOT NULL, " +
                    "fecha DATE NOt NULL);");
            db.execSQL("CREATE TABLE LINEA_TICKET (" +
                    "numLinea INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "numTicket INTEGER NOT NULL," +
                    "unidades INTEGER NOT NULL," +
                    "precio REAL NOt NULL," +
                    "primary KEY(numLinea, numTicket));");
                    */
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

    public Producto getProducto(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCTO WHERE ID="+Integer.toString(id) , null);

        if(cursor != null && cursor.moveToFirst()){
            return new Producto(cursor.getString(1), cursor.getDouble(2));
        }
        return new Producto(Integer.toString(id),0.0);


    }
}
