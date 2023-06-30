package com.formacion.block7crudvalidation.repository;

import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByPerson(Person person);
}
