package com.example.foodplanner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio); // Reemplaza "inicio" con el nombre real de tu archivo XML

      //  btnPerfil = findViewById(R.id.perfil); // Reemplaza "perfil" con el ID real de tu botón en inicio.xml

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reemplaza el contenido del contenedor con el fragmento PerfilFragment
               // getSupportFragmentManager().beginTransaction()
                      //  .replace(R.id.frameLayoutContainer, new PerfilFragment()) // Reemplaza "frameLayoutContainer" con el ID real de tu FrameLayout en inicio.xml
                     //   .commit();
            }
        });
    }
}
