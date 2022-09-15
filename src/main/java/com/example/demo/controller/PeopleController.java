package com.example.demo.controller;

import com.example.demo.Repo.PeopleRepo;
import com.example.demo.model.People;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {


    private PeopleRepo repo;

    public PeopleController(PeopleRepo repo)
    {
        this.repo = repo;
    }

    //Get EveryBody From the database
    @GetMapping("")
    public Iterable<People> all()
    {
        return this.repo.findAll();
    }

    @PostMapping("")
    public People create(@RequestBody People people)
    {
        return this.repo.save(people);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMapping(@PathVariable Long id)
    {
        this.repo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
