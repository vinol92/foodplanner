package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.R;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<Stock> stockList;
    public StockAdapter(List<Stock> stockList) {
        this.stockList = stockList;

    }
    @NonNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock_layout, parent, false);
        return new StockAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.textViewProductName.setText(stock.getName());
        holder.textViewQuantity.setText(String.valueOf(stock.getAmount()));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName,textViewQuantity;


        public ViewHolder(View v) {
            super(v);
            textViewProductName = v.findViewById(R.id.textViewProductName);
            textViewQuantity = v.findViewById(R.id.textViewQuantity);
        }
    }

}
