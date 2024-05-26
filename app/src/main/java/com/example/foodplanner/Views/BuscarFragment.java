package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.Interfaces.SearchRecipe;
import com.example.foodplanner.Models.ApiManager;
import com.example.foodplanner.Models.RandomRecipesAPI;
import com.example.foodplanner.Models.RecipeAdapter;
import com.example.foodplanner.R;


public class BuscarFragment extends Fragment {

    private EditText editTextQuery;
    private Button buttonSend;
    private RecyclerView recyclerView;

    ApiManager apiManager;

    RecipeAdapter recipeAdapter;

    SearchRecipe recipeRecylcerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buscar, container, false);

        editTextQuery = rootView.findViewById(R.id.editTextQuery);
        buttonSend = rootView.findViewById(R.id.buttonSend);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);
        apiManager = new ApiManager(getContext());

        //When this button is pressed, it gets the text of the query and calls the ApiManager class to getRandomRecipes
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextQuery.getText().toString().trim();
                apiManager.getRandomRecipes(searchRecipe,query);
            }
        });

        return rootView;
    }

    //This calls the Interface "SearchRecipe" The method search loads the the recyclerView with the recipes.
    private final SearchRecipe searchRecipe = new SearchRecipe() {
        @Override
        public void search(RandomRecipesAPI response, String message) {
            recipeAdapter = new RecipeAdapter(response.recipes, getContext(), recipeClickIntent);
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
            startActivity(new Intent(getActivity(), RecipeInfoActivity.class).putExtra("id",id));
        }
    };
    }
