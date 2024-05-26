package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;

public class Bienvenida extends AppCompatActivity {

    private Button btnBack;
    private Button btnFinalizar;

    // Variables para almacenar los datos de RegistroNutri y RegistroNutri2
    private String nombre;
    private String apellido;
    private String colegiado;
    private String dni;
    private String usuario;
    private String telefono;
    private String email;
    private String contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);

        // Inicializar vistas
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnFinalizar = findViewById(R.id.finalizar);

        // Obtener datos de RegistroNutri
        Intent intentRegistroNutri = getIntent();
        nombre = intentRegistroNutri.getStringExtra("nombre");
        apellido = intentRegistroNutri.getStringExtra("apellido");
        colegiado = intentRegistroNutri.getStringExtra("colegiado");
        dni = intentRegistroNutri.getStringExtra("dni");

        // Obtener datos de RegistroNutri2
        Intent intentRegistroNutri2 = getIntent();
        usuario = intentRegistroNutri2.getStringExtra("usuario");
        telefono = intentRegistroNutri2.getStringExtra("telefono");
        email = intentRegistroNutri2.getStringExtra("email");
        contra = intentRegistroNutri2.getStringExtra("contra");

        // Configurar clic del botón "Atrás"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        // Configurar clic del botón "Finalizar"
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(Bienvenida.this, Inicio.class);
               // startActivity(intent); // Iniciar la actividad RegistroNutri2
            }
        });
        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
    }
}
