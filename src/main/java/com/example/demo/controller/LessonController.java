package com.example.demo.controller;

import com.example.demo.model.Lesson;
import com.example.demo.Repo.LessonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LessonController {


    private final LessonRepository repository;

    public LessonController(LessonRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all()
    {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson)
    {
        return this.repository.save(lesson);
    }


}
