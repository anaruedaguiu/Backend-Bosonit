package com.formacion.block7crudvalidation.domain;

import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
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
    private Integer id_student;

    @OneToOne
    @JoinColumn(name = "id_person")
    private Person person;

    @Column(name = "num_hours_week")
    private Integer num_hours_week;

    @Column(name = "comments")
    private String comments;

    @Column(name = "branch")
    private String branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_teacher")
    private Teacher teacher;

    @ManyToMany
    private Set<Subjects> subjects;

    public Student(StudentInputDto studentInputDto) {
        this.id_student = studentInputDto.getId_student();
        Person person = new Person();
        person.setId(studentInputDto.getId_person());
        this.person = person;
        this.num_hours_week = studentInputDto.getNum_hours_week();
        this.comments = studentInputDto.getComments();
        this.branch = studentInputDto.getBranch();
    }

    public StudentOutputFullDto studentToStudentOutputFullDto() {
        return new StudentOutputFullDto(
                this.id_student,
                this.num_hours_week,
                this.comments,
                this.branch,
                this.person.personToPersonOutputDto()
        );
    }

    public StudentOutputSimpleDto studentToStudentOutputSimpleDto() {
        List<SubjectsOutputDto> subjectsList = new ArrayList<SubjectsOutputDto>();
        for(Subjects sub : subjects)
            subjectsList.add(sub.subjectsToSubjectsOutputDto());
        Set<SubjectsOutputDto> subjectsSet = new HashSet<>(subjectsList);
        return new StudentOutputSimpleDto(
                this.id_student,
                this.num_hours_week,
                this.comments,
                this.branch,
                subjectsSet
        );
    }
}