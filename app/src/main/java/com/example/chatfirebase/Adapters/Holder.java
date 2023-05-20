package com.example.chatfirebase.Adapters;

import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatfirebase.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Holder extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private CircleImageView foto;
    private ImageView imageMessage;

    public Holder(@NonNull @androidx.annotation.NonNull View itemView) {
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.nombreOtherUser);
        mensaje = (TextView) itemView.findViewById(R.id.message);
        hora = (TextView) itemView.findViewById(R.id.horaSendMensaje);
        foto = (CircleImageView) itemView.findViewById(R.id.fotoPerfilOtherUser);
        imageMessage = (ImageView) itemView.findViewById(R.id.mensajeTipo2);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFoto() {
        return foto;
    }

    public void setFoto(CircleImageView foto) {
        this.foto = foto;
    }

    public ImageView getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(ImageView imageMessage) {
        this.imageMessage = imageMessage;
    }
}
