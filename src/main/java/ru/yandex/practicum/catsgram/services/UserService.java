package ru.yandex.practicum.catsgram.services;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exceptions.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exceptions.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public Optional<User> findById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public User create(User user) {
        String userEmail = user.getEmail();

        if (userEmail == null || userEmail.isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (checkEmailExists(userEmail)) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            String userEmail = user.getEmail();

            if (userEmail != null) {
                if (!userEmail.equals(oldUser.getEmail())) {
                    if (checkEmailExists(userEmail)) {
                        throw new DuplicatedDataException("Этот имейл уже используется");
                    }
                }
                oldUser.setEmail(userEmail);
            }

            if (user.getUsername() != null) {
                oldUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                oldUser.setPassword(user.getPassword());
            }

            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private boolean checkEmailExists(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
