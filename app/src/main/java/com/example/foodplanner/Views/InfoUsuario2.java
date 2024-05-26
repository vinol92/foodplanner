package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class InfoUsuario2 extends AppCompatActivity {
    public static String nombre;
    public static String apellido;
    public static String usuario;
    public static String email;
    public static String contra;

    private ImageButton btnBack;
    private Button btnSiguiente;
    private LinearLayout containerAlergias;
    private Button btnAgregarAlergia;
    private List<EditText> editTexts = new ArrayList<>();
    private CheckBox vegano, grasa, lacteo, azucar, pescetariano, gluten, vegetariano;
    private List<String> alergias = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infousuario2);

        // Inicializar vistas
        btnBack = findViewById(R.id.btn_back);
        btnSiguiente = findViewById(R.id.siguiente);
        containerAlergias = findViewById(R.id.container_alergias);
        btnAgregarAlergia = findViewById(R.id.btn_agregar_alergia);
        // Inicializar CheckBox
        vegano = findViewById(R.id.vegano);
        grasa = findViewById(R.id.grasa);
        lacteo = findViewById(R.id.lacteo);
        azucar = findViewById(R.id.azucar);
        pescetariano = findViewById(R.id.pescetariano);
        gluten = findViewById(R.id.gluten);
        vegetariano = findViewById(R.id.vegetariano);

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
                guardarAlergias();
                // Crear un Intent para ir a la actividad Bienvenida
                Intent intent = new Intent(InfoUsuario2.this, Bienvenida.class);
                // Pasar la lista de alergias
                intent.putStringArrayListExtra("alergias", (ArrayList<String>) alergias);
                startActivity(intent); // Iniciar la actividad Bienvenida
            }
        });

        // Configurar clic del botón "Agregar alergia"
        btnAgregarAlergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEditText();
            }
        });

        // Configurar el ProgressBar para que se rellene un poco
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(75);
    }

    // Método para agregar dinámicamente un EditText al LinearLayout
    private void agregarEditText() {
        EditText editText = new EditText(this);
        editText.setHint("Especifica la alergia o intolerancia");
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setBackgroundResource(R.drawable.shape); // Aplicar el fondo personalizado
        containerAlergias.addView(editText);
        editTexts.add(editText);
    }
    // Método para guardar los valores de los CheckBox y EditText en la lista de alergias
    private void guardarAlergias() {
        alergias.clear(); // Limpiar la lista antes de agregar nuevos valores

        // Verificar y guardar el estado de los CheckBox
        if (vegano.isChecked()) alergias.add(vegano.getText().toString());
        if (grasa.isChecked()) alergias.add(grasa.getText().toString());
        if (lacteo.isChecked()) alergias.add(lacteo.getText().toString());
        if (azucar.isChecked()) alergias.add(azucar.getText().toString());
        if (pescetariano.isChecked()) alergias.add(pescetariano.getText().toString());
        if (gluten.isChecked()) alergias.add(gluten.getText().toString());
        if (vegetariano.isChecked()) alergias.add(vegetariano.getText().toString());

        // Guardar los valores de los EditText
        for (EditText editText : editTexts) {
            String texto = editText.getText().toString().trim();
            if (!texto.isEmpty()) {
                alergias.add(texto);
            }
        }
    }
}
