package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Interfaces.SearchRecipe;
import com.example.foodplanner.Models.RandomRecipesAPI;
import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.R;
import com.example.foodplanner.Models.RecipeAdapter;
import com.example.foodplanner.Models.ApiManager;

//Class for the view that search recipes in Spoonacular and gets the results in a RecyclerView.
public class ApiRecipes extends AppCompatActivity {
    private EditText editTextQuery;
    private Button buttonSend;
    private RecyclerView recyclerView;

    ApiManager apiManager;

    RecipeAdapter recipeAdapter;

    SearchRecipe recipeRecylcerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_recipes);

        editTextQuery = findViewById(R.id.editTextQuery);
        buttonSend = findViewById(R.id.buttonSend);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiManager = new ApiManager(this);

        //When this button is pressed, it gets the text of the query and calls the ApiManager class to getRandomRecipes
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextQuery.getText().toString().trim();
                apiManager.getRandomRecipes(searchRecipe,query);
            }
        });


    }

    //This calls the Interface "SearchRecipe" The method search loads the the recyclerView with the recipes.
   private final SearchRecipe searchRecipe = new SearchRecipe() {
        @Override
        public void search(RandomRecipesAPI response, String message) {
            recipeAdapter = new RecipeAdapter(response.recipes, ApiRecipes.this, recipeClickIntent);
            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void error(String message) {

        }

    };

    // This calls the Interface "RecipeClickIntent". The method onRecipeClciked opens an Intent with the Recipe selected.
    private final RecipeClickIntent recipeClickIntent = new RecipeClickIntent() {
        @Override
        public void onRecipeClicked(String id) {
                startActivity(new Intent(ApiRecipes.this, RecipeInfoActivity.class).putExtra("id",id));
        }
    };
}



