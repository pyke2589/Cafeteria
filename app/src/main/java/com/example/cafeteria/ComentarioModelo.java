package com.example.cafeteria;

public class ComentarioModelo {
    private String autor;
    private String texto;

    public ComentarioModelo() {} // Requerido por Firebase

    public ComentarioModelo(String autor, String texto) {
        this.autor = autor;
        this.texto = texto;
    }

    public String getAutor() { return autor; }
    public String getTexto() { return texto; }
}