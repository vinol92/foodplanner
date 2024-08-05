package com.example.foodplanner.Models;

public class Stock {

    private String name;
    private int amount;
    private int calories;

    // Constructor
    public Stock(String name, int amount, int calories) {
        this.name = name;
        this.amount = amount;
        this.calories = calories;
    }

    public Stock() {

    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getCalories() {
        return calories;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
