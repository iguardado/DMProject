package com.example.ivan.minibar;

/**
 * Created by ivan on 11/18/17.
 */

public class Producto {

    private int id;
    private int cantidad;
    private String nombre;
    private double precio;

    public Producto(int id,String nombre, double precio){
        this.id = id;
        this.cantidad = 0;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void añadir() {
        this.cantidad++;
    }

    public void eliminar() {
        if(this.cantidad>0) {
            this.cantidad--;
        }
    }
    public void reset(){
        this.cantidad = 0;
    }

    public int getId(){
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
