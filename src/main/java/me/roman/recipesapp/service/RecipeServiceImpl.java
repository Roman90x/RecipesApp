package me.roman.recipesapp.service;

import me.roman.recipesapp.exception.ValidationException;
import me.roman.recipesapp.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class RecipeServiceImpl implements RecipeService {
    private static long idCounter = 1;
    private final Map<Long, Recipe> recipes = new HashMap<>();
    private final ValidationService validationService;

    public RecipeServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        return recipes.put(idCounter++, recipe);
    }

    @Override
    public Optional<Recipe> getById(long id) {
        return Optional.ofNullable(recipes.get(id));
    }
}
