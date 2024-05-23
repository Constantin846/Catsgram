package ru.yandex.practicum.catsgram.exceptions;

public class ConditionsNotMetException extends RuntimeException {
    public ConditionsNotMetException(String message) {
        super(message);
    }
    public ConditionsNotMetException() {
        super();
    }
}
