package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder> {

    Context context;

    List<Step> stepList;

    public InstructionStepAdapter(Context context,List<Step> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.stepTextView.setText(String.valueOf(stepList.get(position).number));
        translateStepTitle(stepList.get(position).step, holder);
        holder.ingredients_recycler_view_2.setHasFixedSize(true);
        holder.ingredients_recycler_view_2.setLayoutManager(new LinearLayoutManager(context));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter= new InstructionsIngredientsAdapter(context,stepList.get(position).ingredients);
        holder.ingredients_recycler_view_2.setAdapter(instructionsIngredientsAdapter);


        holder.equipments_recycler_view.setHasFixedSize(true);
        holder.equipments_recycler_view.setLayoutManager(new LinearLayoutManager(context));
        InstructionsEquipmentsAdapter instructionsEquipmentsAdapter = new InstructionsEquipmentsAdapter(context,stepList.get(position).equipment);
        holder.equipments_recycler_view.setAdapter(instructionsEquipmentsAdapter);
    }


    private void translateStepTitle(String title, InstructionStepViewHolder holder) {
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
                                                holder.stepTitleTextView.setText(translatedText);
                                                englishSpanishTranslator.close();
                                            })
                                    .addOnFailureListener(
                                            e -> {
                                                englishSpanishTranslator.close();
                                            });
                        });
    }
    @Override
    public int getItemCount() {
        return stepList.size();
    }
}

class InstructionStepViewHolder extends RecyclerView.ViewHolder {
   TextView stepTextView, stepTitleTextView;
   RecyclerView equipments_recycler_view, ingredients_recycler_view_2;
    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        stepTextView = itemView.findViewById(R.id.stepTextView);
        stepTitleTextView = itemView.findViewById(R.id.stepTitleTextView);
        equipments_recycler_view = itemView.findViewById(R.id.equipments_recycler_view);
        ingredients_recycler_view_2 = itemView.findViewById(R.id.ingredients_recycler_view_2);

    }
}
