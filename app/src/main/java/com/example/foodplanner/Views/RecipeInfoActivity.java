package com.example.foodplanner.Views;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Interfaces.InfoRecipes;
import com.example.foodplanner.Interfaces.InstructionsRecipes;
import com.example.foodplanner.Models.ApiManager;
import com.example.foodplanner.Models.InfoIntentRecipe;
import com.example.foodplanner.Models.Instructions;
import com.example.foodplanner.Models.RecipeAdapter;
import com.example.foodplanner.Models.StepsAdapter;
import com.example.foodplanner.R;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.squareup.picasso.Picasso;

import java.util.List;


//Class for the view that is the intent for the info of the recipes.
public class RecipeInfoActivity extends AppCompatActivity {
    int id;
    ImageView imageViewFood;
    TextView textViewTitle, textViewInfo;
    RecyclerView recyclerViewSteps;
    private ImageButton btnBack; // Cambia Button a ImageButton
    ProgressBar progressBar;

    StepsAdapter stepsAdapter;
    ApiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        imageViewFood = findViewById(R.id.imageViewFood);
        textViewInfo = findViewById(R.id.textViewInfo);
        textViewTitle = findViewById(R.id.textViewTitle);
        recyclerViewSteps = findViewById(R.id.recyclerViewSteps);
        btnBack = findViewById(R.id.btn_back);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new ApiManager(this);
        manager.getRecipesDetails(infoRecipes, id);
        manager.getRecipeSteps(instructionsRecipes, id);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Termina la actividad actual y vuelve a la anterior
            }
        });
    }


    //This calls the interface of infoRecipes.
    private final InfoRecipes infoRecipes = new InfoRecipes() {
        @Override
        public void info(InfoIntentRecipe response, String message) {
            translateRecipeQuery(response.title);
            translateRecipeQuery2(response.summary);
            Picasso.get().load(response.image).into(imageViewFood);


        }

        @Override
        public void error(String message) {
        }
    };


    /**
     * Comentario en español para que se entienda perfectamente lo que hace esto:
     * Básicamente, esto llama a la interfaz de instructionRecipes que devuelve por así decirlo toda la query que trae
     * las instrucciones (ingredientes, sus pasos de como se hace). Entonces, eso lo asigna a un recyclerView.
     * Ese RecyclerView, tiene por dentro otro recyclerview que conecta con un layout donde se ve otra lista para los ingredientes y ya el texto
     * donde se asigna la información de cada paso. ¿Porque se hace así?. Debido a la API ya que devuelve tanto como los ingredientes y los pasos
     * como listas, entonces hay que gestionarlos por separados.
     */
    private final InstructionsRecipes instructionsRecipes = new InstructionsRecipes() {
        @Override
        public void steps(List<Instructions> response, String message) {
            stepsAdapter = new StepsAdapter(RecipeInfoActivity.this, response);
            recyclerViewSteps.setHasFixedSize(true);
            recyclerViewSteps.setAdapter(stepsAdapter);


        }

        @Override
        public void error(String message) {
        }
    };

    private void translateRecipeQuery(String query) {
        // opciones del traductor
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build();

        Translator englishSpanishTraductor = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().build();

        englishSpanishTraductor.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(ignored -> {
                    // Traduce la consulta
                    englishSpanishTraductor.translate(query)
                            .addOnSuccessListener(translatedText -> {
                                textViewTitle.setText(translatedText);
                                englishSpanishTraductor.close();
                            })
                            .addOnFailureListener(e -> englishSpanishTraductor.close());
                });
    }

    private void translateRecipeQuery2(String query) {
        //opciones del traductor
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build();

        Translator englishSpanishTraductor = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().build();

        englishSpanishTraductor.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(ignored -> {
                    // Traduce la consulta
                    englishSpanishTraductor.translate(query)
                            .addOnSuccessListener(translatedText -> {
                                textViewInfo.setText(translatedText);
                                englishSpanishTraductor.close();
                            })
                            .addOnFailureListener(e -> englishSpanishTraductor.close());
                });
    }
}
