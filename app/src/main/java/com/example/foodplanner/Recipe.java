package com.example.foodplanner;

public class Recipe {
    private String title;
    private String imageUrl;
    private int servings;

    public Recipe(String title, String imageUrl, int servings) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}