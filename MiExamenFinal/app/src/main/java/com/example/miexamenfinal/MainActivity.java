package com.example.miexamenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miexamenfinal.adapters.MaestroAdapter;
import com.example.miexamenfinal.clases.Maestro;
import com.example.miexamenfinal.services.MaestroService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imagen = findViewById(R.id.ivMaestro);
        TextView nombre = findViewById(R.id.tvNombre);
        TextView pueblo = findViewById(R.id.tvPueblo);
        Button registrarMaestro=findViewById(R.id.btnCrearMaestro);
        Button registrarPokemon=findViewById(R.id.btnCrearPokemon);
        Button listamos=findViewById(R.id.btnlistar);

        registrarMaestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,nuevoMaestro.class);
                startActivity(intent);
            }
        });
        registrarPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,nuevoPokemon.class);
                startActivity(intent);
            }
        });
        listamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,List_pokemons.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MaestroService service = retrofit.create(MaestroService.class);
        Call<Maestro> entrenador2 = service.getEntrenador();

        entrenador2.enqueue(new Callback<Maestro>() {
            @Override
            public void onResponse(Call<Maestro> call, Response<Maestro> response) {
                if(response.code() == 200){
                    Maestro maestro  = response.body();
                    nombre.setText(maestro.getNombres());
                    pueblo.setText(maestro.getPueblo());

                    Picasso.get().load( maestro.getImagen()).into(imagen);

                }else {
                    Log.i("Internet","No existe");
                }
            }

            @Override
            public void onFailure(Call<Maestro> call, Throwable t) {
                Log.i("Servidor","No existe");
            }
        });

    }
}