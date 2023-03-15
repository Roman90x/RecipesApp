package me.roman.recipesapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import me.roman.recipesapp.exception.ValidationException;
import me.roman.recipesapp.model.Recipe;
import me.roman.recipesapp.service.RecipeService;
import me.roman.recipesapp.service.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private static long idCounter = 1;
    private Map<Long, Recipe> recipes = new HashMap<>();
    private final ValidationService validationService;
    private final FileServiceImpl fileServiceImpl;

    @Value("${path.to.recipes.file}")
    private String recipesFilePath;
    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    private Path recipesPath;


    @Override
    public Recipe save(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(idCounter++, recipe);
        fileServiceImpl.saveMapToFile(recipes, recipesPath);
        return recipe;
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.replace(id, recipe);
        fileServiceImpl.saveMapToFile(recipes, recipesPath);
        return recipe;
    }

    @Override
    public Recipe delete(Long id) {
        Recipe recipe = recipes.remove(id);
        fileServiceImpl.saveMapToFile(recipes, recipesPath);
        return recipe;
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return recipes;
    }

    @Override
    public File readFile() {
        return recipesPath.toFile();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        fileServiceImpl.uploadFile(file, recipesPath);
        recipes = fileServiceImpl.readMapFromFile(recipesPath, new TypeReference<>() {
        });
    }

    @PostConstruct
    private void init() {
        recipesPath = Path.of(recipesFilePath, recipesFileName);
        recipes = fileServiceImpl.readMapFromFile(recipesPath, new TypeReference<>() {
        });
    }
}
