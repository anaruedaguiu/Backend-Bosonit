package com.formacion.block7crudvalidation.domain;

import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id_teacher;

    @OneToOne
    @JoinColumn(name="id_person")
    private Person person;

    @Column(name = "comments")
    private String comments;

    @Column(name = "branch")
    private String branch;

    @OneToMany(mappedBy = "teacher")
    private Set<Student> studentList;


    public Teacher(TeacherInputDto teacherInputDto) {
        this.id_teacher = teacherInputDto.getId_teacher();
        Person person = new Person();
        person.setId(teacherInputDto.getId_person());
        this.person = person;
        this.branch = teacherInputDto.getBranch();
        this.comments = teacherInputDto.getComments();
    }

    public TeacherOutputFullDto teacherToTeacherOutputFullDto() {
        return new TeacherOutputFullDto(
                this.id_teacher,
                this.comments,
                this.branch,
                this.person.personToPersonOutputDto()
        );
    }

    public TeacherOutputSimpleDto teacherToTeacherOutputSimpleDto() {
        return new TeacherOutputSimpleDto(
                this.id_teacher,
                this.branch,
                this.comments
        );
    }
}