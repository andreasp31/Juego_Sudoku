package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SudokuDificil extends AppCompatActivity {
    private GridView gridView;
    private GenerarSudoku generar;
    private AdaptadorSudokuDificil adaptador;
    private int[][] tableroActual;
    private int[][] tableroOriginal;
    private int[][] tableroSolucion;
    private boolean[][] celdasIncorrectas;

    //Para cuardar la celda seleccionada
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sudoku_dificil);
        gridView = findViewById(R.id.tablero);

        Button botonN1 = findViewById(R.id.botonN1);
        Button botonN2 = findViewById(R.id.botonN2);
        Button botonN3= findViewById(R.id.botonN3);
        Button botonN4= findViewById(R.id.botonN4);
        Button botonN5 = findViewById(R.id.botonN5);
        Button botonN6 = findViewById(R.id.botonN6);
        Button botonN7 = findViewById(R.id.botonN7);
        Button botonN8 = findViewById(R.id.botonN8);
        Button botonN9 = findViewById(R.id.botonN9);


        generar = new GenerarSudoku();
        generarPartida();

        gridView.setOnItemClickListener((parent,view,position,id)->{
            filaSeleccionada= position/9;
            columnaSeleccionada= position % 9;
            if(tableroOriginal[filaSeleccionada][columnaSeleccionada] == 0){
                adaptador.celdaSeleccionada(filaSeleccionada,columnaSeleccionada);
            }
            else{
                filaSeleccionada = -1;
                columnaSeleccionada= -1;
                adaptador.celdaSeleccionada(-1,-1);
            }
        });

        botonN1.setOnClickListener(view -> ponerNumero(1));
        botonN2.setOnClickListener(view -> ponerNumero(2));
        botonN3.setOnClickListener(view -> ponerNumero(3));
        botonN4.setOnClickListener(view -> ponerNumero(4));
        botonN5.setOnClickListener(view -> ponerNumero(5));
        botonN6.setOnClickListener(view -> ponerNumero(6));
        botonN7.setOnClickListener(view -> ponerNumero(7));
        botonN8.setOnClickListener(view -> ponerNumero(8));
        botonN9.setOnClickListener(view -> ponerNumero(9));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void generarPartida(){
        generar.generarSudoku();
        tableroActual = copiarTablero(generar.getTablero());
        tableroOriginal = copiarTablero(generar.getTablero());
        tableroSolucion = copiarTablero(generar.getResultado());
        celdasIncorrectas = new boolean[9][9];

        filaSeleccionada = -1;
        columnaSeleccionada = -1;

        if(adaptador == null){
            adaptador = new AdaptadorSudokuDificil(this, tableroActual, tableroOriginal,celdasIncorrectas);
            gridView.setAdapter(adaptador);
        }
        else{
            adaptador.actualizarTablero(tableroActual,celdasIncorrectas);
        }
    }
    private void validarTablero() {
        // Reiniciar validación
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                celdasIncorrectas[i][j] = false;
            }
        }

        // Validar solo celdas modificadas por usuario
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tableroOriginal[i][j] == 0 && tableroActual[i][j] != 0) {
                    if (tableroActual[i][j] != tableroSolucion[i][j]) {
                        celdasIncorrectas[i][j] = true;
                    }
                }
            }
        }

        // Actualizar adapter con los errores
        adaptador.actualizarTablero(tableroActual, celdasIncorrectas);
    }

    public void ponerNumero(int numero){
        if (filaSeleccionada != -1 && columnaSeleccionada !=-1 && tableroOriginal[filaSeleccionada][columnaSeleccionada] == 0){
            tableroActual[filaSeleccionada][columnaSeleccionada] = numero;
            validarTablero();
            adaptador.actualizarTablero(tableroActual,celdasIncorrectas);
            adaptador.celdaSeleccionada(filaSeleccionada, columnaSeleccionada);
        }
    }

    public int[][] copiarTablero(int[][] original ){
        int[][] copia = new int[9][9];
        for(int i = 0; i<9; i++){
            for (int j = 0; j < 9; j++) {
                copia[i][j] = original[i][j];
            }
        }
        return copia;
    }
    public void salirSudoku(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Abandonar")
                .setMessage("¿Seguro que quiere salir del juego?" )
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    //funcion para comprobar el tablero
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }).setNegativeButton("Cancelar", null).show();
    }
    public void comprobarTablero(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comprobar tablero")
                .setMessage("¿Seguro que quiere salir del juego?" )
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    //funcion para comprobar el tablero
                    if(tableroCompletoCorrecto()){
                        tableroResulto();
                    }
                    else{
                        tableroIncompleto();
                    }
                }).setNegativeButton("Cancelar", null).show();
    }

    public boolean tableroCompletoCorrecto (){
        for (int i=0; i<9;i++){
            for (int j=0; j<9; j++){
                if(tableroActual[i][j] != tableroSolucion[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public void tableroResulto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tablero completado correctamente")
                .setMessage("Comprueba tu puntuación" )
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    //funcion para comprobar el tablero

                    System.exit(0);
                }).show();
    }
    public void tableroIncompleto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tablero mal resulto")
                .setMessage("No has completado correctamente el tablero" )
                .setPositiveButton("Continuar", (dialog, which) -> {
                    //funcion para comprobar el tablero

                    System.exit(0);
                }).show();
    }
}