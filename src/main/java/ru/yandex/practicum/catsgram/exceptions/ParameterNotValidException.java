package ru.yandex.practicum.catsgram.exceptions;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ParameterNotValidException extends IllegalArgumentException {
    private final String parameter;
    private final String reason;
}
