package com.example.myapplication;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void moverNiveles(View view){
        Intent intent = new Intent(this,NivelJugar.class);
        startActivity(intent);
    }
    public void moverPuntuaciones(View view){
        Intent intent = new Intent(this,NivelPuntuacion.class);
        startActivity(intent);
    }
    public void salirApp(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir")
                .setMessage("¿Seguro que quiere salir del juego?" )
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                }).setNegativeButton("Quedarse", null).show();
    }

}
