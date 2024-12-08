package ru.yandex.practicum.catsgram.exceptions;

public class ImageFileException extends RuntimeException {
    public ImageFileException(String message) {
        super(message);
    }

    public ImageFileException(String message, Exception e) {
        super(message, e);
    }
}
