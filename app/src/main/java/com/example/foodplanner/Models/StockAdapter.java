package com.example.foodplanner.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    String userName;
    public StockAdapter(List<Stock> stockList, String userName) {
        this.stockList = stockList;
        this.userName = userName;
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



        holder.addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int amount = stock.getAmount();
             amount++;
            addStockToFirebase(stock,userName,holder,amount);
            }
        });

        holder.deleteStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int amount = stock.getAmount();
                amount--;
                deleteStockToFirebase(stock,userName,holder,amount);

            }
        });
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName,textViewQuantity;
        private Button addStock, deleteStock;


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
        if (amount <= 0) {
            myRef.child(stock.getName()).removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                int position = holder.getAdapterPosition();
                                stockList.remove(position);
                                notifyItemRemoved(position);

                            } else {

                            }
                        }
                    });
        } else {
            myRef.child(stock.getName()).setValue(stock)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
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

}
