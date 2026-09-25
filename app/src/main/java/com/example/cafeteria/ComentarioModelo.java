package com.example.cafeteria;

public class ComentarioModelo {
    private String idComentario;
    private String idUsuario;
    private String idCafeteria;
    private String autor;
    private String texto;

    public ComentarioModelo() {} // Firestore necesita esto vacío

    public ComentarioModelo(String autor, String texto, String idUsuario, String idCafeteria) {
        this.autor = autor;
        this.texto = texto;
        this.idUsuario = idUsuario;
        this.idCafeteria = idCafeteria;
    }

    public String getIdComentario() { return idComentario; }
    public void setIdComentario(String idComentario) { this.idComentario = idComentario; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getIdCafeteria() { return idCafeteria; }
    public void setIdCafeteria(String idCafeteria) { this.idCafeteria = idCafeteria; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}