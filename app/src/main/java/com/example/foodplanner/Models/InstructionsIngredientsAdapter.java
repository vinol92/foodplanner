package com.example.foodplanner.Models;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsIngredientsAdapter extends RecyclerView.Adapter<InstructionsIngredientsViewHolder> {

    Context context;

    List<Ingredient> stepList;

    public InstructionsIngredientsAdapter(Context context,List<Ingredient> stepList) {
        this.context = context;
        this.stepList = stepList;
    }


    @NonNull
    @Override
    public InstructionsIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_list,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsIngredientsViewHolder holder, int position) {
        translateStepTitle(stepList.get(position).name, holder);
        Picasso.get().load("https://img.spoonacular.com/ingredients_100x100/" + stepList.get(position).image).into(holder.imageView_instructions_step_items);
    }




    @Override
    public int getItemCount() {
        return stepList.size();
    }

    private void translateStepTitle(String title, InstructionsIngredientsViewHolder holder) {
        // Configura las opciones del traductor
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
                            // Traduce el título.
                            englishSpanishTranslator.translate(title)
                                    .addOnSuccessListener(
                                            translatedText -> {
                                                holder.textView_instructions_step_items.setText(translatedText);
                                                englishSpanishTranslator.close();
                                            })
                                    .addOnFailureListener(
                                            e -> {
                                                englishSpanishTranslator.close();
                                            });
                        });
    }
}

class InstructionsIngredientsViewHolder extends RecyclerView.ViewHolder {
ImageView imageView_instructions_step_items;
TextView textView_instructions_step_items;

    public InstructionsIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_instructions_step_items = itemView.findViewById(R.id.imageView_instructions_step_items);
        textView_instructions_step_items = itemView.findViewById(R.id.textView_instructions_step_items);

    }


}
