package me.roman.recipesapp.controllers;

import me.roman.recipesapp.model.Recipe;
import me.roman.recipesapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resipe")
public class RecipeController {
    public final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.save(recipe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable long id) {
        return ResponseEntity.of(recipeService.getById(id));
    }
}
