package com.example.demo.controller;


import com.example.demo.Repo.NickRepo;
import com.example.demo.model.Nick;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NickController {

    private final NickRepo repository;

    public NickController(NickRepo repo)
    {
        this.repository = repo;
    }


    @GetMapping("/Nick")
    public Iterable<Nick> all(){
        return this.repository.findAll();
    }

    @PostMapping("/Nick")
    public Nick create(@RequestBody Nick nick)
    {
        return this.repository.save(nick);
    }

    @GetMapping("/Nick/{id}")
    public ResponseEntity<Nick> getOneNick(@PathVariable Long id)
    {
        if (this.repository.findById(id).isPresent()){
            Nick nick = this.repository.findById(id).get();
            return new ResponseEntity<>(nick, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Nick/{id}")
    public ResponseEntity<Object> deleteMapping(@PathVariable Long id)
    {
        this.repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
