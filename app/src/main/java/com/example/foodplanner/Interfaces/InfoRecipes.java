package com.example.foodplanner.Interfaces;

import com.example.foodplanner.Models.InfoIntentRecipe;


//This interface is for the info of the recipes into the intent.
public interface InfoRecipes {

    void info(InfoIntentRecipe response, String message);
    void error(String message);
}
