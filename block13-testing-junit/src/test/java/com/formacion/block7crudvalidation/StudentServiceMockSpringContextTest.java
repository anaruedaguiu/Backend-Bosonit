package com.formacion.block7crudvalidation;

import com.formacion.block7crudvalidation.application.StudentService;
import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.application.impl.SubjectsServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.PersonOutputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.StudentOutputFullDto;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceMockSpringContextTest {
    @InjectMocks
    StudentServiceImpl studentServiceImpl;
    SubjectsServiceImpl subjectsServiceImpl;
    Person p;
    @Mock
    PersonRepository personRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    SubjectsRepository subjectsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        p = new Person(new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));

    }

    @Test
    public void testAddStudent() throws Exception {
        int id = 1;
        Person person = new Person();
        person.setId(id);

        StudentInputDto studentInputDto = new StudentInputDto();
        studentInputDto.setId_student(1);
        studentInputDto.setNum_hours_week(20);
        studentInputDto.setComments("comments");
        studentInputDto.setBranch("branch");
        studentInputDto.setId_person(1);

        Student student = new Student();
        student.setId_student(2);
        student.setNum_hours_week(studentInputDto.getNum_hours_week());
        student.setComments(studentInputDto.getComments());
        student.setBranch(studentInputDto.getBranch());
        student.setPerson(person);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Optional<StudentOutputFullDto> resultOptional = studentServiceImpl.addStudentFull(studentInputDto);
        assertThat(resultOptional).isNotNull();
        StudentOutputFullDto result = resultOptional.get();

        assertEquals(student.getId_student(), result.getId_student());
        assertEquals(student.getNum_hours_week(), result.getNum_hours_week());
        assertEquals(student.getComments(), result.getComments());
        assertEquals(student.getBranch(), result.getBranch());

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testCanNotAddStudentIfPersonHasRoleAssigned() {
        int id = 1;
        Person person = new Person();
        person.setId(id);
        person.setRole("Teacher");

        StudentInputDto studentInputDto = new StudentInputDto();
        studentInputDto.setId_student(1);
        studentInputDto.setNum_hours_week(20);
        studentInputDto.setComments("comments");
        studentInputDto.setBranch("branch");
        studentInputDto.setId_person(1);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        assertThrows(Exception.class, ()->studentServiceImpl.addStudentFull(studentInputDto));

        verify(personRepository, never()).save(any(Person.class));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testCanNotAddStudentWithNullStudentIdField() {
        Person person = new Person();

        StudentInputDto studentInputDto = new StudentInputDto(null, 20, "comments", "branch", 1);
        Student student = new Student(studentInputDto);

        student.setPerson(person);

        assertThrows(Exception.class, ()->studentServiceImpl.addStudentFull(studentInputDto));
        verify(studentRepository, never()).save(student);
    }

    @Test
    public void testCanNotAddStudentWithNullNumHoursWeekField() {
        Person person = new Person();

        StudentInputDto studentInputDto = new StudentInputDto(1, null, "comments", "branch", 1);
        Student student = new Student(studentInputDto);

        student.setPerson(person);

        assertThrows(Exception.class, ()->studentServiceImpl.addStudentFull(studentInputDto));
        verify(studentRepository, never()).save(student);
    }

    @Test
    public void testCanNotAddStudentWithNullCommentsField() {
        Person person = new Person();

        StudentInputDto studentInputDto = new StudentInputDto(1, 20, null, "branch", 1);
        Student student = new Student(studentInputDto);

        student.setPerson(person);

        assertThrows(Exception.class, ()->studentServiceImpl.addStudentFull(studentInputDto));
        verify(studentRepository, never()).save(student);
    }

    @Test
    public void testCanNotAddStudentWithNullBranchField() {
        Person person = new Person();

        StudentInputDto studentInputDto = new StudentInputDto(1, 20, "comments", null, 1);
        Student student = new Student(studentInputDto);

        student.setPerson(person);

        assertThrows(Exception.class, ()->studentServiceImpl.addStudentFull(studentInputDto));
        verify(studentRepository, never()).save(student);
    }

    @Test
    public void testGetStudentSimpleById() {
        int studentId = 1;
        Student student = new Student();
        student.setId_student(studentId);

        student.setSubjects(Collections.emptySet());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        StudentOutputSimpleDto result = studentServiceImpl.getStudentSimpleById(studentId);

        assertEquals(student.getId_student(), result.getId_student());
    }

    @Test
    public void testGetStudentFullById() {
        int studentId = 1;
        Student student = new Student();
        student.setId_student(studentId);

        Person person = new Person();

        student.setSubjects(Collections.emptySet());
        student.setPerson(person);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        StudentOutputFullDto result = studentServiceImpl.getStudentFullById(studentId);

        assertEquals(student.getId_student(), result.getId_student());
    }

    @Test
    public void testCanNotFindStudentSimpleWithWrongId() {
        StudentInputDto studentInputDto = new StudentInputDto(1, 20, "comments", "branch", 1);
        Student student = new Student(studentInputDto);

        student.setSubjects(Collections.emptySet());

        when(studentRepository.findById(student.getId_student())).thenReturn(Optional.of(student));

        StudentOutputSimpleDto result = studentServiceImpl.getStudentSimpleById(student.getId_student());

        assertNotEquals(2, result.getId_student());
    }

    @Test
    public void testCanNotFindStudentFullWithWrongId() {
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentServiceImpl.getStudentFullById(studentId));
        verify(studentRepository, never()).save(any(Student.class));
    }


    // ______PROBLEMA: la lista studentList son students, la que devuelve studentOutput como es full tiene students y las personas a las que están asignados
//    @Test // PENDIENTE
//    public void testGetAllStudents() {
//        int pageNumber = 0;
//        int pageSize = 4;
//
//        Person person = new Person(new PersonInputDto(3,"person1", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
//                "city", true, new Date() , "image-000", new Date()));
//
//        Student student = new Student(1, person, 20, "comments", "branch", new Teacher(), new HashSet<>());
//
//        List<Student> studentList = Arrays.asList(
//                student
//        );
//
//        //Person person1 = new Person();
//        person.setStudent(student);
//
//        //studentList.get(0).setPerson(person1);
//
//        Page<Student> studentPage = new PageImpl<>(studentList);
//
//        when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);
//
//        Iterable<StudentOutputFullDto> result = studentServiceImpl.getAllStudents(pageNumber, pageSize);
//
//        List<StudentOutputFullDto> expected = Arrays.asList(
//                student.studentToStudentOutputFullDto()
//        );
//
//        assertThat(result).isEqualTo(expected);
//        verify(studentRepository).findAll(PageRequest.of(pageNumber, pageSize));
//    }

    @Test
    public void testDeleteStudentById() {
        Student student = new Student();
        Person person = new Person();
        student.setPerson(person);
        when(studentRepository.findById(student.getId_student())).thenReturn(Optional.of(student));
        studentServiceImpl.deleteStudentById(student.getId_student());
        verify(studentRepository, times(1)).deleteById(student.getId_student());
    }

    @Test
    public void testCanNotDeleteAStudentDoesNotExist() {

        Student student = new Student();
        Person person = new Person();
        student.setPerson(person);
        given(studentRepository.findById(student.getId_student())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->studentServiceImpl.deleteStudentById(student.getId_student()));
        verify(studentRepository, never()).delete(student);
    }

    @Test
    public void testUpdateStudentById() {
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        Person person = new Person();
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(1, 20, "updated", "branch", 1);

        when(studentRepository.findById(student.getId_student())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        StudentOutputFullDto studentUpdated = studentServiceImpl.updateStudentFullById(studentInputDto, student.getId_student());

        assertThat(studentUpdated).isNotNull();
        assertEquals(studentInputDto.getId_student(), studentUpdated.getId_student());
    }

    @Test
    public void testCanNotUpdateStudentWithWrongId() {
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        Person person = new Person();
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(2, 20, "updated", "branch", 1);

        Assert.assertNotEquals(studentInputDto.getId_student(), student.getId_student());
        assertThrows(Exception.class, ()->studentServiceImpl.updateStudentFullById(studentInputDto, studentInputDto.getId_student()));
        verify(studentRepository, never()).save(student);
    }

    @Test
    public void testCanNotUpdateAStudentDoesNotExist() {
        Person person = new Person();
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(null, 20, "updated", "branch", 1);

        assertThrows(EntityNotFoundException.class, ()->studentServiceImpl.updateStudentFullById(studentInputDto, 1));
        verify(studentRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateAStudentWithNullNumHoursWeekField() {
        Person person = new Person();
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(1, null, "updated", "branch", 1);

        assertThrows(EntityNotFoundException.class, ()->studentServiceImpl.updateStudentFullById(studentInputDto, 1));
        verify(studentRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateAStudentWithNullCommentsField() {
        Person person = new Person();
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(1, 20, null, "branch", 1);

        assertThrows(EntityNotFoundException.class, ()->studentServiceImpl.updateStudentFullById(studentInputDto, 1));
        verify(studentRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateAStudentWithNullBranchField() {
        Person person = new Person();
        Student student = new Student(new StudentInputDto(1, 20, "comments", "branch", 1));
        student.setPerson(person);
        StudentInputDto studentInputDto = new StudentInputDto(1, 20, "updated", null, 1);

        assertThrows(EntityNotFoundException.class, ()->studentServiceImpl.updateStudentFullById(studentInputDto, 1));
        verify(studentRepository, never()).save(any());
    }

//    @Test // PENDIENTE
//    public void testAddSubjectListToStudent() throws Exception {
//        int studentId = 1;
//        List<Integer> subjectIds = List.of(1, 2, 3); // IDs de asignaturas a agregar al estudiante
//
//        // Creamos un estudiante con ID 1 y un conjunto vacío de asignaturas
//        Student student = new Student();
//        student.setId_student(studentId);
//        student.setSubjects(new HashSet<>());
//
//        // Creamos tres asignaturas con los IDs especificados
//        Subjects subject1 = new Subjects();
//        subject1.setId_subject(1);
//        Subjects subject2 = new Subjects();
//        subject2.setId_subject(2);
//        Subjects subject3 = new Subjects();
//        subject3.setId_subject(3);
//
//        // Configuramos el comportamiento de los mocks
//        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
//        when(subjectsRepository.findById(1)).thenReturn(Optional.of(subject1));
//        when(subjectsRepository.findById(2)).thenReturn(Optional.of(subject2));
//        when(subjectsRepository.findById(3)).thenReturn(Optional.of(subject3));
//
//        // Ejecutamos el método que queremos probar
//        StudentOutputSimpleDto result = subjectsServiceImpl.addSubjectsToStudent(studentId, subjectIds);
//
//
//        // Verificamos que el método ha devuelto correctamente el estudiante actualizado
//        assertEquals(student.getId_student(), result.getId_student());
//        assertEquals(subjectIds.size(), result.getSubjects().size());
//    }
}
