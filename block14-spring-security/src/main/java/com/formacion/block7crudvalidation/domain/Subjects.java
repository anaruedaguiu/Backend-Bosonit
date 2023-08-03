package com.formacion.block7crudvalidation.domain;

import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.annotation.MatrixVariableParameterProcessor;

import java.rmi.StubNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "study")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id_subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> student;

    @Column(name = "subject")
    private String subject;

    @Column(name = "comments")
    private  String comments;

    @Column(name = "initial_date")
    private Date initial_date;

    @Column(name = "finish_date")
    private Date finish_date;

    public Subjects(SubjectsInputDto subjectsInputDto) {
        teacher = new Teacher();
        teacher.setId_teacher(subjectsInputDto.getId_teacher());
        this.subject = subjectsInputDto.getSubject();
        this.comments = subjectsInputDto.getComments();
        this.initial_date = subjectsInputDto.getInitial_date();
        this.finish_date = subjectsInputDto.getFinish_date();
    }

    public SubjectsOutputDto subjectsToSubjectsOutputDto() {
        return new SubjectsOutputDto(
                this.id_subject,
                this.teacher.getId_teacher(),
                this.subject,
                this.comments,
                this.initial_date,
                this.finish_date
        );
    }
}
