package com.example.foodplanner.Views;

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

import com.example.foodplanner.R;

public class RegistroNutri2 extends AppCompatActivity {

    public static String nombre;
    public static String apellido;
    public static String colegiado;

    public static String usuario;
    public static String telefono;
    public static String email;
    public static String contra;
    public static String dni;
    private EditText inUser;
    private EditText inTelefono;
    private EditText inEmail;
    private EditText inContra;
    private EditText inContra2;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infonutri2);

        // Inicializar vistas
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnSiguiente = findViewById(R.id.siguiente);

        // Obtener referencias de EditText
        inUser = findViewById(R.id.inuser);
        inTelefono = findViewById(R.id.intelefono);
        inEmail = findViewById(R.id.inemail);
        inContra = findViewById(R.id.incontra);
        inContra2 = findViewById(R.id.incontra2);

        nombre= String.valueOf(inUser.getText());
        apellido= String.valueOf(inUser.getText());
        colegiado= String.valueOf(inUser.getText());
        dni= String.valueOf(inUser.getText());

        usuario= String.valueOf(inUser.getText());
        telefono= String.valueOf(inTelefono.getText());
        email= String.valueOf(inEmail.getText());
        contra = String.valueOf(inContra.getText());

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
                    Toast.makeText(RegistroNutri2.this, mensajeError, Toast.LENGTH_SHORT).show();
                } else if (!validarContraseña()) {
                    Toast.makeText(RegistroNutri2.this, "La contraseña no cumple con los requisitos de seguridad", Toast.LENGTH_SHORT).show();
                } else if (!contraseñasCoinciden()) {
                    Toast.makeText(RegistroNutri2.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    // Crear un Intent para ir a la actividad Bienvenida
                    Intent intent = new Intent(RegistroNutri2.this, Bienvenida.class);
                  //  String []todoaString = {nombre,apellido,colegiado,dni,usuario,telefono,email,contra};
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    intent.putExtra("colegiado",colegiado);
                    intent.putExtra("dni",dni);
                    intent.putExtra("usuario",usuario);
                    intent.putExtra("telefono",telefono);
                    intent.putExtra("email",email);
                    intent.putExtra("contra",contra);
                    startActivity(intent); // Iniciar la actividad Bienvenida
                }
            }
        });

        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(75);
    }

    // Método para validar campos y devolver un mensaje de error descriptivo
    private String validarCampos() {
        if (TextUtils.isEmpty(inUser.getText().toString().trim())) {
            return "El campo Usuario es obligatorio";
        }
        if (TextUtils.isEmpty(inTelefono.getText().toString().trim())) {
            return "El campo Teléfono es obligatorio";
        }
        if (TextUtils.isEmpty(inEmail.getText().toString().trim())) {
            return "El campo Email es obligatorio";
        }
        if (!validarFormatoEmail(inEmail.getText().toString().trim())) {
            return "El campo Email debe contener un formato válido";
        }
        if (TextUtils.isEmpty(inContra.getText().toString().trim())) {
            return "El campo Contraseña es obligatorio";
        }
        if (TextUtils.isEmpty(inContra2.getText().toString().trim())) {
            return "El campo Repita contraseña es obligatorio";
        }
        return null; // No hay errores
    }

    // Método para validar el formato de correo electrónico
    private boolean validarFormatoEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para validar la contraseña
    private boolean validarContraseña() {
        String contraseña = inContra.getText().toString().trim();
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

    // Método para verificar si las contraseñas coinciden
    private boolean contraseñasCoinciden() {
        String contraseña1 = inContra.getText().toString().trim();
        String contraseña2 = inContra2.getText().toString().trim();
        return contraseña1.equals(contraseña2);
    }
}
