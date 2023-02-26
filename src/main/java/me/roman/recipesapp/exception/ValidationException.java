package me.roman.recipesapp.exception;

import org.apache.el.util.Validation;

public class ValidationException extends RuntimeException {
    public ValidationException(String entity) {
        super("Ошибка валидации " + entity);
    }
}
