package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import java.util.List;

public class StepsAdapter  extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    List<Instructions> instructionList;
    Context context;


    public StepsAdapter(Context context, List<Instructions> instructionList) {
        this.context = context;
        this.instructionList = instructionList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_layout, parent, false);
        return new StepsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.step.setText(instructionList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView step,ingredients;

            RecyclerView stepsRecyclerView;
        public ViewHolder(View v) {
            super(v);
            step = v.findViewById(R.id.step);
            ingredients = v.findViewById(R.id.ingredients);
        }
    }
}
