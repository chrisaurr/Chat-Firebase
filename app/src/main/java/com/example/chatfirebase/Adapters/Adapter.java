package com.example.chatfirebase.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatfirebase.Models.MensajeReceptor;
import com.example.chatfirebase.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder> {

    private List<MensajeReceptor> lista = new ArrayList<>();
    private Context c;

    public Adapter(Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeReceptor mensaje){
        lista.add(mensaje);
        notifyItemInserted(lista.size());
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull Holder holder, int i) {

            holder.getNombre().setText(lista.get(i).getNombre());
            holder.getMensaje().setText(lista.get(i).getMensaje());
            //holder.getHora().setText(lista.get(i).getHora());

            if(lista.get(i).getTipoMensaje().equals("2")){
                holder.getImageMessage().setVisibility(View.VISIBLE);
                holder.getMensaje().setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(lista.get(i).getImageUrl())
                        .into(holder.getImageMessage());

            }else if(lista.get(i).getTipoMensaje().equals("1")){
                holder.getImageMessage().setVisibility(View.GONE);
                holder.getMensaje().setVisibility(View.VISIBLE);
            }else if(lista.get(i).getFotoPerfil().isEmpty()){
                holder.getFoto().setImageResource(R.mipmap.ic_launcher);
            }else{
                Picasso.get()
                        .load(lista.get(i).getFotoPerfil())
                        .into(holder.getFoto());
            }

            Long codeTime = lista.get(i).getHora();
            Date date = new Date(codeTime);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            holder.getHora().setText(sdf.format(date));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
