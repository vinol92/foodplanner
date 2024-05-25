package com.example.foodplanner.Models;

import java.util.ArrayList;

//Class that is required by the Spoonacular API. JSON to POJO.
public class AnalyzedInstruction {
    public String name;
    public ArrayList<Step> steps;
}
