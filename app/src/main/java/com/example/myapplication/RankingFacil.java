package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingFacil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdaptadorRankingF adaptador;
    private ArrayList<Puntuacion> listaPuntuacion;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ranking_facil);

        recyclerView = findViewById(R.id.grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaPuntuacion = new ArrayList<>();

        adaptador = new AdaptadorRankingF(listaPuntuacion);
        recyclerView.setAdapter(adaptador);

        apiService = RetrofitUsuario.getApiService();

        cargarRanking();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cargarRanking(){
        Call<ArrayList<Puntuacion>> llamar = apiService.obtenerRanking("facil");
       llamar.enqueue(new Callback<ArrayList<Puntuacion>>() {
           @Override
           public void onResponse(Call<ArrayList<Puntuacion>> call, Response<ArrayList<Puntuacion>> response) {
               if(response.isSuccessful()){
                   ArrayList<Puntuacion> ranking = response.body();
                   listaPuntuacion.clear();
                   listaPuntuacion.addAll(ranking);
                   adaptador.notifyDataSetChanged();
               }
               else {
                   listaPuntuacion.clear();
                   adaptador.notifyDataSetChanged();
               }
           }
           @Override
           public void onFailure(Call<ArrayList<Puntuacion>> call, Throwable t) {
               listaPuntuacion.clear();
               adaptador.notifyDataSetChanged();
           }
       });
    }
    public void volverMenu(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}