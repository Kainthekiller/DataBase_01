package com.example.demo.controller;


import com.example.demo.Repo.NickRepo;
import com.example.demo.model.Lesson;
import com.example.demo.model.Nick;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
        //Found
        if (this.repository.findById(id).isPresent()){
            Nick nick = this.repository.findById(id).get();
            return new ResponseEntity<>(nick, HttpStatus.ACCEPTED);
        }
        //Not Found
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

      //  return repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("No Such Element Found %d", id)));
    }

    @DeleteMapping("/Nick/{id}")
    public ResponseEntity<Nick> deleteMapping(@PathVariable Long id)
    {
        if (this.repository.findById(id).isPresent())
        {
            this.repository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); //204
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }


    }

//    @DeleteMapping("/Nick/{id}")
//    public ResponseEntity<Object> deleteMappings(@PathVariable Long id)
//    {
//
//        this.repository.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public  ResponseEntity<object> noFound(Exception e) {
//        return String.format("Record with id %d is not present", id), e.getMessage();
//    }


}
