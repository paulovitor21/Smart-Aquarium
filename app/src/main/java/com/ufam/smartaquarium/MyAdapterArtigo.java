package com.ufam.smartaquarium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterArtigo extends RecyclerView.Adapter<MyAdapterArtigo.MyViewHolder> {

    Context context;
    ArrayList<Artigo> artigoArrayList;

    public MyAdapterArtigo(Context context, ArrayList<Artigo> artigoArrayList) {
        this.context = context;
        this.artigoArrayList = artigoArrayList;
    }

    @NonNull
    @Override
    public MyAdapterArtigo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterArtigo.MyViewHolder holder, int position) {
        Artigo artigo = artigoArrayList.get(position);

        holder.titulo.setText(artigo.titulo);
        holder.descricao.setText(artigo.descricao);

    }

    @Override
    public int getItemCount() {
        return artigoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView descricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTitulo);
            descricao = itemView.findViewById(R.id.tvDescricao);
        }
    }
}
