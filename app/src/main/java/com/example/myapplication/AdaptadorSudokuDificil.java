package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

//Va a coger los números y los convierte en celdas visibles en la vista
public class AdaptadorSudokuDificil extends BaseAdapter {
    //Donde va a mostrar las celtas
    private Context context;
    //Los números que va a poner el jugador
    private int[][] tablero;
    //El tablero que tiene  los números fijos
    private int[][] tableroOriginal;
    private boolean[][] celdasIncorrectas;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;



    public AdaptadorSudokuDificil(Context context, int[][] tablero, int[][]tableroOriginal, boolean[][] celdasIncorrectas){
        this.context = context;
        this.tablero = tablero;
        this.tableroOriginal = tableroOriginal;
        this.celdasIncorrectas = celdasIncorrectas;
    }

    @Override
    public int getCount(){
        //Hay que enseñar81 casillas
        return 81;
    }

    @Override
    public Object getItem(int posicion){
        return  null;
    }

    @Override
    public long getItemId(int posicion){
        return posicion;
    }

    @Override
    public View getView(int posicion, View vistaExistente, ViewGroup parent){
        TextView vistaCelda;
        if(vistaExistente == null){
            //Crear celda
            vistaCelda = new TextView(context);
            //Tamaño de la celda
            vistaCelda.setLayoutParams(new ViewGroup.LayoutParams(110,110));
            //Espacio
            vistaCelda.setPadding(8, 8, 8, 8);
            //Texto centrado (tuve que usar gravity porque el alineado básico no me funcionaba bien)
            vistaCelda.setGravity(Gravity.CENTER);
            //Tamaño texto
            vistaCelda.setTextSize(20);
        }
        else{
            vistaCelda = (TextView)  vistaExistente;
        }
       int fila = posicion/9;
        int columna = posicion % 9;
        int valorCelda = tablero[fila][columna];

        boolean celdaSeleccionadaSi = (fila == filaSeleccionada && columna == columnaSeleccionada);

        if(celdaSeleccionadaSi){
            vistaCelda.setBackgroundResource(R.drawable.estilo_bordes_seleccionado);
        }
        else{
            vistaCelda.setBackgroundResource(R.drawable.estilo_bordes);
        }

        if(valorCelda !=0){
            vistaCelda.setText(String.valueOf(valorCelda));
            if (tableroOriginal[fila][columna] != 0) {
                // Números que no se pueden editar
                vistaCelda.setTextColor(Color.DKGRAY);         // Texto negro
                vistaCelda.setTypeface(null, android.graphics.Typeface.BOLD); // Negrita
                if(celdaSeleccionadaSi){
                    vistaCelda.setBackgroundResource(R.drawable.estilo_bordes_seleccionado);
                }
            }
            else {
                if(celdasIncorrectas != null && celdasIncorrectas[fila][columna]){
                    vistaCelda.setTextColor(ContextCompat.getColor(context,R.color.azul));
                }
                else{
                    // NÚMERO DEL USUARIO (azul) - Se puede cambiar
                    vistaCelda.setTextColor(ContextCompat.getColor(context,R.color.azul));          // Texto azul
                }
                if (celdaSeleccionadaSi) {
                    vistaCelda.setBackgroundResource(R.drawable.estilo_bordes_seleccionado);
                }
            }
        }
        else{
            vistaCelda.setText("");

        }
        return vistaCelda;
    }

    public void celdaSeleccionada(int fila, int columna){
        this.filaSeleccionada = fila;
        this.columnaSeleccionada = columna;
        notifyDataSetChanged();
    }

    public void actualizarTablero(int[][] nuevoTablero,boolean[][] errores){
        this.tablero = nuevoTablero;
        this.celdasIncorrectas = errores;
        notifyDataSetChanged();
    }
}
