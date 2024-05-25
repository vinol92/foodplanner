package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.R;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

//Adapter of recyclerView for recipes that are found by Spoonacular API.
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipeList;

    Context context;

    RecipeClickIntent listenerRecipe;

    public RecipeAdapter(List<Recipe> recipeList, Context context, RecipeClickIntent listenerRecipe) {
        this.recipeList = recipeList;
        this.context = context;
        this.listenerRecipe  = listenerRecipe;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        translateRecipeTitle(recipeList.get(position).getTitle(), holder); //Translate to spanish the name of the Recipe
        Picasso.get().load(recipeList.get(position).image).into(holder.imageFood); //This is a library to load images.

        //If the name of the Recipe is open, it goes to the intent of the recipe selected
        holder.recipeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenerRecipe.onRecipeClicked(String.valueOf(recipeList.get(holder.getAdapterPosition()).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeTitle;

        private ImageView imageFood;
        public ViewHolder(View v) {
            super(v);
            recipeTitle = v.findViewById(R.id.recipeTitle);
            imageFood = v.findViewById(R.id.imageFood);
        }
    }



    // This method translate the Recipe Title
    private void translateRecipeTitle(String title, ViewHolder holder) {
        // Configure the options of the translator
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.SPANISH)
                        .build();

        Translator englishSpanishTranslator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().build();


        englishSpanishTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        ignored -> {
                            // Translates the title.
                            englishSpanishTranslator.translate(title)
                                    .addOnSuccessListener(
                                            translatedText -> {
                                                holder.recipeTitle.setText(translatedText);
                                                englishSpanishTranslator.close();
                                            })
                                    .addOnFailureListener(
                                            e -> {
                                                englishSpanishTranslator.close();
                                            });
                        });
    }



}

