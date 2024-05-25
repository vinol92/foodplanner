package com.example.foodplanner.Models;

//Class that is required by the Spoonacular API. JSON to POJO.
public class ProductMatch{
    public int id;
    public String title;
    public String description;
    public String price;
    public String imageUrl;
    public double averageRating;
    public double ratingCount;
    public double score;
    public String link;
}
