package com.formacion.block7crudvalidation;

import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.application.impl.TeacherServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputSimpleDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputFullDto;
import com.formacion.block7crudvalidation.controllers.dto.output.TeacherOutputSimpleDto;
import com.formacion.block7crudvalidation.domain.Person;
import com.formacion.block7crudvalidation.domain.Student;
import com.formacion.block7crudvalidation.domain.Subjects;
import com.formacion.block7crudvalidation.domain.Teacher;
import com.formacion.block7crudvalidation.exceptions.EntityNotFoundException;
import com.formacion.block7crudvalidation.repository.PersonRepository;
import com.formacion.block7crudvalidation.repository.StudentRepository;
import com.formacion.block7crudvalidation.repository.SubjectsRepository;
import com.formacion.block7crudvalidation.repository.TeacherRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherServiceMockSpringContextTest {
    @InjectMocks
    TeacherServiceImpl teacherServiceImpl;
    Person p;
    @Mock
    PersonRepository personRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    SubjectsRepository subjectsRepository;

    @Test
    public void testAddTeacher() throws Exception {
        int id = 1;
        Person person = new Person();
        person.setId(id);

        TeacherInputDto teacherInputDto = new TeacherInputDto();
        teacherInputDto.setId_teacher(1);
        teacherInputDto.setComments("comments");
        teacherInputDto.setBranch("branch");
        teacherInputDto.setId_person(1);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        teacher.setComments(teacherInputDto.getComments());
        teacher.setBranch(teacherInputDto.getBranch());
        teacher.setPerson(person);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Optional<TeacherOutputFullDto> resultOptional = teacherServiceImpl.addTeacher(teacherInputDto);
        assertThat(resultOptional).isNotNull();
        TeacherOutputFullDto result = resultOptional.get();

        assertEquals(teacher.getId_teacher(), result.getId_teacher());
        assertEquals(teacher.getComments(), result.getComments());
        assertEquals(teacher.getBranch(), result.getBranch());

        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    public void testCanNotAddTeacherIfPersonHasRoleAssigned() {
        int id = 1;
        Person person = new Person();
        person.setId(id);
        person.setRole("Student");

        TeacherInputDto teacherInputDto = new TeacherInputDto();
        teacherInputDto.setId_teacher(1);
        teacherInputDto.setComments("comments");
        teacherInputDto.setBranch("branch");
        teacherInputDto.setId_person(1);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        assertThrows(Exception.class, ()->teacherServiceImpl.addTeacher(teacherInputDto));

        verify(personRepository, never()).save(any(Person.class));
        verify(teacherRepository, never()).save(any(Teacher.class));
    }

    @Test
    public void testCanNotAddTeacherWithNullTeacherIdField() {
        Person person = new Person();

        TeacherInputDto teacherInputDto = new TeacherInputDto(null, "comments", "branch", 1);
        Teacher teacher = new Teacher(teacherInputDto);

        teacher.setPerson(person);

        assertThrows(Exception.class, ()->teacherServiceImpl.addTeacher(teacherInputDto));
        verify(teacherRepository, never()).save(teacher);
    }

    @Test
    public void testCanNotAddTeacherWithNullCommentsField() {
        Person person = new Person();

        TeacherInputDto teacherInputDto = new TeacherInputDto(1, null, "branch", 1);
        Teacher teacher = new Teacher(teacherInputDto);

        teacher.setPerson(person);

        assertThrows(Exception.class, ()->teacherServiceImpl.addTeacher(teacherInputDto));
        verify(teacherRepository, never()).save(teacher);
    }

    @Test
    public void testCanNotAddTeacherWithNullBranchField() {
        Person person = new Person();

        TeacherInputDto teacherInputDto = new TeacherInputDto(1, "comments", null, 1);
        Teacher teacher = new Teacher(teacherInputDto);

        teacher.setPerson(person);

        assertThrows(Exception.class, ()->teacherServiceImpl.addTeacher(teacherInputDto));
        verify(teacherRepository, never()).save(teacher);
    }

    @Test
    public void testGetTeacherFullById() {
        int teacherId = 1;
        Teacher teacher = new Teacher();
        teacher.setId_teacher(teacherId);

        Person person = new Person();

        teacher.setStudentList(Collections.emptySet());
        teacher.setPerson(person);

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        TeacherOutputFullDto result = teacherServiceImpl.getTeacherById(teacherId);

        assertEquals(teacher.getId_teacher(), result.getId_teacher());
    }

    @Test
    public void testCanNotFindTeacherWithWrongId() {
        TeacherInputDto teacherInputDto = new TeacherInputDto(1, "comments", "branch", 1);
        Teacher teacher = new Teacher(teacherInputDto);

        teacher.setStudentList(Collections.emptySet());

        when(teacherRepository.findById(teacher.getId_teacher())).thenReturn(Optional.of(teacher));

        TeacherOutputFullDto result = teacherServiceImpl.getTeacherById(teacher.getId_teacher());

        assertNotEquals(2, result.getId_teacher());
    }

    @Test
    public void testDeleteTeacherById() {
        int teacherId = 1;
        Teacher teacher = new Teacher();
        teacher.setId_teacher(teacherId);
        Person person = new Person();
        teacher.setPerson(person);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        teacherServiceImpl.deleteTeacherById(teacher.getId_teacher());
        verify(teacherRepository, times(1)).deleteById(teacher.getId_teacher());
    }

    @Test
    public void testCanNotDeleteATeacherDoesNotExist() {

        int teacherId = 1;
        Teacher teacher = new Teacher();
        teacher.setId_teacher(teacherId);
        Person person = new Person();
        teacher.setPerson(person);
        given(teacherRepository.findById(teacher.getId_teacher())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->teacherServiceImpl.deleteTeacherById(teacher.getId_teacher()));
        verify(teacherRepository, never()).delete(teacher);
    }

    @Test
    public void testUpdateTeacherById() {

        Teacher teacher = new Teacher(new TeacherInputDto(1,"comments", "branch", 1));
        Person person = new Person();
        teacher.setPerson(person);
        TeacherInputDto teacherInputDto = new TeacherInputDto(1,"updated", "branch", 1);

        when(teacherRepository.findById(teacher.getId_teacher())).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        TeacherOutputFullDto teacherUpdated = teacherServiceImpl.updateTeacherById(teacherInputDto, teacher.getId_teacher());

        assertThat(teacherUpdated).isNotNull();
        assertEquals(teacherInputDto.getId_teacher(), teacherUpdated.getId_teacher());
    }

    @Test
    public void testCanNotUpdateTeacherWithWrongId() {
        Teacher teacher = new Teacher(new TeacherInputDto(1,"comments", "branch", 1));
        Person person = new Person();
        teacher.setPerson(person);
        TeacherInputDto teacherInputDto = new TeacherInputDto(2,"updated", "branch", 1);

        Assert.assertNotEquals(teacherInputDto.getId_teacher(), teacher.getId_teacher());
        assertThrows(Exception.class, ()->teacherServiceImpl.updateTeacherById(teacherInputDto, teacherInputDto.getId_teacher()));
        verify(teacherRepository, never()).save(teacher);
    }

    @Test
    public void testCanNotUpdateATeacherDoesNotExist() {
        Person person = new Person();
        Teacher teacher = new Teacher(new TeacherInputDto(1,"comments", "branch", 1));
        teacher.setPerson(person);
        TeacherInputDto teacherInputDto = new TeacherInputDto(null,"updated", "branch", 1);

        assertThrows(EntityNotFoundException.class, ()->teacherServiceImpl.updateTeacherById(teacherInputDto, 1));
        verify(teacherRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateTeacherWithNullCommentsField() {
        Teacher teacher = new Teacher(new TeacherInputDto(1,"comments", "branch", 1));
        Person person = new Person();
        teacher.setPerson(person);
        TeacherInputDto teacherInputDto = new TeacherInputDto(1,null, "branch", 1);

        assertThrows(Exception.class, ()->teacherServiceImpl.updateTeacherById(teacherInputDto, teacherInputDto.getId_teacher()));
        verify(teacherRepository, never()).save(teacher);
    }

    @Test
    public void testCanNotUpdateTeacherWithNullBranchField() {
        Teacher teacher = new Teacher(new TeacherInputDto(1,"comments", "branch", 1));
        Person person = new Person();
        teacher.setPerson(person);
        TeacherInputDto teacherInputDto = new TeacherInputDto(1,"updated", null, 1);

        assertThrows(Exception.class, ()->teacherServiceImpl.updateTeacherById(teacherInputDto, teacherInputDto.getId_teacher()));
        verify(teacherRepository, never()).save(teacher);
    }
}
