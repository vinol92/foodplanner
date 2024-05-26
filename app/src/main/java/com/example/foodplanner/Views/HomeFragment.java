package com.example.foodplanner.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class HomeFragment extends Fragment {
    private TextView textFoodName, textFoodAmount;

    private RecyclerView recyclerView;
    StockAdapter stockAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        obtainStock("Pepe");
        return view;

    }


    //This method obtains the stock from Firebase depending of the user.
    private void obtainStock(String userName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(userName).child("Stock");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Stock> stockList = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                        String foodName = foodSnapshot.getKey();
                        Map<String, Object> foodDetails = (Map<String, Object>) foodSnapshot.getValue();
                        Stock stock = new Stock();
                        stock.setName(foodName);

                        Long amountLong = (Long) foodDetails.get("Amount");
                        int amount = amountLong.intValue();
                        stock.setAmount(amount);

                        Long caloriesLong = (Long) foodDetails.get("Calories");
                        int calories = caloriesLong.intValue();
                        stock.setCalories(calories);

                        stockList.add(stock);
                    }
                    stockAdapter = new StockAdapter(stockList);
                    recyclerView.setAdapter(stockAdapter);
                }


             else {
                    // Manejar el caso en el que el usuario no tenga stock de alimentos
                    textFoodName.setText("El usuario " + userName + " no tiene stock de alimentos.");
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
}