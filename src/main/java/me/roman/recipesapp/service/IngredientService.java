package me.roman.recipesapp.service;

import me.roman.recipesapp.model.Ingredient;

import java.util.Optional;

public interface IngredientService {
    Ingredient save(Ingredient ingredient);

    Optional <Ingredient> getById(long id);
}
