package com.example.foodplanner.Models;

import java.util.ArrayList;

//Class that is required by the Spoonacular API. JSON to POJO.
public class ExtendedIngredient {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalName;
    public double amount;
    public String unit;
    public ArrayList<String> meta;
    public Measures measures;
}
