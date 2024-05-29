package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import com.example.foodplanner.R;

public class Inicio extends AppCompatActivity {

     ImageButton btnPerfil, btnLupa, btnLector,btnEstrella, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        btnPerfil = findViewById(R.id.perfil);
        btnLupa = findViewById(R.id.lupa);
        btnLector = findViewById(R.id.lector);
        btnEstrella = findViewById(R.id.estrella);
        btnHome = findViewById(R.id.home);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reemplaza el contenido del contenedor con el fragmento PerfilFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new PerfilFragment()) // Reemplaza "frameLayoutContainer" con el ID real de tu FrameLayout en inicio.xml
                             .commit();
            }
        });

        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new BuscarFragment()) // Reemplaza "frameLayoutContainer" con el ID real de tu FrameLayout en inicio.xml
                        .commit();
            }
        });

        btnLector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, OcrActivity.class);
                startActivity(intent);
            }
        });

        btnEstrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new FavoritosFragment()) // Reemplaza "frameLayoutContainer" con el ID real de tu FrameLayout en inicio.xml
                        .commit();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reemplaza el contenido del contenedor con el fragmento PerfilFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new HomeFragment()) // Reemplaza "frameLayoutContainer" con el ID real de tu FrameLayout en inicio.xml
                        .commit();
            }
        });


    }


}
