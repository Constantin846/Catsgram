package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = {"email", "id"})
public class User {
    Long id;
    String username;
    String password;
    Instant registrationDate;
    String email;
}
