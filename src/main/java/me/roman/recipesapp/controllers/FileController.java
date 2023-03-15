package me.roman.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.roman.recipesapp.model.Ingredient;
import me.roman.recipesapp.service.IngredientService;
import me.roman.recipesapp.service.RecipeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/files")
@Tag(name = "API по работе с файлами", description = "Загрузка и выгрузка файлов")
@RequiredArgsConstructor
public class FileController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Operation(summary = "Выгрузка файла рецептов")
    @GetMapping("/recipe/export")
    public ResponseEntity<InputStreamResource> downloadRecipesFile() {
        try {
            File recipeFile = recipeService.readFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(recipeFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipeFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Выгрузка файла ингредиентов")
    @GetMapping("/ingredient/export")
    public ResponseEntity<InputStreamResource> downloadIngredientsFile() {
        try {
            File ingredientFile = ingredientService.readFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(ingredientFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(ingredientFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ingredientFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла рецептов")
    public ResponseEntity<String> uploadRecipesFile(@RequestParam MultipartFile file) {
        try {
            recipeService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла");
        }
    }

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла ингредиентов")
    public ResponseEntity<String> uploadIngredientsFile(@RequestParam MultipartFile file) {
        try {
            ingredientService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла");
        }
    }
}
