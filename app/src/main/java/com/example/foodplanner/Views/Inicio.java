package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inicio extends AppCompatActivity {

    ImageButton btnPerfil, btnLupa, btnLector, btnEstrella, btnHome;
    Button btnPremium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        // Referenciar los elementos de diseño XML
        btnPerfil = findViewById(R.id.perfil);
        btnLupa = findViewById(R.id.lupa);
        btnLector = findViewById(R.id.lector);
        btnEstrella = findViewById(R.id.estrella);
        btnHome = findViewById(R.id.home);
        btnPremium = findViewById(R.id.btnpremium);

        // Obtener el nombre de usuario y el tipo de usuario pasados desde MainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String tipoUsuario = intent.getStringExtra("tipousuario");

        // Referenciar el TextView nombreusu
        TextView nombreUsuTextView = findViewById(R.id.nombreusu);

        // Establecer el nombre de usuario en el TextView
        if (username != null && !username.isEmpty()) {
            nombreUsuTextView.setText(username);
        }

        // Ajustar la visibilidad del botón btnpremium según el tipo de usuario
        if ("paciente".equals(tipoUsuario)) {
            btnPremium.setVisibility(View.VISIBLE);
        } else {
            btnPremium.setVisibility(View.GONE);
        }

        // Configurar los clics de los botones
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el fragmento de perfil y pasarle el nombre de usuario
                PerfilFragment perfilFragment = new PerfilFragment();
                Bundle args = new Bundle();
                args.putString("usuario", username);
                perfilFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, perfilFragment)
                        .commit();
            }
        });

        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarFragment buscarFragment = new BuscarFragment();
                Bundle args = new Bundle();
                args.putString("usuario", username);
                buscarFragment.setArguments(args);

                // Reemplaza el contenido del contenedor con el fragmento PerfilFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, buscarFragment)
                        .commit();
            }
        });

        btnLector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, OcrActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnEstrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritosFragment favoritosFragment = new FavoritosFragment();
                Bundle args = new Bundle();
                args.putString("usuario", username);
                favoritosFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, favoritosFragment)
                        .commit();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                Bundle args = new Bundle();
                args.putString("usuario", username);
                homeFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, homeFragment)
                        .commit();
            }
        });

        btnPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener una referencia a la ubicación del usuario en la base de datos
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(username);

                // Escuchar el valor de tiposuscripcion en la base de datos
                userRef.child("tiposuscripcion").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Obtener el valor de tiposuscripcion
                        String tiposuscripcion = dataSnapshot.getValue(String.class);

                        // Verificar si el tiposuscripcion es "premium"
                        if ("premium".equals(tiposuscripcion)) {
                            // Mostrar un Toast indicando que el usuario ya está suscrito
                            Toast.makeText(Inicio.this, "¡Ya estás suscrito!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Si no es "premium", abrir el fragmento PremiumFragment
                            PremiumFragment premiumFragment = new PremiumFragment();
                            Bundle args = new Bundle();
                            args.putString("username", username);
                            args.putString("tipousuario", tipoUsuario);
                            premiumFragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.infousu2, premiumFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Manejar el error, si lo hay
                        Toast.makeText(Inicio.this, "Error al obtener el tipo de suscripción: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
