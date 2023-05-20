package com.example.chatfirebase.Models;

public class Mensaje {
    private String mensaje;
    private String imageUrl;
    private String nombre;
    private String fotoPerfil;
    private String tipoMensaje;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String nombre, String fotoPerfil, String tipoMensaje) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.tipoMensaje = tipoMensaje;
    }

    public Mensaje(String mensaje, String imageUrl, String nombre, String fotoPerfil, String tipoMensaje) {
        this.mensaje = mensaje;
        this.imageUrl = imageUrl;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
