package com.example.foodplanner.Views;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.List;


//Class for the view that is the intent for the info of the recipes.
public class RecipeInfoActivity extends AppCompatActivity {
int id;
ImageView imageViewFood;
TextView textViewTitle, textViewInfo;
RecyclerView recyclerViewSteps;

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
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new ApiManager(this);
        manager.getRecipesDetails(infoRecipes,id);
        manager.getRecipeSteps(instructionsRecipes, id);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));

    }


    //This calls the Interface "InfoRecipes" The method info set the text for the textViews, images.
    private final InfoRecipes infoRecipes = new InfoRecipes() {
        @Override
        public void info(InfoIntentRecipe response, String message) {
            textViewTitle.setText(response.title);
            textViewInfo.setText(response.summary);
            Picasso.get().load(response.image).into(imageViewFood);
        }

        @Override
        public void error(String message) {

        }
    };
    private final InstructionsRecipes instructionsRecipes = new InstructionsRecipes() {
        @Override
        public void steps(List<Instructions> response, String message) {
            recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            stepsAdapter = new StepsAdapter(getApplicationContext(),response);


            recyclerViewSteps.setAdapter(stepsAdapter);
        }

        @Override
        public void error(String message) {

        }
    };
}