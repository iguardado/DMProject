package com.example.ivan.minibar;

/**
 * Created by Ivan on 28/11/2017.
 */

public class LineaTicket {

    private int numLinea;
    private int numTicket;
    private int unidades;
    private Producto producto;

    public LineaTicket(int numLinea, int numTicket, int unidades, Producto producto) {
        this.numLinea = numLinea;
        this.numTicket = numTicket;
        this.unidades = unidades;
        this.producto = producto;
    }

    public int getNumLinea() {
        return numLinea;
    }

    public void setNumLinea(int numLinea) {
        this.numLinea = numLinea;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
