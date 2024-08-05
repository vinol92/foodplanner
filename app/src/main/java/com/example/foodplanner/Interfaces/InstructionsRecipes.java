package com.example.foodplanner.Interfaces;

import com.example.foodplanner.Models.Instructions;

import java.util.List;

public interface InstructionsRecipes {
    void steps(List<Instructions> response, String message);
    void error(String message);

}
