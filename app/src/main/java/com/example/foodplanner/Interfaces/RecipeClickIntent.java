package com.example.foodplanner.Interfaces;


//This interface is to check which recipe is selected to open the intent with the id of the recipe.
public interface RecipeClickIntent {
    void onRecipeClicked(String id);
}
