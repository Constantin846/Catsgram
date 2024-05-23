package ru.yandex.practicum.catsgram.exceptions;

public class DuplicatedDataException extends RuntimeException {
    public DuplicatedDataException(String message) {
        super(message);
    }

    public DuplicatedDataException() {
        super();
    }
}
