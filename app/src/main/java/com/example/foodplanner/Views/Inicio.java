package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.foodplanner.R;

public class Inicio extends AppCompatActivity {

    ImageButton btnPerfil, btnLupa, btnLector, btnEstrella, btnHome;
// String usuarioiniciado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

       // Intent intent = getIntent();
      //  usuarioiniciado = intent.getStringExtra("usuario");

        // Obtener el nombre de usuario pasado desde MainActivity
        String username = getIntent().getStringExtra("username");

        // Referenciar los elementos de diseño XML
        btnPerfil = findViewById(R.id.perfil);
        btnLupa = findViewById(R.id.lupa);
        btnLector = findViewById(R.id.lector);
        btnEstrella = findViewById(R.id.estrella);
        btnHome = findViewById(R.id.home);

        // Referenciar el TextView nombreusu
        TextView nombreUsuTextView = findViewById(R.id.nombreusu);

        // Establecer el nombre de usuario en el TextView
        if (username != null && !username.isEmpty()) {
            nombreUsuTextView.setText(username);
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

                // Reemplaza el contenido del contenedor con el fragmento PerfilFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, perfilFragment) // Reemplaza "infousu2" con el ID real de tu FrameLayout en inicio.xml
                        .commit();
            }
        });

        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new BuscarFragment())
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
                        .replace(R.id.infousu2, new FavoritosFragment())
                        .commit();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infousu2, new HomeFragment())
                        .commit();
            }
        });
    }
}
