package com.example.miexamenfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miexamenfinal.clases.Pokemon;
import com.example.miexamenfinal.services.PokemonService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pokemon_Detalle extends AppCompatActivity {

        protected  void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detalle_pokemon);



            ImageView imagen = findViewById(R.id.imagenPokemon);
            TextView nombre = findViewById(R.id.nombrePokemon);
            TextView latitud = findViewById(R.id.latitude);
            TextView longitud = findViewById(R.id.longitude);
            Button btnMapa = findViewById(R.id.btnMapa);
            Button btnCapturar=findViewById(R.id.btnCapturar);



            Intent intent = getIntent();
            String clase = intent.getStringExtra("Class");
            Pokemon pokemon = new Gson().fromJson(clase,Pokemon.class);
            String latitude = String.valueOf(pokemon.getLatitude());
            String longitude = String.valueOf(pokemon.getLongitude());
            nombre.setText(pokemon.getNombre());
            latitud.setText(latitude);
            longitud.setText(longitude);

            Picasso.get().load( "https://upn.lumenes.tk/" + pokemon.getUrl_imagen()).into(imagen);

            btnMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Pokemon_Detalle.this,MapsActivity.class);
                    startActivity(intent);
                }
            });
            btnCapturar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Pokemon_Detalle.this,capturar.class);
                    startActivity(intent);
                }
            });
        }


}
