package com.example.miexamenfinal.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.miexamenfinal.clases.Maestro;

import java.util.List;

public interface MaestroService {

    @GET("entrenador/N00149730")
    Call<Maestro> getEntrenador();

    @POST("entrenador/N00149730")
    Call<Maestro> create(@Body Maestro maestro);
}
