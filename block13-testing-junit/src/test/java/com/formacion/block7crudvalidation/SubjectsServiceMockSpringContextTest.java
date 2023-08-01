package com.formacion.block7crudvalidation;

import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.application.impl.StudentServiceImpl;
import com.formacion.block7crudvalidation.application.impl.SubjectsServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubjectsServiceMockSpringContextTest {
    @InjectMocks
    SubjectsServiceImpl subjectsServiceImpl;
    StudentServiceImpl studentServiceImpl;
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
    public void testAddSubject() {
        int teacherId = 1;
        Teacher teacher = new Teacher();
        teacher.setId_teacher(teacherId);

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto();
        subjectsInputDto.setSubject("subject");
        subjectsInputDto.setComments("comments");
        subjectsInputDto.setInitial_date(new Date());
        subjectsInputDto.setFinish_date(new Date());
        subjectsInputDto.setId_teacher(1);

        Subjects subject = new Subjects();
        subject.setId_subject(1);
        subject.setSubject(subjectsInputDto.getSubject());
        subject.setComments(subjectsInputDto.getComments());
        subject.setInitial_date(subjectsInputDto.getInitial_date());
        subject.setFinish_date(subjectsInputDto.getFinish_date());
        subject.setStudent(Collections.emptySet());
        subject.setTeacher(teacher);

        when(subjectsRepository.save(any(Subjects.class))).thenReturn(subject);
        SubjectsOutputDto result = subjectsServiceImpl.addSubject(subjectsInputDto);
        verify(subjectsRepository, times(1)).save(any(Subjects.class));
        assertEquals(subjectsInputDto.getSubject(), result.getSubject());
        assertEquals(subjectsInputDto.getComments(), result.getComments());
        assertEquals(subjectsInputDto.getInitial_date(), result.getInitial_date());
        assertEquals(subjectsInputDto.getFinish_date(), result.getFinish_date());

        SubjectsOutputDto subjectsOutputDto = subjectsServiceImpl.addSubject(subjectsInputDto);
        assertThat(subjectsOutputDto).isNotNull();

    }

    @Test
    public void testCanNotAddSubjectWithNullTeacherIdField() {
        int subjectId = 1;
        Teacher teacher = new Teacher();

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(null, "subject", "comments", new Date(), new Date());
        Subjects subject = new Subjects(subjectsInputDto);

        subject.setId_subject(subjectId);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        assertThrows(Exception.class, ()->subjectsServiceImpl.addSubject(subjectsInputDto));
        verify(subjectsRepository, never()).save(subject);
    }

    @Test
    public void testCanNotAddSubjectWithNullSubjectField() {
        int subjectId = 1;
        Teacher teacher = new Teacher();

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, null, "comments", new Date(), new Date());
        Subjects subject = new Subjects(subjectsInputDto);

        subject.setId_subject(subjectId);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        assertThrows(Exception.class, ()->subjectsServiceImpl.addSubject(subjectsInputDto));
        verify(subjectsRepository, never()).save(subject);
    }

    @Test
    public void testCanNotAddSubjectWithNullCommentsField() {
        int subjectId = 1;
        Teacher teacher = new Teacher();

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", null, new Date(), new Date());
        Subjects subject = new Subjects(subjectsInputDto);

        subject.setId_subject(subjectId);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        assertThrows(Exception.class, ()->subjectsServiceImpl.addSubject(subjectsInputDto));
        verify(subjectsRepository, never()).save(subject);
    }

    @Test
    public void testCanNotAddSubjectWithNullInitialDateField() {
        int subjectId = 1;
        Teacher teacher = new Teacher();

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", "comments", null, new Date());
        Subjects subject = new Subjects(subjectsInputDto);

        subject.setId_subject(subjectId);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        assertThrows(Exception.class, ()->subjectsServiceImpl.addSubject(subjectsInputDto));
        verify(subjectsRepository, never()).save(subject);
    }

    @Test
    public void testCanNotAddSubjectWithNullFinishDateField() {
        int subjectId = 1;
        Teacher teacher = new Teacher();

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", "comments", new Date(), null);
        Subjects subject = new Subjects(subjectsInputDto);

        subject.setId_subject(subjectId);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        assertThrows(Exception.class, ()->subjectsServiceImpl.addSubject(subjectsInputDto));
        verify(subjectsRepository, never()).save(subject);
    }

    @Test
    public void testAddSubjectToStudent() {
        int subjectId = 1;

        Teacher teacher = new Teacher();

        Set<Student> students = new HashSet<>();

        Student student1 = new Student();
        student1.setId_student(1);
        students.add(student1);

        Student student2 = new Student();
        student2.setId_student(2);
        students.add(student2);

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto();
        subjectsInputDto.setId_teacher(1);
        subjectsInputDto.setSubject("subject");
        subjectsInputDto.setComments("comments");
        subjectsInputDto.setInitial_date(new Date());
        subjectsInputDto.setFinish_date(new Date());

        Subjects subject = new Subjects();
        subject.setId_subject(subjectId);
        subject.setSubject(subjectsInputDto.getSubject());
        subject.setComments(subjectsInputDto.getComments());
        subject.setInitial_date(subjectsInputDto.getInitial_date());
        subject.setFinish_date(subjectsInputDto.getFinish_date());
        subject.setTeacher(teacher);

        Set<Subjects> subjects = new HashSet<>();
        subjects.add(subject);

        student1.setSubjects(subjects);
        student2.setSubjects(subjects);

        when(studentRepository.findById(student1.getId_student())).thenReturn(Optional.of(student1));
        when(subjectsRepository.findById(subject.getId_subject())).thenReturn(Optional.of(subject));

        subjectsServiceImpl.addSubjectsToStudent(1, 1);

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testGetSubjectById() {
        int subjectId = 1;
        Subjects subject = new Subjects();
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        when(subjectsRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        SubjectsOutputDto result = subjectsServiceImpl.getSubjectById(subjectId);

        assertEquals(subject.getId_subject(), result.getId_subject());
    }

    @Test
    public void testCanNotFindSubjectWithWrongId() {
        int subjectId = 1;
        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", "comments", new Date(), new Date());
        Subjects subject = new Subjects(subjectsInputDto);
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        when(subjectsRepository.findById(subject.getId_subject())).thenReturn(Optional.of(subject));

        SubjectsOutputDto result = subjectsServiceImpl.getSubjectById(subject.getId_subject());

        assertNotEquals(2, result.getId_subject());
    }

//    @Test // PENDIENTE
//    public void testGetAllSubjects() {
//        int pageNumber = 0;
//        int pageSize = 4;
//
//        Teacher teacher = new Teacher();
//
//        Subjects subject = new Subjects(1, new Teacher(), new HashSet<Student>(), "subject1", "comments", new Date(), new Date());
//
//        subject.setTeacher(teacher);
//        subject.setStudent(Collections.emptySet());
//
//        List<Subjects> subjectsList = Arrays.asList(
//                subject
//        );
//
//
//        Page<Subjects> subjectsPage = new PageImpl<>(subjectsList);
//
//        when(subjectsRepository.findAll(any(Pageable.class))).thenReturn(subjectsPage);
//
//        Iterable<SubjectsOutputDto> result = subjectsServiceImpl.getAllSubjects(pageNumber, pageSize);
//
//        List<SubjectsOutputDto> expected = subjectsList.stream()
//                .map(Subjects::subjectsToSubjectsOutputDto)
//                .collect(Collectors.toList());
//
//        assertEquals(expected, result);
//        verify(subjectsRepository).findAll(PageRequest.of(pageNumber, pageSize));
//    }

    @Test
    public void testDeleteSubjectById() {
        int subjectId = 1;
        Subjects subject = new Subjects();
        subject.setId_subject(subjectId);

        when(subjectsRepository.findById(subject.getId_subject())).thenReturn(Optional.of(subject));
        subjectsServiceImpl.deleteSubjectById(subject.getId_subject());
        verify(subjectsRepository, times(1)).deleteById(subject.getId_subject());
    }

    @Test
    public void testCanNotDeleteASubjectDoesNotExist() {

        int subjectId = 1;
        Subjects subject = new Subjects();
        subject.setId_subject(subjectId);

        given(subjectsRepository.findById(subjectId)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->subjectsServiceImpl.deleteSubjectById(subjectId));
        verify(subjectsRepository, never()).delete(subject);
    }

    @Test
    public void testUpdateSubjectById() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "update", "comments", new Date(), new Date());
        subjectsInputDto.setId_teacher(1);


        when(subjectsRepository.findById(subject.getId_subject())).thenReturn(Optional.of(subject));
        when(subjectsRepository.save(subject)).thenReturn(subject);

        SubjectsOutputDto subjectUpdated = subjectsServiceImpl.updateSubjectById(subjectsInputDto, subject.getId_subject());

        assertThat(subjectUpdated).isNotNull();
        assertEquals(subjectsInputDto.getId_teacher(), subjectUpdated.getId_teacher());
    }

    @Test
    public void testCanNotUpdateASubjectWithNullTeacherIdField() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(null, "update", "comments", new Date(), new Date());

        assertThrows(EntityNotFoundException.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateSubjectWithWrongTeacherId() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(2, "subject", "comments", new Date(), new Date());

        Assert.assertNotEquals(subjectsInputDto.getId_teacher(), subject.getTeacher().getId_teacher());
        assertThrows(Exception.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateASubjectWithNullSubjectField() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, null, "comments", new Date(), new Date());

        assertThrows(Exception.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateASubjectWithNullCommentsField() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", null, new Date(), new Date());

        assertThrows(Exception.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateASubjectWithNullInitialDateField() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", "comments", null, new Date());

        assertThrows(Exception.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateASubjectWithNullFinishDateField() {
        int subjectId = 1;
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setId_subject(subjectId);

        Teacher teacher = new Teacher();
        teacher.setId_teacher(1);
        subject.setTeacher(teacher);
        subject.setStudent(Collections.emptySet());

        SubjectsInputDto subjectsInputDto = new SubjectsInputDto(1, "subject", "comments", new Date(), null);

        assertThrows(Exception.class, ()->subjectsServiceImpl.updateSubjectById(subjectsInputDto, 1));
        verify(subjectsRepository, never()).save(any());
    }

    @Test
    public void testGetSubjectListByStudent() {
        Student student = new Student();
        student.setId_student(1);
        Teacher teacher = new Teacher();

        Subjects subject1 = new Subjects();
        subject1.setId_subject(1);
        subject1.setSubject("subject1");
        subject1.setComments("comments");
        subject1.setInitial_date(new Date());
        subject1.setFinish_date(new Date());
        subject1.setStudent(Set.of(student));
        subject1.setTeacher(teacher);

        Subjects subject2 = new Subjects();
        subject2.setId_subject(2);
        subject2.setSubject("subject2");
        subject2.setComments("comments");
        subject2.setInitial_date(new Date());
        subject2.setFinish_date(new Date());
        subject1.setStudent(Set.of(student));
        subject2.setTeacher(teacher);

        Set<Subjects> subjects = new HashSet<>();
        subjects.add(subject1);
        subjects.add(subject2);
        student.setSubjects(subjects);

        when(studentRepository.findById(student.getId_student())).thenReturn(Optional.of(student));
        when(subjectsRepository.findByIdStudent(student.getId_student())).thenReturn(subjects);

        StudentOutputSimpleDto result = subjectsServiceImpl.getSubjectsListByStudent(1);

        assertEquals(student.getId_student(), result.getId_student());

        Set<SubjectsOutputDto> subjectsOutputDto = result.getSubjects();
        assertEquals(2, subjectsOutputDto.size());

        assertTrue(subjectsOutputDto.stream().anyMatch(s -> s.getSubject().equals(subject1.getSubject())));
        assertTrue(subjectsOutputDto.stream().anyMatch(s -> s.getSubject().equals(subject2.getSubject())));
    }

    @Test
    public void testCanNotGetSubjectListByStudentIfStudentDoesNotExist() {
        Student student = new Student();
        student.setId_student(2);

        when(studentRepository.findById(student.getId_student())).thenReturn(Optional.empty());

        assertThrows(Exception.class, ()->subjectsServiceImpl.getSubjectsListByStudent(student.getId_student()));
    }
}
