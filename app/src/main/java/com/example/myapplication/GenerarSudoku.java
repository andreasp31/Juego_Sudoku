package com.example.myapplication;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenerarSudoku {
      private  int[][] tablero;
      private int[][] resultado;
      private Random random;

      public GenerarSudoku(){
          tablero = new int[9][9];
          resultado = new int[9][9];
          random = new Random();
      }

      public void limpiarTablero(){
          for(int i=0; i<9; i++){
              for(int j=0; j<9; j++){
                  tablero [i][j]= 0;
                  resultado [i][j]= 0;
              }
          }
      }
      public void rellenarCaja(int fila, int columna){
          ArrayList<Integer> numeros = new ArrayList<>();
          for(int i=1; i<=9; i++){
              numeros.add(i);
          }
          //Para mezclar más fácil
          Collections.shuffle(numeros);

          int indice = 0;
          for(int i=0; i<3;i++){
              for(int j=0; j<3;j++){
                  //por cada posición en el tablero según la fila y columna colocamos el número que está en la lista según la posición en la que está
                  tablero[fila+i][columna+j] = numeros.get(indice++);
                  //Y esto lo igualamos al resultado porque van a ser si o si esos números
                  resultado[fila+i][columna+j]= tablero[fila+i][columna+j];
              }
          }
      }

      //Vamos a rellenar 3 cajas diagonales porque no crea conflicto para resolver
      public void rellenar3Cajas(){
          rellenarCaja(0,0);
          rellenarCaja(3,3);
          rellenarCaja(6,6);
      }

      public void generarSudoku(){
          limpiarTablero();
          rellenar3Cajas();
          resolverSudoku();
          crearSudoku();
      }

      //algoritmo de backtracking
      public boolean resolverSudoku(){
          //filas
            for(int i=0 ; i<9; i++){
                //columnas
                for(int j=0; j<9; j++){
                    //si hay un hueoc
                    if (tablero[i][j] ==0){
                        for(int numero = 1; numero<=9; numero ++){
                            if(esCorrecto(i,j,numero)){
                                //Si es correcto el número que introduzco se queda puesto en el tablero y queda guardado en la copia del resultado
                                tablero[i][j] = numero;

                                if(resolverSudoku()){
                                    return true;
                                }

                                //Si no pues queda en 0
                                tablero[i][j] = 0;
                            }

                        }
                        return false;
                    }
                }
            }
            for(int i=0; i<9; i++){
                for(int j=0; j<9;j++){
                    resultado[i][j] = tablero[i][j];
                }
            }
            return true;
      }

      public boolean esCorrecto(int fila,int columna,int numero){
          //Verificar fila
          for(int i=0; i<9; i++){
              if(tablero[fila][i] == numero ){
                  return false;
              }
          }
          //Verificar columna
          for(int i=0; i<9; i++){
              if(tablero[i][columna] == numero ){
                  return false;
              }
          }
          //Verificar cajas, a partir de la fila y la columna sacamos en que caja estamos
          int inicioFila = fila - fila % 3;
          int inicioColumna = columna - columna % 3;
          for (int i=0; i<3;i++){
              for (int j=0;j<3;j++){
                  if(tablero[i+inicioFila][j+inicioColumna] == numero){
                      return false;
                  }
              }
          }
          return true;
      }

      //Borrar numeros para que empieza el juego
      public void crearSudoku(){
          int borrarCasillas = 50;
          for(int i=0; i<borrarCasillas; i++){
              while(true){
                  int fila = random.nextInt(9);
                  int columna = random.nextInt(9);

                  if(tablero[fila][columna] !=0){
                      tablero[fila][columna] = 0;
                      break;
                  }
              }
          }
      }

      public boolean solucionSudoku(int[][] miSudoku){
          for(int i=0; i<9; i++){
              for(int j=0; j<9; j++){
                  if(miSudoku[i][j] != resultado[i][j]){
                      return true;
                  }
              }
          }
          return false;
      }

    public int[][] getResultado() {
        return resultado;
    }

    public int[][] getTablero() {
        return tablero;
    }
}
