package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorRankingF extends RecyclerView.Adapter<AdaptadorRankingF.PuntuacionViewHolder> {

    private ArrayList<Puntuacion> listaPuntuacion;

    //Comunica con la activity cuando vaya a interacturar con los botones de cambiar la cantidad
    public ImageView botonMas, botonMenos;

    //Interfaz del listener
    public interface  cantidadProducto{
        void cantidadProducto (int posicion, int cantidad);
    }

    //Metodo para asignar el listener
    public AdaptadorRankingF(ArrayList<Puntuacion> listaPuntuacion){
        this.listaPuntuacion=listaPuntuacion;
    }

    //Creamos los moldes de cada elemento
    @NonNull
    @Override
    public PuntuacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adaptador_ranking_f,parent,false);
        return new PuntuacionViewHolder(view);
    }

    //Para encargarse de llenar los datos
    @Override
    public void onBindViewHolder (@NonNull PuntuacionViewHolder holder, int posicion){
        Puntuacion puntuacion = listaPuntuacion.get(posicion);
        holder.bind(puntuacion);
    }

    @Override
    //Para que el recycled view sepa cuantas puntuaciones hay
    public int getItemCount(){
        return listaPuntuacion.size();
    }

    public class PuntuacionViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, tiempo, dificultad;

        public PuntuacionViewHolder(@NonNull View vistaPuntuacion) {
            super(vistaPuntuacion);
            //Relacionamos cada variable con cada elemento visual
            nombre = vistaPuntuacion.findViewById(R.id.textNombre);
            tiempo = vistaPuntuacion.findViewById(R.id.tiempoResuelto);
        }
        public void bind(Puntuacion puntuacion) {
            nombre.setText(puntuacion.getNombre());
            tiempo.setText(puntuacion.getTiempo());
        }
    }
}