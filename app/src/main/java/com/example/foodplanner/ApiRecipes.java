package com.example.foodplanner;

import android.os.Bundle;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiRecipes extends AppCompatActivity {
    private EditText editTextQuery;
    private Button buttonSend;
    private TextView textViewResponse;

    private static final String BASE_URL = "https://api.spoonacular.com/";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_api_recipes);

        editTextQuery = findViewById(R.id.editTextQuery);
        buttonSend = findViewById(R.id.buttonSend);
        textViewResponse = findViewById(R.id.textViewResponse);

        // Configurar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la consulta del EditText
                String query = editTextQuery.getText().toString().trim();

                // Realizar la solicitud a la API utilizando Retrofit
                SpoonacularAPI api = retrofit.create(SpoonacularAPI.class);
                Call<RecipeResponse> call = api.randomRecipe("882a2637a7044d4fb7c2742a578a3e8f", query,2);
                call.enqueue(new Callback<RecipeResponse>() {
                    @Override
                    public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                        if (response.isSuccessful()) {
                            RecipeResponse recipeResponse = response.body();

                            List<Recipe> recipes = recipeResponse.getRecipes();
                            Log.d("Response", "Response body: " + response.body());
                            Log.d("Response", "Recipe response: " + recipeResponse);
                            Log.d("Response", "Recipes: " + recipes);

                            StringBuilder responseText = new StringBuilder();
                            for (Recipe recipe : recipes) {
                                responseText.append(recipe.getTitle()).append("\n");
                            }
                            textViewResponse.setText(responseText.toString());
                        } else {


                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}