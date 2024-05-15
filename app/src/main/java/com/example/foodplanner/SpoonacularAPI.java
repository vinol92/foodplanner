package com.example.foodplanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularAPI {
    @GET("recipes/random")
    Call<RecipeResponse> randomRecipe(
            @Query("apiKey") String apiKey,
            @Query("tags") String tags,
            @Query("number") int number

    );

}