package org.example.models;

public class Ticket {
    private int id ;
    private int Cantidad;
    private String Descripcion;
    //Precio (producto)
    private double total;
    //ventasid (ventas)

    public Ticket(){
    };

    public int getId(){
        return this.id;
    }

    public void SetId(int id){
        this.id= id;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
    public String getDescripcion(){
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
