package com.example.foodplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoUsuario extends AppCompatActivity {

    private Button btnSiguiente;
    private EditText etNombre, etApellido, etUsuario, etEmail, etContra, etContra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infousuario);

        // Inicializar vistas
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnSiguiente = findViewById(R.id.siguiente);
        etNombre = findViewById(R.id.innombre);
        etApellido = findViewById(R.id.inapellido);
        etUsuario = findViewById(R.id.inuser);
        etEmail = findViewById(R.id.inemail);
        etContra = findViewById(R.id.incontra);
        etContra2 = findViewById(R.id.incontra2);

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
                // Validar campos
                String mensajeError = validarCampos();
                if (mensajeError != null) {
                    Toast.makeText(InfoUsuario.this, mensajeError, Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un Intent para ir a la actividad InfoUsuario2
                    Intent intent = new Intent(InfoUsuario.this, InfoUsuario2.class);
                    startActivity(intent); // Iniciar la actividad InfoUsuario2
                }
            }
        });

        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);
    }

    // Método para validar los campos
    private String validarCampos() {
        // Validar campo Nombre
        if (TextUtils.isEmpty(etNombre.getText().toString().trim())) {
            return "El campo Nombre es obligatorio";
        }

        // Validar campo Apellidos
        if (TextUtils.isEmpty(etApellido.getText().toString().trim())) {
            return "El campo Apellidos es obligatorio";
        }

        // Validar campo Usuario
        if (TextUtils.isEmpty(etUsuario.getText().toString().trim())) {
            return "El campo Usuario es obligatorio";
        }

        // Validar campo Email
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            return "El campo Email es obligatorio";
        } else if (!validarFormatoEmail(etEmail.getText().toString().trim())) {
            return "El campo Email debe contener un formato válido";
        }

        // Validar campo Contraseña
        if (TextUtils.isEmpty(etContra.getText().toString().trim())) {
            return "El campo Contraseña es obligatorio";
        } else if (!validarFormatoContraseña(etContra.getText().toString().trim())) {
            return "La contraseña debe tener al menos 8 caracteres y contener al menos una mayúscula, una minúscula, un número y un carácter especial";
        }

        // Validar campo Repita contraseña
        if (TextUtils.isEmpty(etContra2.getText().toString().trim())) {
            return "El campo Repita contraseña es obligatorio";
        }

        // Validar que las contraseñas coincidan
        String contra = etContra.getText().toString().trim();
        String contra2 = etContra2.getText().toString().trim();
        if (!contra.equals(contra2)) {
            return "Las contraseñas no coinciden";
        }

        return null; // No hay errores
    }

    // Método para validar el formato de correo electrónico
    private boolean validarFormatoEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para validar el formato de la contraseña
    private boolean validarFormatoContraseña(String contraseña) {
        // Verificar longitud mínima
        if (contraseña.length() < 8) {
            return false;
        }
        // Verificar presencia de caracteres especiales, números, mayúsculas y minúsculas
        boolean contieneCaracterEspecial = contraseña.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        boolean contieneNumero = contraseña.matches(".*\\d.*");
        boolean contieneMayuscula = !contraseña.equals(contraseña.toLowerCase());
        boolean contieneMinuscula = !contraseña.equals(contraseña.toUpperCase());
        return contieneCaracterEspecial && contieneNumero && contieneMayuscula && contieneMinuscula;
    }
}
