package com.example.myapplication;

public class Puntuacion {
    private String nombre;
    private long tiempoSegundos;
    private String tiempo;
    private String dificultad;

    public Puntuacion(){}

    public Puntuacion(String nombre,long tiempoSegundos, String tiempo, String dificultad){
        this.nombre=nombre;
        this.tiempoSegundos=tiempoSegundos;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public long getTiempoSegundos() {
        return tiempoSegundos;
    }

    public void setTiempoSegundos(long tiempoSegundos) {
        this.tiempoSegundos = tiempoSegundos;
    }
}
