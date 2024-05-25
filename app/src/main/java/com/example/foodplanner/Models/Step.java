package com.example.foodplanner.Models;

import java.util.ArrayList;


//Class that is required by the Spoonacular API.
public class Step{
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Equipment> equipment;
    public Length length;
}