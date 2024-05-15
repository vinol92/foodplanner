package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroNutri2 extends AppCompatActivity {

    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infonutri2);

        // Inicializar vistas
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
                // Crear un Intent para ir a la actividad BienvenidaActivity
                Intent intent = new Intent(RegistroNutri2.this, Bienvenida.class);
                startActivity(intent); // Iniciar la actividad BienvenidaActivity
            }
        });
        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(75);
    }
}
