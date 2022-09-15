package com.example.demo.Repo;

import com.example.demo.model.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepo extends CrudRepository<People, Long> {
}
