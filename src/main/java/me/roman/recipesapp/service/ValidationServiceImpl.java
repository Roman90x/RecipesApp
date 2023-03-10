package me.roman.recipesapp.service;

import me.roman.recipesapp.model.Ingredient;
import me.roman.recipesapp.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ValidationServiceImpl implements ValidationService{
    @Override
    public boolean validate(Recipe recipe) {
        return recipe != null
                && recipe.getName() != null
                && recipe.getSteps() != null
                && recipe.getIngredients() != null
                && !recipe.getName().isEmpty()
                && !recipe.getSteps().isEmpty()
                && !recipe.getIngredients().isEmpty()
                && !recipe.getName().isBlank();
    }

    @Override
    public boolean validate(Ingredient ingredient) {
        return ingredient != null
                && ingredient.getName() != null
                && ingredient.getSize() != null
                && !ingredient.getName().isEmpty()
                && !ingredient.getSize().isEmpty()
                && !ingredient.getName().isBlank()
                && !ingredient.getSize().isBlank();
    }
}
