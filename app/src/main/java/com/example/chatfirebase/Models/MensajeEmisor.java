package com.example.chatfirebase.Models;

import java.util.Map;

public class MensajeEmisor extends Mensaje{
    private Map hora;

    public MensajeEmisor() {
    }

    public MensajeEmisor(Map hora) {
        this.hora = hora;
    }

    public MensajeEmisor(String mensaje, String nombre, String fotoPerfil, String tipoMensaje, Map hora) {
        super(mensaje, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public MensajeEmisor(String mensaje, String imageUrl, String nombre, String fotoPerfil, String tipoMensaje, Map hora) {
        super(mensaje, imageUrl, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
