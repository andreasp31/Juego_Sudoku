package com.example.myapplication;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/puntuacion")
    Call<Puntuacion> guardarPuntuacion(@Body Puntuacion puntuacion);

    @GET("api/ranking/{dificultad}")
    Call<ArrayList<Puntuacion>> obtenerRanking(@Path("dificultad")String dificultad);
}
