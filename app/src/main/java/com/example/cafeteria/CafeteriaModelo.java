package com.example.cafeteria;

public class CafeteriaModelo {
    private String id;
    private String nombre;
    private String direccion;
    private String categoria;
    private double latitud;
    private double longitud;
    private int total_likes;

    private String imagen;
    private String horario;
    private String marcas;
    private String tipo;
    private String descripcion; // ¡NUEVO CAMPO!

    public CafeteriaModelo() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getCategoria() { return categoria; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public int getTotal_likes() { return total_likes; }
    public String getImagen() { return imagen; }
    public String getHorario() { return horario; }
    public String getMarcas() { return marcas; }
    public String getTipo() { return tipo; }

    // Getters y Setters de la descripción
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}