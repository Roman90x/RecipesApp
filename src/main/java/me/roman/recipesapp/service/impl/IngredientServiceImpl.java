package me.roman.recipesapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import me.roman.recipesapp.exception.ValidationException;
import me.roman.recipesapp.model.Ingredient;
import me.roman.recipesapp.service.IngredientService;
import me.roman.recipesapp.service.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private static long idCounter = 1;
    private Map<Long, Ingredient> ingredients = new HashMap<>();
    private final ValidationService validationService;
    private final FileService fileService;

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;
    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    private Path ingredientPath;

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.put(idCounter++, ingredient);
        fileService.saveMapToFile(ingredients, ingredientPath);
        return ingredient;
    }

    @Override
    public Optional<Ingredient> getById(Long id) {
        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.replace(id, ingredient);
        fileService.saveMapToFile(ingredients, ingredientPath);
        return ingredient;
    }

    @Override
    public Ingredient delete(Long id) {
        Ingredient ingredient = ingredients.remove(id);
        fileService.saveMapToFile(ingredients, ingredientPath);
        return ingredient;
    }

    @Override
    public Map<Long, Ingredient> getAll() {
        return ingredients;
    }

    @PostConstruct
    private void init() {
        ingredientPath = Path.of(ingredientsFilePath, ingredientsFileName);
        ingredients = fileService.readMapFromFile(ingredientPath, new TypeReference<HashMap<Long, Ingredient>>() {
        });
    }
}
