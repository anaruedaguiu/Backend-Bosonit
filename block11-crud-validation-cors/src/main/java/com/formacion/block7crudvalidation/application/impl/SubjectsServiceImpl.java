package com.formacion.block7crudvalidation.application.impl;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.formacion.block7crudvalidation.application.SubjectsService;
import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import com.formacion.block7crudvalidation.domain.Subjects;
import com.formacion.block7crudvalidation.domain.Teacher;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.exceptions.UnprocessableEntityException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import com.formacion.block7crudvalidation.repository.StudentRepository;
import com.formacion.block7crudvalidation.repository.SubjectsRepository;
import com.formacion.block7crudvalidation.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectsServiceImpl implements SubjectsService {
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Override
    public SubjectsOutputDto addSubject(SubjectsInputDto subjectsInputDto) {
        return subjectsRepository.save(new Subjects(subjectsInputDto)).subjectsToSubjectsOutputDto();
    }

    @Override
    public void addSubjectsToStudent(int id_subject, int id_student) {
        Student student = studentRepository.findById(id_student).orElseThrow();
        Subjects subject = subjectsRepository.findById(id_subject).orElseThrow();

        student.getSubjects().add(subject);
        studentRepository.save(student);
    }

    @Override
    public SubjectsOutputDto getSubjectById(int id) {
        if(subjectsRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return subjectsRepository.findById(id).orElseThrow()
                    .subjectsToSubjectsOutputDto();
        }
    }

    @Override
    public Iterable<SubjectsOutputDto> getAllSubjects(int pageNumber, int pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return subjectsRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Subjects::subjectsToSubjectsOutputDto).toList();
    }

    @Override
    public void deleteSubjectById(int id) {
        if(subjectsRepository.findById(id).isEmpty())
            throw new EntityNotFoundException();

        subjectsRepository.deleteById(id);
    }

    @Override
    public SubjectsOutputDto updateSubjectById(SubjectsInputDto subjectsInputDto, int id) {
        if(subjectsRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Subjects subjectUpdated = subjectsRepository.findById(id).orElseThrow();
            subjectUpdated.setComments(subjectsInputDto.getComments());
            subjectUpdated.setSubject(subjectsInputDto.getSubject());
            return subjectsRepository.save(subjectUpdated)
                    .subjectsToSubjectsOutputDto();
        }
    }

    @Override
    public StudentOutputSimpleDto getSubjectsListByStudent(int id_student) {
        Student student = studentRepository.findById(id_student).orElseThrow();
        List<SubjectsOutputDto> subjectsList = subjectsRepository.findByIdStudent(id_student)
                .stream()
                .map(Subjects::subjectsToSubjectsOutputDto)
                .toList();
        Set<SubjectsOutputDto> subjects = new HashSet<>(subjectsList);

        return new StudentOutputSimpleDto(student.getId_student(), student.getNum_hours_week(), student.getComments(),
                student.getBranch(), subjects);
    }
}
