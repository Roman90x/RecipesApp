package me.roman.recipesapp.service;

import me.roman.recipesapp.model.Recipe;

import java.util.Optional;

public interface RecipeService {
    Recipe save(Recipe recipe);

    Optional<Recipe> getById(long id);
}
