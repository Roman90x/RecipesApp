package me.roman.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String runApplication() {
        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String info() {
        return "Имя ученика: Селин Роман </br>" +
                "Название проекта: Приложение для сайта рецептов </br>" +
                "Дата создания проекта: 18.02.2023 </br>" +
                "Описание проекта: Проект создан для работ с рецептами блюд </br>";
    }
}
