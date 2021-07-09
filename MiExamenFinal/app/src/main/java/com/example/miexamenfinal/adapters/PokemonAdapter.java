package com.example.miexamenfinal.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.miexamenfinal.R;
import com.example.miexamenfinal.clases.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>
        implements View.OnClickListener {

    List<Pokemon> pokemones;
    private View.OnClickListener listener;

    public PokemonAdapter(List<Pokemon> pokemones) {
        this.pokemones = pokemones;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        v.setOnClickListener(this);
        PokemonAdapter.PokemonViewHolder viewHolder = new PokemonAdapter.PokemonViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull PokemonAdapter.PokemonViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.nombre);
        TextView tipe = holder.itemView.findViewById(R.id.tipo);
        ImageView image = holder.itemView.findViewById(R.id.imagePokemon);



        String nombre = String.valueOf(pokemones.get(position).getNombre());
        String tipo = String.valueOf(pokemones.get(position).getTipo());
        String imagen = String.valueOf(pokemones.get(position).getUrl_imagen());

        name.setText(nombre);
        tipe.setText(tipo);
        Picasso.get().load("https://upn.lumenes.tk"+imagen).into(image);


    }



    @Override
    public int getItemCount() {
        return pokemones.size();
    }

    public void OnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
