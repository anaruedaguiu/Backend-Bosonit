package com.formacion.block7crudvalidation.application.impl;

import com.formacion.block7crudvalidation.application.TeacherService;
import com.formacion.block7crudvalidation.controllers.TeacherController;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import com.formacion.block7crudvalidation.domain.Teacher;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import com.formacion.block7crudvalidation.repository.StudentRepository;
import com.formacion.block7crudvalidation.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    StudentRepository studentRepository;

    @Override
    public TeacherOutputFullDto getTeacherById(int id) {
        if(teacherRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return teacherRepository.findById(id).orElseThrow()
                    .teacherToTeacherOutputFullDto();
        }
    }

    @Override
    public Optional<TeacherOutputFullDto> addTeacher(TeacherInputDto teacherInputDto) throws Exception {
        Person person = personRepository.findById(teacherInputDto.getId_person()).orElseThrow();
        if(person.getRole() != null)
            throw new Exception("This person has already a role assigned");
        person.setRole("Teacher");
        Teacher teacher = new Teacher(teacherInputDto);
        person.setTeacher(teacher);
        teacher.setPerson(person);
        Teacher teacher1 = teacherRepository.save(teacher);
        return Optional.of(teacher1.teacherToTeacherOutputFullDto());
    }

    @Override
    public void addTeacherToStudent(int id_teacher, int id_student) {
        Student student = studentRepository.findById(id_student).orElseThrow();
        Teacher teacher = teacherRepository.findById(id_teacher).orElseThrow();

        student.setTeacher(teacher);
        teacher.getStudentList().add(student);

        studentRepository.save(student);
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(int id) throws EntityNotFoundException {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if(teacherOptional.isEmpty())
            throw new EntityNotFoundException();

        Teacher teacher = teacherOptional.get();
        Person person = teacher.getPerson();
        person.setRole(null);
        personRepository.save(person);

        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherOutputFullDto updateTeacherById(TeacherInputDto teacherInputDto, int id) {
        if(teacherRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Teacher teacherUpdated = teacherRepository.findById(id).orElseThrow();
            teacherUpdated.setBranch(teacherInputDto.getBranch());
            teacherUpdated.setComments(teacherInputDto.getComments());
            return teacherRepository.save(teacherUpdated)
                    .teacherToTeacherOutputFullDto();
        }
    }

    @Override
    public Iterable<TeacherOutputFullDto> getAllTeachers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return teacherRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Teacher::teacherToTeacherOutputFullDto).toList();
    }
}
