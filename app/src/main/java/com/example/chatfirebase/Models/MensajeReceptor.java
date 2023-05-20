package com.example.chatfirebase.Models;

public class MensajeReceptor extends Mensaje{
    private Long hora;

    public MensajeReceptor() {
    }

    public MensajeReceptor(Long hora) {
        this.hora = hora;
    }

    public MensajeReceptor(String mensaje, String imageUrl, String nombre, String fotoPerfil, String tipoMensaje, Long hora) {
        super(mensaje, imageUrl, nombre, fotoPerfil, tipoMensaje);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
