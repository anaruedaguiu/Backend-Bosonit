package com.formacion.block7crudvalidation.domain;

import com.formacion.block7crudvalidation.controllers.dto.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputSimpleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_person")
    private Person person;

    @Column(name = "num_hours_week")
    private Integer num_hours_week;

    @Column(name = "comments")
    private String comments;

    @Column(name = "branch")
    private String branch;

    @ManyToMany(mappedBy = "students")
    private Set<Student_Studies> studentStudies;

    public Student(StudentInputDto studentInputDto) {
        this.id = studentInputDto.getId();
        Person person = new Person();
        person.setId(studentInputDto.getId_person());
        this.person = person;
        this.num_hours_week = studentInputDto.getNum_hours_week();
        this.comments = studentInputDto.getComments();
        this.branch = studentInputDto.getBranch();
    }

    public StudentOutputFullDto studentToStudentOutputFullDto() {
        return new StudentOutputFullDto(
                this.id,
                this.num_hours_week,
                this.comments,
                this.branch,
                this.person.personToPersonOutputDto()
        );
    }

    public StudentOutputSimpleDto studentToStudentOutputSimpleDto() {
        return new StudentOutputSimpleDto(
                this.id,
                this.num_hours_week,
                this.comments,
                this.branch
        );
    }
}