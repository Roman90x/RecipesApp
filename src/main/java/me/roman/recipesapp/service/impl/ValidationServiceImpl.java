package me.roman.recipesapp.service.impl;

import me.roman.recipesapp.model.Ingredient;
import me.roman.recipesapp.model.Recipe;
import me.roman.recipesapp.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(Recipe recipe) {
        return recipe != null
                && !StringUtils.isBlank(recipe.getName())
                && !StringUtils.isEmpty(recipe.getName())
                && recipe.getName() != null
                && recipe.getSteps() != null
                && recipe.getIngredients() != null
                && !recipe.getSteps().isEmpty()
                && !recipe.getIngredients().isEmpty();
    }

    @Override
    public boolean validate(Ingredient ingredient) {
        return ingredient != null
                && !StringUtils.isBlank(ingredient.getName())
                && !StringUtils.isEmpty(ingredient.getName())
                && !StringUtils.isBlank(ingredient.getSize())
                && !StringUtils.isEmpty(ingredient.getSize())
                && ingredient.getName() != null
                && ingredient.getSize() != null;
    }
}
