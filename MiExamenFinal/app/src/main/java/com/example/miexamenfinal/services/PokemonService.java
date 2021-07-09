package com.example.miexamenfinal.services;


import com.example.miexamenfinal.clases.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("pokemons/N00149730")
    Call<List<Pokemon>> getAll();

    @POST("pokemons/N00149730/crear")
    Call<Pokemon> create(@Body Pokemon pokemon);

    @GET("pokemones/N00149730")
    Call <Pokemon> getAllById(@Query("id")Integer id);
}
