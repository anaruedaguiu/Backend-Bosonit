package com.formacion.block7crudvalidation.application.impl;

import com.formacion.block7crudvalidation.application.StudentService;
import com.formacion.block7crudvalidation.controllers.dto.PersonOutputDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import com.formacion.block7crudvalidation.repository.StudentRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PersonRepository personRepository;

    @Override
    public StudentOutputFullDto addStudentFull(StudentInputDto studentInputDto) throws  Exception {
        Person person = personRepository.findById(studentInputDto.getId_person()).orElseThrow();
        Student student = new Student(studentInputDto);
        person.setStudent(student);
        student.setPerson(person);
        return studentRepository.save(student)
                .studentToStudentOutputFullDto();
    }

    @Override
    public StudentOutputSimpleDto getStudentSimpleById(Integer id) {
        if(studentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return studentRepository.findById(id).orElseThrow()
                    .studentToStudentOutputSimpleDto();
        }
    }

    @Override
    public StudentOutputFullDto getStudentFullById(Integer id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return studentRepository.findById(id).orElseThrow()
                    .studentToStudentOutputFullDto();
        }
    }

    @Override
    public Iterable<StudentOutputFullDto> getAllStudents(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return studentRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Student::studentToStudentOutputFullDto).toList();
    }

    @Override
    public void deleteStudentById(Integer id) {
        if(studentRepository.findById(id).isEmpty())
            throw new EntityNotFoundException();
        Student student = studentRepository.findById(id).orElseThrow();
        Person person = personRepository.findById(student.getPerson().getId()).orElseThrow();
        studentRepository.deleteById(id);
    }

    @Override
    public StudentOutputFullDto updateStudentFullById(StudentInputDto student, Integer id) {
        if(studentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Student studentUpdated = studentRepository.findById(id).orElseThrow();
            studentUpdated.setNum_hours_week(student.getNum_hours_week());
            studentUpdated.setComments(student.getComments());
            studentUpdated.setBranch(student.getBranch());
            return studentRepository.save(studentUpdated)
                    .studentToStudentOutputFullDto();
        }
    }
}