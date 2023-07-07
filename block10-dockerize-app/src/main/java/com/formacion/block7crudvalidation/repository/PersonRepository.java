package com.formacion.block7crudvalidation.repository;

import com.formacion.block7crudvalidation.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String user);
}