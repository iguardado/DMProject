package com.example.ivan.minibar;

import java.util.ArrayList;

/**
 * Created by Ivan on 28/11/2017.
 */

public class Ticket {

    private int numTicket;
    private double total;
    private String fechaTicket;
    private int ivaTicket;
    private ArrayList<LineaTicket> lineasTicket;

    public Ticket(int numTicket, double total, String fechaTicket, int ivaTicket, ArrayList<LineaTicket> lineasTicket) {
        this.numTicket = numTicket;
        this.total = total;
        this.fechaTicket = fechaTicket;
        this.ivaTicket = ivaTicket;
        this.lineasTicket = lineasTicket;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(String fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public int getIvaTicket() {
        return ivaTicket;
    }

    public void setIvaTicket(int ivaTicket) {
        this.ivaTicket = ivaTicket;
    }

    public ArrayList<LineaTicket> getLineasTicket() {
        return lineasTicket;
    }

    public void setLineasTicket(ArrayList<LineaTicket> lineasTicket) {
        this.lineasTicket = lineasTicket;
    }
}
