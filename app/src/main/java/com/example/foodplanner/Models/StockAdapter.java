package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Interfaces.RecipeClickIntent;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        holder.addStock.setImageResource(R.drawable.estrella);
        holder.deleteStock.setImageResource(R.drawable.lector);


        holder.addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int amount = stock.getAmount();
             amount++;
            addStockToFirebase(stock,"pepe",holder,amount);
            }
        });

        holder.deleteStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = stock.getAmount();
                amount--;
                deleteStockToFirebase(stock,"pepe",holder,amount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName,textViewQuantity;
        private ImageView addStock, deleteStock;


        public ViewHolder(View v) {
            super(v);
            textViewProductName = v.findViewById(R.id.textViewProductName);
            textViewQuantity = v.findViewById(R.id.textViewQuantity);
            addStock = v.findViewById(R.id.addStock);
            deleteStock = v.findViewById(R.id.deleteStock);
        }
    }
    private void addStockToFirebase(Stock stock, String userName, ViewHolder holder, int amount) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(userName).child("Stock");
            stock.setAmount(amount);
         myRef.child(stock.getName()).setValue(stock)
                 .addOnCompleteListener(new OnCompleteListener<Void>(){
                     @Override
                     public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             holder.textViewQuantity.setText(String.valueOf(stock.getAmount()));


                         } else {

                         }
                     }
                 });
    }
    private void deleteStockToFirebase(Stock stock, String userName, ViewHolder holder, int amount) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(userName).child("Stock");
        stock.setAmount(amount);
        myRef.child(stock.getName()).setValue(stock)
                .addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            holder.textViewQuantity.setText(String.valueOf(stock.getAmount()));


                        } else {

                        }
                    }
                });
    }

}
