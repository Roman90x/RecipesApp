package me.roman.recipesapp.service;

import me.roman.recipesapp.model.Ingredient;
import me.roman.recipesapp.model.Recipe;

public interface ValidationService {
    public boolean validate(Recipe recipe);

    public boolean validate(Ingredient ingredient);
}
