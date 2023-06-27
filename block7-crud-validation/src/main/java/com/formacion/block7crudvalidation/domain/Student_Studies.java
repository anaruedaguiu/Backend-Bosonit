package com.formacion.block7crudvalidation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "study")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student_Studies {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id_study;

    @ManyToMany
    @JoinTable(
            name = "student_studies",
            joinColumns = @JoinColumn(name = "id_study"),
            inverseJoinColumns = @JoinColumn(name = "id_student")
    )
    private Set<Student> students;

    @Column(name = "course")
    private String course;

    @Column(name = "coments")
    private  String coments;

    @Column(name = "initial_date")
    private Date initial_date;

    @Column(name = "finish_date")
    private Date finish_date;
}
