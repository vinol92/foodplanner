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

import java.util.List;

public class StepsAdapter  extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    Context context;
    List<Instructions> instructionList;


    public StepsAdapter(Context context,  List<Instructions> instructionList) {
        this.instructionList = instructionList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_layout, parent, false);
        return new StepsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.ingredients.setText(instructionList.get(position).name);
    holder.recycler_instruction_steps.setHasFixedSize(true);
    holder.recycler_instruction_steps.setLayoutManager(new LinearLayoutManager(context));
    InstructionStepAdapter stepAdapter = new InstructionStepAdapter(context, instructionList.get(position).steps);
    holder.recycler_instruction_steps.setAdapter(stepAdapter);
    }

    @Override
    public int getItemCount() {
    return instructionList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredients;

        RecyclerView recycler_instruction_steps;

        public ViewHolder(View v) {
            super(v);
            ingredients = v.findViewById(R.id.ingredients);
            recycler_instruction_steps = v.findViewById(R.id.recycler_instruction_steps);
        }
    }
}
