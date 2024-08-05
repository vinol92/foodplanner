package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Bienvenida extends AppCompatActivity {

    private Button btnBack;
    private Button btnFinalizar;

    // Variables para almacenar los datos de RegistroNutri, RegistroNutri2, e InfoUsuario2
    private String nombre;
    private String apellido;
    private String colegiado;
    private String dni;
    private String usuario;
    private String telefono;
    private String email;
    private String contra;
    private String tipousuario;

    private List<String> alergias = new ArrayList<>();

    // Firebase
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);

        // Inicializar vistas
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnFinalizar = findViewById(R.id.finalizar);

        // Inicializar Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Obtener datos de RegistroNutri, RegistroNutri2, InfoUsuario, e InfoUsuario2
        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        apellido = intent.getStringExtra("apellido");
        colegiado = intent.getStringExtra("colegiado");
        dni = intent.getStringExtra("dni");
        usuario = intent.getStringExtra("usuario");
        telefono = intent.getStringExtra("telefono");
        email = intent.getStringExtra("email");
        contra = intent.getStringExtra("contra");
        tipousuario = intent.getStringExtra("tipousuario");
        alergias = intent.getStringArrayListExtra("alergias");

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
                guardarUsuarioEnFirebase();
            }
        });

        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
    }

    private void guardarUsuarioEnFirebase() {
        // Determinar el tipo de usuario y los datos a guardar
        if (dni != null && !dni.isEmpty() && colegiado != null && !colegiado.isEmpty()) {
            tipousuario = "nutricionista";
        } else {
            tipousuario = "paciente";
            dni = "";
            colegiado = "";
        }

        // Crear objeto Usuario
        Usuario usuarioObj = new Usuario(nombre, apellido, colegiado, dni, usuario, telefono, email, contra, tipousuario, alergias);

        // Guardar los datos del usuario en Firebase
        databaseReference.child(usuario).setValue(usuarioObj)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Si el usuario es un paciente, añadir la clave "Tipo suscripcion"
                        if ("paciente".equals(tipousuario)) {
                            databaseReference.child(usuario).child("Tipo suscripcion").setValue("normal")
                                    .addOnCompleteListener(subscriptionTask -> {
                                        if (subscriptionTask.isSuccessful()) {
                                            // Datos guardados exitosamente
                                            Toast.makeText(Bienvenida.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Bienvenida.this, Inicio.class);
                                            intent.putExtra("username", usuario); // Pasar el nombre de usuario como extra
                                            startActivity(intent);
                                            finish(); // Finaliza la actividad actual para que no esté en la pila de retroceso
                                        } else {
                                            // Error al guardar la suscripción
                                            Toast.makeText(Bienvenida.this, "Error al registrar la suscripción", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Datos guardados exitosamente para usuarios no pacientes
                            Toast.makeText(Bienvenida.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Bienvenida.this, Inicio.class);
                            intent.putExtra("username", usuario); // Pasar el nombre de usuario como extra
                            startActivity(intent);
                            finish(); // Finaliza la actividad actual para que no esté en la pila de retroceso
                        }
                    } else {
                        // Error al guardar los datos
                        Toast.makeText(Bienvenida.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Clase Usuario
    public static class Usuario {
        public String nombre;
        public String apellido;
        public String colegiado;
        public String dni;
        public String usuario;
        public String telefono;
        public String email;
        public String contra;
        public String tipousuario;
        public List<String> alergias;

        public Usuario() {
            // Constructor vacío requerido para Firebase
        }

        public Usuario(String nombre, String apellido, String colegiado, String dni, String usuario, String telefono, String email, String contra, String tipousuario, List<String> alergias) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.colegiado = colegiado;
            this.dni = dni;
            this.usuario = usuario;
            this.telefono = telefono;
            this.email = email;
            this.contra = contra;
            this.tipousuario = tipousuario;
            this.alergias = alergias;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public String getColegiado() {
            return colegiado;
        }

        public String getDni() {
            return dni;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getEmail() {
            return email;
        }

        public String getContra() {
            return contra;
        }

        public String getTipousuario() {
            return tipousuario;
        }

        public List<String> getAlergias() {
            return alergias;
        }
    }
}
