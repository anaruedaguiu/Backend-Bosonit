package com.formacion.block7crud.repository;

import com.formacion.block7crud.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
