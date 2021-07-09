package com.example.miexamenfinal.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miexamenfinal.R;
import com.example.miexamenfinal.clases.Maestro;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MaestroAdapter extends RecyclerView.Adapter<MaestroAdapter.MaestroViewHolder>
implements View.OnClickListener{
    private View.OnClickListener listener;

    Maestro mData;


    public MaestroAdapter(Maestro mData) {
        this.mData = mData;
    }

    @Override
    public MaestroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main,parent,false);
        view.setOnClickListener(this);
        MaestroAdapter.MaestroViewHolder viewHolder = new MaestroAdapter.MaestroViewHolder(view);
        return new MaestroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MaestroAdapter.MaestroViewHolder holder, int position) {
        //View view = holder.itemView;

        TextView pkNombre = holder.itemView.findViewById(R.id.tvNombre);
        TextView pkPueblo= holder.itemView.findViewById(R.id.tvPueblo);
        ImageView ig = holder.itemView.findViewById(R.id.ivMaestro);
        String nombres = String.valueOf(mData.getNombres());
        String pueblo = String.valueOf(mData.getPueblo());
        String imagen = String.valueOf(mData.getImagen());
        pkNombre.setText(nombres);
        pkPueblo.setText(pueblo);
        Picasso.get().load(imagen).into(ig);


    }

    @Override
    public int getItemCount() {
        return 0;
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

    public class MaestroViewHolder extends RecyclerView.ViewHolder {
        public MaestroViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}