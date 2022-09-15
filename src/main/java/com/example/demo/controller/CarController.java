package com.example.demo.controller;

import com.example.demo.Repo.CarRepo;
import com.example.demo.model.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    private final CarRepo repository;

    public CarController(CarRepo repository)
    {
        this.repository = repository;
    }

    @GetMapping("/Car")
    public Iterable<Car> all()
    {
        return this.repository.findAll();
    }

    @PostMapping("/Car")
    public Car create(@RequestBody Car car)
    {
        return this.repository.save(car);
    }
}
