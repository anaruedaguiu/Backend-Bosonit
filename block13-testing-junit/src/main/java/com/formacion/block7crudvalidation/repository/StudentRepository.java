package com.formacion.block7crudvalidation.repository;

import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByPerson(Person person);
}
