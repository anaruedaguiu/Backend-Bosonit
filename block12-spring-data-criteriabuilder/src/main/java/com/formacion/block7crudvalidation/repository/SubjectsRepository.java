package com.formacion.block7crudvalidation.repository;

import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import com.formacion.block7crudvalidation.domain.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SubjectsRepository extends JpaRepository<Subjects, Integer> {
    @Query("SELECT ae FROM Subjects ae JOIN ae.student s WHERE s.id = :id_student")
    Set<Subjects> findByIdStudent(Integer id_student);
}