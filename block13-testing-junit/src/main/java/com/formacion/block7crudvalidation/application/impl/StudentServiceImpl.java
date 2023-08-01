package com.formacion.block7crudvalidation.application.impl;

import com.formacion.block7crudvalidation.application.StudentService;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.SubjectsOutputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import com.formacion.block7crudvalidation.domain.Subjects;
import com.formacion.block7crudvalidation.domain.Teacher;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import com.formacion.block7crudvalidation.repository.StudentRepository;
import com.formacion.block7crudvalidation.repository.SubjectsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SubjectsRepository subjectsRepository;

    @Override
    public Optional<StudentOutputFullDto> addStudentFull(StudentInputDto studentInputDto) throws  Exception {
        Person person = personRepository.findById(studentInputDto.getId_person()).orElseThrow();
        if(person.getRole() != null)
            throw new Exception("This person has already a role assigned");
        person.setRole("Student");

        Student student = new Student(studentInputDto);
        /*person.setStudent(student);*/
        student.setPerson(person);
        Student student1 = studentRepository.save(student);
        return Optional.of(student1.studentToStudentOutputFullDto());
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
    public void deleteStudentById(Integer id) throws EntityNotFoundException {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty())
            throw new EntityNotFoundException();

        Student student = studentOptional.get();
        Person person = student.getPerson();
        person.setRole(null);
        personRepository.save(person);

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
    @Override
    public StudentOutputSimpleDto addSubjectListToStudent(int id, List<Integer> id_list) throws Exception {
        Student student = studentRepository.findById(id).orElseThrow();

        Set<Subjects> subjectsList = new HashSet<>();
        for(Integer id_subject : id_list) {
            Subjects subject = subjectsRepository.findById(id_subject).orElseThrow();
            subjectsList.add(subject);
        }

        student.setSubjects(subjectsList);
        studentRepository.save(student);

        return student.studentToStudentOutputSimpleDto();
    }

    @Override
    public StudentOutputSimpleDto removeSubjectListToStudent(int id, List<Integer> id_list) throws Exception {
        Student student = studentRepository.findById(id).orElseThrow();
        Set<Subjects> subjectsList = student.getSubjects();

        List<Subjects> subjectsToRemove = new ArrayList<>();

        for(Subjects s :subjectsList) {
            if(id_list.contains(s.getId_subject())) {
                subjectsToRemove.add(s);
            }
        }

        subjectsList.removeAll(subjectsToRemove);
        student.setSubjects(subjectsList);

        return studentRepository.save(student)
                .studentToStudentOutputSimpleDto();
    }
}