package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.Models.FavoriteRecipeAdapter;
import com.example.foodplanner.Models.Recipe;
import com.example.foodplanner.Models.RecipeAdapter;
import com.example.foodplanner.Models.Stock;
import com.example.foodplanner.Models.StockAdapter;
import com.example.foodplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FavoritosFragment extends Fragment {

    private TextView textFoodName, textFoodAmount;

    private RecyclerView recyclerView;
    FavoriteRecipeAdapter recipeAdapter;
    String usuarioiniciado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            usuarioiniciado = getArguments().getString("usuario");
        }
        obtainRecipes(usuarioiniciado);
        return view;

    }

    private void obtainRecipes(String userName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(userName).child("Recipes");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Recipe> recipeList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                        String foodName = foodSnapshot.getKey();
                        Map<String, Object> foodDetails = (Map<String, Object>) foodSnapshot.getValue();
                        Recipe recipe = new Recipe();
                        recipe.setTitle(foodName);
                        String id = foodDetails.get("id").toString();
                        recipe.setId(Integer.parseInt(id));
                        String image = foodDetails.get("image").toString();
                        recipe.setImage(image);
                        recipeList.add(recipe);
                    }
                    recipeAdapter = new FavoriteRecipeAdapter(recipeList,getContext(),recipeClickIntent,usuarioiniciado);
                    recyclerView.setAdapter(recipeAdapter);
                }

                else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores al obtener el stock de alimentos
                textFoodName.setText("Error al obtener el stock de alimentos del usuario " + userName);
                textFoodAmount.setText("Error: " + databaseError.getMessage());
            }
        });
    }
    private final RecipeClickIntent recipeClickIntent = new RecipeClickIntent() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(getActivity(), RecipeInfoActivity.class).putExtra("id",id));
        }
    };
}