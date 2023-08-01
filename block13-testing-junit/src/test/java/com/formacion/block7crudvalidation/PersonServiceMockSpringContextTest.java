package com.formacion.block7crudvalidation;

import com.formacion.block7crudvalidation.application.impl.PersonServiceImpl;
import com.formacion.block7crudvalidation.controllers.dto.input.PersonInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.StudentInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.SubjectsInputDto;
import com.formacion.block7crudvalidation.controllers.dto.input.TeacherInputDto;
import com.formacion.block7crudvalidation.controllers.dto.output.*;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceMockSpringContextTest {
    @InjectMocks
    PersonServiceImpl personServiceImpl;
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
    public void testAddPerson() throws Exception {
        PersonInputDto personInputDto = new PersonInputDto();
        personInputDto.setUsername("usuario");
        personInputDto.setPassword("1234");
        personInputDto.setName("Ana");
        personInputDto.setSurname("Rueda");
        personInputDto.setCompany_email("company@email.com");
        personInputDto.setPersonal_email("personal@email.com");
        personInputDto.setCity("ciudad");
        personInputDto.setActive(true);
        personInputDto.setCreated_date(new Date());
        personInputDto.setImage_url("imagen-prueba-url");
        personInputDto.setTermination_date(new Date());

        Person person = new Person();
        person.setId(1);
        person.setUsername(personInputDto.getUsername());
        person.setPassword(personInputDto.getPassword());
        person.setName(personInputDto.getName());
        person.setSurname(personInputDto.getSurname());
        person.setCompany_email(personInputDto.getCompany_email());
        person.setPersonal_email(personInputDto.getPersonal_email());
        person.setCity(personInputDto.getCity());
        person.setActive(personInputDto.getActive());
        person.setCreated_date(personInputDto.getCreated_date());
        person.setImage_url(personInputDto.getImage_url());
        person.setTermination_date(personInputDto.getTermination_date());

        when(personRepository.save(any(Person.class))).thenReturn(person);
        PersonOutputDto result = personServiceImpl.addPerson(personInputDto);
        verify(personRepository, times(1)).save(any(Person.class));
        assertEquals(1, result.getId());
        assertEquals(personInputDto.getUsername(), result.getUsername());
        assertEquals(personInputDto.getPassword(), result.getPassword());
        assertEquals(personInputDto.getName(), result.getName());
        assertEquals(personInputDto.getSurname(), result.getSurname());
        assertEquals(personInputDto.getCompany_email(), result.getCompany_email());
        assertEquals(personInputDto.getPersonal_email(), result.getPersonal_email());
        assertEquals(personInputDto.getCity(), result.getCity());
        assertEquals(personInputDto.getActive(), result.getActive());
        assertEquals(personInputDto.getCreated_date(), result.getCreated_date());
        assertEquals(personInputDto.getImage_url(), result.getImage_url());
        assertEquals(personInputDto.getTermination_date(), result.getTermination_date());

        PersonOutputDto personOutputDto = personServiceImpl.addPerson(personInputDto);
        assertThat(personOutputDto).isNotNull();
    }

    @Test
    public void testCanNotAddPersonWithNullUsernameField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,null, "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithBadFormatUsernameField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"user", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullPasswordField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", null, "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullNameField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", null, "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullCompanyEmailField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", null, "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullPersonalEmailField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", null,
                "city", true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullCityField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                null, true, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullActiveField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", null, new Date() , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testCanNotAddPersonWithNullCreatedDateField() throws UnprocessableEntityException {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, null , "image-000", new Date());
        Person p = new Person(personInputDto);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.addPerson(personInputDto));
        verify(personRepository, never()).save(p);
    }

    @Test
    public void testGetPersonById() {
        Person p = new Person();

        when(personRepository.findById(p.getId())).thenReturn(Optional.of(p));

        PersonOutputDto result = personServiceImpl.getPersonById(p.getId());

        assertEquals(p.getId(), result.getId());
    }

    @Test
    public void testCanNotFindPersonWithNullId() {
        PersonInputDto personInputDto = new PersonInputDto();
        int id = 123;
        Person p = new Person(personInputDto);
        p.setId(id);

        given(personRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(Exception.class, ()->personServiceImpl.getPersonById(id));
    }

    @Test
    public void testCanNotFindPersonWithWrongId() {
        PersonInputDto personInputDto = new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, null , "image-000", new Date());
        Person p = new Person(personInputDto);

        when(personRepository.findById(p.getId())).thenReturn(Optional.of(p));

        PersonOutputDto result = personServiceImpl.getPersonById(p.getId());

        assertNotEquals(2, result.getId());
    }

    @Test
    public void testGetPersonByUsername() {
        Person p = new Person();
        p.setUsername("usuario");

        when(personRepository.findByUsername(p.getUsername())).thenReturn(Optional.of(p));

        PersonOutputDto result = personServiceImpl.getPersonByUsername(p.getUsername());

        assertEquals(p.getUsername(), result.getUsername());
    }

    @Test
    public void testGetPersonAndRoleByIdWithSimpleOutputType() {
        //Test para persona cuando no tiene asignado ningún estudiante o profesor
        int id = 1;
        Person p = new Person(new PersonInputDto(1,"hola", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        p.setId(id);
        String outputType = "simple";

        //Obtiene la persona por su id
        given(personRepository.findById(id)).willReturn(Optional.of(p));

        Object result = personServiceImpl.getPersonAndRoleById(id, outputType);

        //Comprobar que se devuelve la persona como se espera
        assertTrue(result instanceof PersonOutputDto);
        PersonOutputDto personOutputDto = (PersonOutputDto) result;
        //Se comprueban los campos
        assertEquals(p.getId(), personOutputDto.getId());
        assertEquals(p.getUsername(), personOutputDto.getUsername());
        assertEquals(p.getPassword(), personOutputDto.getPassword());
        assertEquals(p.getName(), personOutputDto.getName());
        assertEquals(p.getSurname(), personOutputDto.getSurname());
        assertEquals(p.getCompany_email(), personOutputDto.getCompany_email());
        assertEquals(p.getPersonal_email(), personOutputDto.getPersonal_email());
        assertEquals(p.getCity(), personOutputDto.getCity());
        assertEquals(p.getActive(), personOutputDto.getActive());
        assertEquals(p.getCreated_date(), personOutputDto.getCreated_date());
        assertEquals(p.getImage_url(), personOutputDto.getImage_url());
        assertEquals(p.getTermination_date(), personOutputDto.getTermination_date());

        //Asegurar que no se hace ninguna llamada a los repositorios de estudiante y profesor
        verify(studentRepository, never()).findByPerson(any(Person.class));
        verify(teacherRepository, never()).findByPerson(any(Person.class));
    }

    @Test
    public void testGetPersonAndRoleByIdWithFullOutputTypeAndStudentRole() {
        // Test para persona cuando es estudiante y el valor de outputType es full
        int personId = 1;
        Person p = new Person(new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        Person person2 = new Person(new PersonInputDto(2,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        p.setId(personId);
        String outputType = "full";
        String role = "student";
        p.setRole(role);

        // Se crea una instancia de student y se asigna a la persona creada anteriormente
        Student student = new Student(new StudentInputDto(1, 10, "comments", "branch", 1));
        student.setPerson(p);
        Teacher teacher = new Teacher(new TeacherInputDto(1, "comments", "branch", 2));
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setTeacher(teacher);
        Set<Subjects> subjectsSet = new HashSet<>();
        subjectsSet.add(subject);
        student.setSubjects(subjectsSet);
        p.setStudent(student);

        StudentOutputSimpleDto studentOutputSimpleDto = student.studentToStudentOutputSimpleDto();

        // Se crea instancia de PersonStudentOutputDto
        PersonStudentOutputDto personStudentOutputDto = new PersonStudentOutputDto();
        personStudentOutputDto.setId(p.getId());
        personStudentOutputDto.setUsername(p.getUsername());
        personStudentOutputDto.setPassword(p.getPassword());
        personStudentOutputDto.setName(p.getName());
        personStudentOutputDto.setSurname(p.getSurname());
        personStudentOutputDto.setCompany_email(p.getCompany_email());
        personStudentOutputDto.setPersonal_email(p.getPersonal_email());
        personStudentOutputDto.setCity(p.getCity());
        personStudentOutputDto.setActive(p.getActive());
        personStudentOutputDto.setCreated_date(p.getCreated_date());
        personStudentOutputDto.setImage_url(p.getImage_url());
        personStudentOutputDto.setTermination_date(p.getTermination_date());
        personStudentOutputDto.setRole(p.getRole());
        personStudentOutputDto.setStudent(studentOutputSimpleDto);

        // Obtiene la persona por su id y el estudiante asignado a esa persona
        given(personRepository.findById(personId)).willReturn(Optional.of(p));
        given(studentRepository.findByPerson(p)).willReturn(student);

        Object result = personServiceImpl.getPersonAndRoleById(personId, outputType);

        // Comprueba que se devuelve el tipo de objeto esperado, en este caso: PersonStudentOutputDto
        assertTrue(result instanceof PersonStudentOutputDto);
        //Comprueba los campos
        PersonStudentOutputDto studentOutput = (PersonStudentOutputDto) result;

        assertEquals(personStudentOutputDto.getId(), studentOutput.getId());
        assertEquals(personStudentOutputDto.getUsername(), studentOutput.getUsername());
        assertEquals(personStudentOutputDto.getPassword(), studentOutput.getPassword());
        assertEquals(personStudentOutputDto.getName(), studentOutput.getName());
        assertEquals(personStudentOutputDto.getSurname(), studentOutput.getSurname());
        assertEquals(personStudentOutputDto.getCompany_email(), studentOutput.getCompany_email());
        assertEquals(personStudentOutputDto.getPersonal_email(), studentOutput.getPersonal_email());
        assertEquals(personStudentOutputDto.getCity(), studentOutput.getCity());
        assertEquals(personStudentOutputDto.getActive(), studentOutput.getActive());
        assertEquals(personStudentOutputDto.getCreated_date(), studentOutput.getCreated_date());
        assertEquals(personStudentOutputDto.getImage_url(), studentOutput.getImage_url());
        assertEquals(personStudentOutputDto.getTermination_date(), studentOutput.getTermination_date());
        assertEquals(personStudentOutputDto.getRole(), studentOutput.getRole());
        assertEquals(personStudentOutputDto.getStudent().getId_student(), studentOutput.getStudent().getId_student());
        assertEquals(personStudentOutputDto.getStudent().getNum_hours_week(), studentOutput.getStudent().getNum_hours_week());
        assertEquals(personStudentOutputDto.getStudent().getComments(), studentOutput.getStudent().getComments());
        assertEquals(personStudentOutputDto.getStudent().getBranch(), studentOutput.getStudent().getBranch());

        //Asegurar que no se hace ninguna llamada al repositorio de profesor
        verify(teacherRepository, never()).findByPerson(any(Person.class));
    }

    @Test
    public void testGetPersonAndRoleByIdWithFullOutputTypeAndTeacherRole() {
        // Test para persona cuando es profesor y el valor de outputType es full
        int personId = 1;
        Person p = new Person(new PersonInputDto(1,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        Person person2 = new Person(new PersonInputDto(2,"usuario", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        p.setId(personId);
        String outputType = "full";
        String role = "teacher";
        p.setRole(role);

        // Se crea una instancia de teacher y se asigna a la persona creada anteriormente
        Teacher teacher = new Teacher(new TeacherInputDto(1, "comments", "branch", 1));
        teacher.setPerson(p);
        Student student = new Student(new StudentInputDto(1, 10, "comments", "branch", 2));
        Subjects subject = new Subjects(new SubjectsInputDto(1, "subject", "comments", new Date(), new Date()));
        subject.setTeacher(teacher);
        Set<Subjects> subjectsSet = new HashSet<>();
        subjectsSet.add(subject);
        student.setSubjects(subjectsSet);
        p.setTeacher(teacher);

        TeacherOutputSimpleDto teacherOutputSimpleDto = teacher.teacherToTeacherOutputSimpleDto();

        // Se crea instancia de PersonTeacherOutputDto
        PersonTeacherOutputDto personTeacherOutputDto = new PersonTeacherOutputDto();
        personTeacherOutputDto.setId(p.getId());
        personTeacherOutputDto.setUsername(p.getUsername());
        personTeacherOutputDto.setPassword(p.getPassword());
        personTeacherOutputDto.setName(p.getName());
        personTeacherOutputDto.setSurname(p.getSurname());
        personTeacherOutputDto.setCompany_email(p.getCompany_email());
        personTeacherOutputDto.setPersonal_email(p.getPersonal_email());
        personTeacherOutputDto.setCity(p.getCity());
        personTeacherOutputDto.setActive(p.getActive());
        personTeacherOutputDto.setCreated_date(p.getCreated_date());
        personTeacherOutputDto.setImage_url(p.getImage_url());
        personTeacherOutputDto.setTermination_date(p.getTermination_date());
        personTeacherOutputDto.setRole(p.getRole());
        personTeacherOutputDto.setTeacher(teacherOutputSimpleDto);

        // Obtiene la persona por su id y el profesor asignado a esa persona
        given(personRepository.findById(personId)).willReturn(Optional.of(p));
        given(teacherRepository.findByPerson(p)).willReturn(teacher);

        Object result = personServiceImpl.getPersonAndRoleById(personId, outputType);

        // Comprueba que se devuelve el tipo de objeto esperado, en este caso: PersonTeacherOutputDto
        assertTrue(result instanceof PersonTeacherOutputDto);
        //Comprueba los campos
        PersonTeacherOutputDto teacherOutput = (PersonTeacherOutputDto) result;

        assertEquals(personTeacherOutputDto.getId(), teacherOutput.getId());
        assertEquals(personTeacherOutputDto.getUsername(), teacherOutput.getUsername());
        assertEquals(personTeacherOutputDto.getPassword(), teacherOutput.getPassword());
        assertEquals(personTeacherOutputDto.getName(), teacherOutput.getName());
        assertEquals(personTeacherOutputDto.getSurname(), teacherOutput.getSurname());
        assertEquals(personTeacherOutputDto.getCompany_email(), teacherOutput.getCompany_email());
        assertEquals(personTeacherOutputDto.getPersonal_email(), teacherOutput.getPersonal_email());
        assertEquals(personTeacherOutputDto.getCity(), teacherOutput.getCity());
        assertEquals(personTeacherOutputDto.getActive(), teacherOutput.getActive());
        assertEquals(personTeacherOutputDto.getCreated_date(), teacherOutput.getCreated_date());
        assertEquals(personTeacherOutputDto.getImage_url(), teacherOutput.getImage_url());
        assertEquals(personTeacherOutputDto.getTermination_date(), teacherOutput.getTermination_date());
        assertEquals(personTeacherOutputDto.getRole(), teacherOutput.getRole());
        assertEquals(personTeacherOutputDto.getTeacher().getId_teacher(), teacherOutput.getTeacher().getId_teacher());
        assertEquals(personTeacherOutputDto.getTeacher().getBranch(), teacherOutput.getTeacher().getBranch());
        assertEquals(personTeacherOutputDto.getTeacher().getComments(), teacherOutput.getTeacher().getComments());

        //Asegurar que no se hace ninguna llamada al repositorio de profesor
        verify(studentRepository, never()).findByPerson(any(Person.class));
    }

    @Test
    public void testGetAllPersons() {
        int pageNumber = 0;
        int pageSize = 4;

        List<Person> personList = Arrays.asList(
                new Person(new PersonInputDto(3,"person1", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                        "city", true, new Date() , "image-000", new Date())),
                new Person(new PersonInputDto(4,"person2", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                        "city", true, new Date() , "image-000", new Date()))
        );

        Page<Person> personaPage = new PageImpl<>(personList);

        when(personRepository.findAll(any(Pageable.class))).thenReturn(personaPage);

        Iterable<PersonOutputDto> result = personServiceImpl.getAllPersons(pageNumber, pageSize);

        List<PersonOutputDto> expected = Arrays.asList(
                personList.get(0).personToPersonOutputDto(),
                personList.get(1).personToPersonOutputDto()
        );

        assertEquals(expected, result);
        verify(personRepository).findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Test
    public void testDeletePersonById() {

        Person p = new Person();
        when(personRepository.findById(p.getId())).thenReturn(Optional.of(p));
        personServiceImpl.deletePerson(p.getId());
        verify(personRepository, times(1)).deleteById(p.getId());
    }

    @Test
    public void testCanNotDeleteAPersonDoesNotExist() {

        int id = 1;
        Person p = new Person();
        p.setId(id);
        given(personRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->personServiceImpl.deletePerson(id));
        verify(personRepository, never()).delete(p);
    }

    @Test
    public void testUpdatePersonById() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        PersonOutputDto personUpdated = personServiceImpl.updatePerson(personInputDto, person.getId());

        assertThat(personUpdated).isNotNull();
        assertEquals(personInputDto.getId(), personUpdated.getId());
    }

    @Test
    public void testCanNotUpdateWithNullUsernameField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,null, "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setUsername(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullPasswordField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", null, "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setPassword(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullNameField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", null, "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setName(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullCompanyEmailField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", null, "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setCompany_email(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullPersonalEmailField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", "company_email@email.com", null,
                "city", true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setPersonal_email(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullCityField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                null, true, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setCity(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullActiveField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", null, new Date() , "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setActive(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdateWithNullCreatedDateField() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(1,"updated", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, null, "image-000", new Date());

        given(personRepository.findById(person.getId())).willReturn(Optional.of(person));

        personInputDto.setCreated_date(null);

        assertThrows(UnprocessableEntityException.class, ()->personServiceImpl.updatePerson(personInputDto, 1));
        verify(personRepository, never()).save(any());
    }

    @Test
    public void testCanNotUpdatePersonWithWrongId() {
        Person person = new Person(new PersonInputDto(1,"persona", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date()));
        PersonInputDto personInputDto = new PersonInputDto(2,"updated", "password", "name", "surname", "company_email@email.com", "personal_email@email.com",
                "city", true, new Date() , "image-000", new Date());

        assertNotEquals(personInputDto.getId(), person.getId());
        assertThrows(Exception.class, ()->personServiceImpl.updatePerson(personInputDto, personInputDto.getId()));
        verify(personRepository, never()).save(person);
    }
}
