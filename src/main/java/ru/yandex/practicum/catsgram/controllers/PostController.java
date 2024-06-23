package ru.yandex.practicum.catsgram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exceptions.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.services.PostService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(defaultValue = "desc") String sort,
                                    @RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        SortOrder sortOrder = SortOrder.from(sort);
        if (sortOrder == null) {
            throw new ParameterNotValidException("sortOrder", "некорректное значение");
        }
        if (from < 0) {
            throw new ParameterNotValidException("from", "не может быть меньше нуля");
        }
        if (size < 1) {
            throw new ParameterNotValidException("from", "должен быть больше нуля");
        }

        List<Post> posts = postService.findAll().stream()
                .sorted().toList();

        if (sortOrder == SortOrder.DESCENDING) {
            Collections.reverse(posts);
        } else if (sortOrder != SortOrder.ASCENDING) {
            throw new ConditionsNotMetException("Неправильно указан параметр \"sort\"");
        }

        return posts.stream()
                .skip(from).limit(size).toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> findById(@PathVariable("id") long postId) {
        return postService.findById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
       return postService.update(newPost);
    }
}
