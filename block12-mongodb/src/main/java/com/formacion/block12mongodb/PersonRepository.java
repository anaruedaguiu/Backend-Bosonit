package com.formacion.block12mongodb;

import com.formacion.block12mongodb.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Integer> {
}
