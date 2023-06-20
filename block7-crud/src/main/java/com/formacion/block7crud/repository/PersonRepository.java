package com.formacion.block7crud.repository;

import com.formacion.block7crud.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name);
}