package com.example.cafeteria;

public class CafeteriaModelo {
    private String id; // ¡NUEVO! El identificador único de Firebase
    private String nombre;
    private String direccion;
    private String categoria;
    private double latitud;
    private double longitud;
    private int total_likes;

    public CafeteriaModelo() {}

    // Getters y Setters para el ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Getters normales
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getCategoria() { return categoria; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public int getTotal_likes() { return total_likes; }
}