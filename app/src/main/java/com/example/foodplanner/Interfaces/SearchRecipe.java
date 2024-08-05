package com.example.foodplanner.Interfaces;

import com.example.foodplanner.Models.RandomRecipesAPI;

//This interface search in the API the recipe that is wanted
public interface SearchRecipe {

    void search(RandomRecipesAPI response, String message);
    void error(String message);

}
