package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentsAdapter extends RecyclerView.Adapter<InstructionsEquipmentsViewHolder> {

    Context context;

    List<Equipment> stepList;

    public InstructionsEquipmentsAdapter(Context context,List<Equipment> stepList) {
        this.context = context;
        this.stepList = stepList;
    }


    @NonNull
    @Override
    public InstructionsEquipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsEquipmentsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_list,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsEquipmentsViewHolder holder, int position) {
        holder.textView_instructions_step_items.setText(stepList.get(position).name);
        Picasso.get().load("https://img.spoonacular.com/ingredients_250x250/" + stepList.get(position).image).into(holder.imageView_instructions_step_items);
    }




    @Override
    public int getItemCount() {
        return stepList.size();
    }
}

class InstructionsEquipmentsViewHolder extends RecyclerView.ViewHolder {
ImageView imageView_instructions_step_items;
TextView textView_instructions_step_items;

    public InstructionsEquipmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_instructions_step_items = itemView.findViewById(R.id.imageView_instructions_step_items);
        textView_instructions_step_items = itemView.findViewById(R.id.textView_instructions_step_items);

    }
}
