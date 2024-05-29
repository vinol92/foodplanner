package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;

public class RegistroNutri extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSiguiente;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etColegiado;
    private EditText etDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infonutri);

        // Inicializar vistas
        btnBack = findViewById(R.id.btn_back);
        btnSiguiente = findViewById(R.id.siguiente);
        etNombre = findViewById(R.id.innombre);
        etApellido = findViewById(R.id.inapellido);
        etColegiado = findViewById(R.id.incolegiado);
        etDNI = findViewById(R.id.indni);

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
                // Verificar campos vacíos y otros errores
                String mensajeError = validarCampos();
                if (mensajeError != null) {
                    Toast.makeText(RegistroNutri.this, mensajeError, Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un Intent para ir a la actividad RegistroNutri2
                    Intent intent = new Intent(RegistroNutri.this, RegistroNutri2.class);

                    // Pasar los datos a través del Intent
                    intent.putExtra("nombre", etNombre.getText().toString().trim());
                    intent.putExtra("apellido", etApellido.getText().toString().trim());
                    intent.putExtra("colegiado", etColegiado.getText().toString().trim());
                    intent.putExtra("dni", etDNI.getText().toString().trim());

                    startActivity(intent); // Iniciar la actividad RegistroNutri2
                }
            }
        });

        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);
    }

    // Método para validar si todos los campos EditText están rellenados
    private String validarCampos() {
        if (TextUtils.isEmpty(etNombre.getText().toString().trim())) {
            return "El campo Nombre es obligatorio";
        }
        if (TextUtils.isEmpty(etApellido.getText().toString().trim())) {
            return "El campo Apellido es obligatorio";
        }
        if (TextUtils.isEmpty(etColegiado.getText().toString().trim())) {
            return "El campo Colegiado es obligatorio";
        }
        if (TextUtils.isEmpty(etDNI.getText().toString().trim())) {
            return "El campo DNI es obligatorio";
        }
        return null; // No hay errores
    }
}
