package com.example.miexamenfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miexamenfinal.adapters.PokemonAdapter;
import com.example.miexamenfinal.clases.Pokemon;
import com.example.miexamenfinal.services.PokemonService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class List_pokemons extends AppCompatActivity {
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.lista_pokemones);
            RecyclerView rv = findViewById(R.id.rvPokemons);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(this));

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://upn.lumenes.tk/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PokemonService service = retrofit.create(PokemonService.class);
            Call<List<Pokemon>> pokemon = service.getAll();


            pokemon.enqueue(new Callback<List<Pokemon>>() {
                @Override
                public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                    if (response.code() == 200) {
                        List<Pokemon> pokemones = response.body();
                        PokemonAdapter adapter = new PokemonAdapter(pokemones);
                        rv.setAdapter(adapter);
                        adapter.OnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Seleccionaste a: " + pokemones
                                                .get(rv.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(List_pokemons.this, Pokemon_Detalle.class);
                                String clase = new Gson().toJson(pokemones.get(rv.getChildAdapterPosition(v)));
                                intent.putExtra("Class", clase);
                                startActivity(intent);
                            }
                        });
                        rv.setAdapter(adapter);
                    } else {
                        Log.i("Internet", "No hay conexion");
                    }
                }
                @Override
                public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                    Log.i("Internet","No hay conexion con el servidor");
                }
            });



        }
}

