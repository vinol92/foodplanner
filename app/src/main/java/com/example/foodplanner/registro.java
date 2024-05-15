package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        // Botón de retroceso
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });

        // Botón para unir menú nutricionista
        Button unirMenuNutri = findViewById(R.id.unirmenutri);
        unirMenuNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad RegistroNutri
                Intent intent = new Intent(registro.this, RegistroNutri.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        // Botón para ir a la actividad InfoUsuario
        Button btnDietas = findViewById(R.id.dieta);
        btnDietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad InfoUsuario
                Intent intent = new Intent(registro.this, InfoUsuario.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        // Botón para ir a la actividad InfoUsuario
        Button btnIdeas = findViewById(R.id.ideas);
        btnIdeas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad InfoUsuario
                Intent intent = new Intent(registro.this, InfoUsuario.class);
                startActivity(intent); // Iniciar la actividad
            }
        });
        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(25);
    }
}
