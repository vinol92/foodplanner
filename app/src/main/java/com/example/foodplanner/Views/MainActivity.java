package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button registra, acceder;
    TextView inuss, inpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registra = findViewById(R.id.registra);
        inuss = findViewById(R.id.inuss);
        inpass = findViewById(R.id.inpass);
        acceder = findViewById(R.id.acceder);

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, registro.class);
                startActivity(i);
            }
        });

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsuario();
            }
        });
    }

    private void checkUsuario() {
        final String userInput = inuss.getText().toString().trim();
        final String password = inpass.getText().toString().trim();

        if (userInput.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean userFound = false;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String storedUsername = userSnapshot.child("usuario").getValue(String.class);
                    String storedEmail = userSnapshot.child("email").getValue(String.class);
                    String storedPassword = userSnapshot.child("contra").getValue(String.class);
                    String tipoUsuario = userSnapshot.child("tipousuario").getValue(String.class);

                    if ((userInput.equals(storedUsername) || userInput.equals(storedEmail)) && storedPassword != null && storedPassword.equals(password)) {
                        userFound = true;
                        Intent intent = new Intent(MainActivity.this, Inicio.class);
                        intent.putExtra("username", userInput); // Pasar el nombre de usuario como extra
                        intent.putExtra("tipousuario", tipoUsuario); // Pasar el tipo de usuario como extra
                        startActivity(intent);
                        finish(); // Finaliza la actividad actual para que no esté en la pila de retroceso
                        break;
                    }
                }

                if (!userFound) {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error en la base de datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
