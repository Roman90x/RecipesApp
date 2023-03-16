package me.roman.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Тестовый контроллер")
public class TestController {

    @Operation(summary = "Проверка запуска приложения")
    @GetMapping
    public String runApplication() {
        return "Приложение запущено";
    }

    @Operation(summary = "Получение информации о приложении")
    @GetMapping("/info")
    public String info() {
        return "Имя ученика: Селин Роман </br>" +
                "Название проекта: Приложение для сайта рецептов </br>" +
                "Дата создания проекта: 18.02.2023 </br>" +
                "Описание проекта: Проект создан для работ с рецептами блюд </br>";
    }
}
