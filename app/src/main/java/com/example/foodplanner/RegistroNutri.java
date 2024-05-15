package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroNutri extends AppCompatActivity {

    private Button btnBack;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infonutri);

        // Inicializar vistas
        // Busca el botón btn_back por su ID
        ImageButton btnBack = findViewById(R.id.btn_back);

        btnSiguiente = findViewById(R.id.siguiente);

        // Configurar clic del botón "Atrás"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        // Configurar clic del botón "Siguiente"
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para ir a RegistroNutri2Activity
                Intent intent = new Intent(RegistroNutri.this, RegistroNutri2.class);
                startActivity(intent); // Iniciar la actividad RegistroNutri2Activity
            }
        });
        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);
    }
}