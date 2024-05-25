package com.example.foodplanner.Models;

import android.content.Context;

import com.example.foodplanner.Interfaces.SearchRecipe;
import com.example.foodplanner.Interfaces.InfoRecipes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



public class ApiManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder() //Builds the url with the api of Spoonacular
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiManager(Context context) {
        this.context = context;
    }



    //Interface that has the structure of the call to get a random Recipe by its parameters
    private interface randomRecipeInterface {
        @GET("recipes/random")
        Call<RandomRecipesAPI> randomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") String tags
        );
    }

    //Interface that has the structure of the call to get the details of a recipe by its parameters.
    private interface recipeDetails {
        @GET("recipes/{id}/information")
        Call<InfoIntentRecipe> recipeDetails (
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    //This method makes the call to Spoonacular with the required parameters to get a recipe.
    public void getRandomRecipes(SearchRecipe recipe, String tags) {
        randomRecipeInterface randomRecipes = retrofit.create(randomRecipeInterface.class);
        Call<RandomRecipesAPI> call = randomRecipes.randomRecipe("882a2637a7044d4fb7c2742a578a3e8f", "2",tags);
        call.enqueue(new Callback<RandomRecipesAPI>() {
            //If the response is sucessful, the recipe it gets its body and message.
            @Override
            public void onResponse(Call<RandomRecipesAPI> call, Response<RandomRecipesAPI> response) {

                if(response.isSuccessful()) {
                    recipe.search(response.body(), response.message());
                }
            }

            //If the call fails, it gives an error.
            @Override
            public void onFailure(Call<RandomRecipesAPI> call, Throwable t) {
                recipe.error(t.getMessage());
            }

        });
    }

    //This method makes the call to Spoonacular with the required parameters to get details of a recipe.
    public void getRecipesDetails(InfoRecipes responseList, int id) {
        recipeDetails  details = retrofit.create(recipeDetails.class);
        Call<InfoIntentRecipe> call = details.recipeDetails(id,"882a2637a7044d4fb7c2742a578a3e8f");
        call.enqueue(new Callback<InfoIntentRecipe>() {
            //If the response is sucessful, the recipe gets its body and message which contain the info of the recipe.
            @Override
            public void onResponse(Call<InfoIntentRecipe> call, Response<InfoIntentRecipe> response) {

                if(response.isSuccessful()) {
                    responseList.info(response.body(),response.message());
                }
            }

            @Override
            public void onFailure(Call<InfoIntentRecipe> call, Throwable t) {
                responseList.error(t.getMessage());

            }
        });
    }


}
