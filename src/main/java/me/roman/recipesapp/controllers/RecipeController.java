package me.roman.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.roman.recipesapp.model.Recipe;
import me.roman.recipesapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "API по работе с рецептами", description = "CRUD - операции")
public class RecipeController {
    public final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Добавление рецепта")
    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.save(recipe));
    }

    @Operation(summary = "Получение рецепта по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.getById(id));
    }

    @Operation(summary = "Редактирование рецепта по ID")
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.update(id, recipe));
    }

    @Operation(summary = "Удаление рецепта по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.delete(id));

    }

    @Operation(summary = "Получение списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Всё хорошо, запрос выполнился",
                    content = {
                            @Content(schema = @Schema(implementation = Recipe.class))
                    }
            ),@ApiResponse(
            responseCode = "400",
            description = "Есть ошибка в параметрах запроса",
            content = {
                    @Content(schema = @Schema(implementation = String.class))
            }
    ),@ApiResponse(
            responseCode = "404",
            description = "URL неверный или такого действия нет в веб-приложении",
            content = {
                    @Content(schema = @Schema(implementation = String.class))
            }
    ),@ApiResponse(
            responseCode = "500",
            description = "Во время выполнения запроса произошла ошибка на сервере"
    )
    })
    @GetMapping()
    public ResponseEntity<Map<Long, Recipe>> getAll() {
        return ResponseEntity.ok(recipeService.getAll());
    }

}
