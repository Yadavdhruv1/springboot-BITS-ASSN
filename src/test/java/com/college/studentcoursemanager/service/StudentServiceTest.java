package com.college.studentcoursemanager.service;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.entity.Student;
import com.college.studentcoursemanager.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentService.
 * Repository calls are mocked using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepo;

    @InjectMocks
    private StudentService studentService;

    private Student sampleStudent;
    private Course sampleCourse;

    @BeforeEach
    void init() {
        sampleCourse = new Course("Data Structures", "CS201", "Computer Science", 4, "DSA basics");
        sampleCourse.setId(1L);

        sampleStudent = new Student("Aarav Sharma", "aarav@uni.in", "2024CS001", 3, sampleCourse);
        sampleStudent.setId(1L);
    }

    @Test
    @DisplayName("listAll should return list of all students")
    void listAll_shouldReturnStudents() {
        Student another = new Student("Meera Patel", "meera@uni.in", "2024CS002", 5, sampleCourse);
        another.setId(2L);

        when(studentRepo.findAll()).thenReturn(Arrays.asList(sampleStudent, another));

        List<Student> students = studentService.listAll();

        assertEquals(2, students.size());
        assertEquals("Aarav Sharma", students.get(0).getName());
        verify(studentRepo).findAll();
    }

    @Test
    @DisplayName("getById should return student when found")
    void getById_existingId_shouldReturnStudent() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Student found = studentService.getById(1L);

        assertNotNull(found);
        assertEquals("2024CS001", found.getRollNumber());
    }

    @Test
    @DisplayName("getById should throw for missing student")
    void getById_missingId_shouldThrow() {
        when(studentRepo.findById(50L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.getById(50L));

        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    @DisplayName("addStudent should succeed with unique email and roll number")
    void addStudent_uniqueData_shouldSave() {
        Student newStud = new Student("Karan Gupta", "karan@uni.in", "2024CS003", 5, sampleCourse);

        when(studentRepo.existsByEmail("karan@uni.in")).thenReturn(false);
        when(studentRepo.existsByRollNumber("2024CS003")).thenReturn(false);
        when(studentRepo.save(newStud)).thenReturn(newStud);

        Student saved = studentService.addStudent(newStud);

        assertNotNull(saved);
        assertEquals("Karan Gupta", saved.getName());
        verify(studentRepo).save(newStud);
    }

    @Test
    @DisplayName("addStudent should throw on duplicate email")
    void addStudent_duplicateEmail_shouldThrow() {
        Student dup = new Student("Test User", "aarav@uni.in", "2024CS099", 3, sampleCourse);

        when(studentRepo.existsByEmail("aarav@uni.in")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.addStudent(dup));

        assertTrue(ex.getMessage().contains("already registered"));
        verify(studentRepo, never()).save(any());
    }

    @Test
    @DisplayName("addStudent should throw on duplicate roll number")
    void addStudent_duplicateRoll_shouldThrow() {
        Student dup = new Student("Test User", "new@uni.in", "2024CS001", 3, sampleCourse);

        when(studentRepo.existsByEmail("new@uni.in")).thenReturn(false);
        when(studentRepo.existsByRollNumber("2024CS001")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.addStudent(dup));

        assertTrue(ex.getMessage().contains("already taken"));
        verify(studentRepo, never()).save(any());
    }

    @Test
    @DisplayName("updateStudent should modify existing student and save")
    void updateStudent_shouldUpdateAndSave() {
        Student updatedData = new Student("Aarav S.", "aarav@uni.in", "2024CS001", 4, sampleCourse);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepo.save(any(Student.class))).thenReturn(sampleStudent);

        Student result = studentService.updateStudent(1L, updatedData);

        assertNotNull(result);
        verify(studentRepo).findById(1L);
        verify(studentRepo).save(any(Student.class));
    }

    @Test
    @DisplayName("getStudentsWithCourseInfo should use inner join query")
    void getStudentsWithCourseInfo_shouldCallCustomQuery() {
        when(studentRepo.findAllStudentsWithCourses())
                .thenReturn(Collections.singletonList(sampleStudent));

        List<Student> results = studentService.getStudentsWithCourseInfo();

        assertEquals(1, results.size());
        assertNotNull(results.get(0).getCourse());
        verify(studentRepo).findAllStudentsWithCourses();
    }

    @Test
    @DisplayName("getByRollNumber should return student for valid roll")
    void getByRollNumber_validRoll_shouldReturn() {
        when(studentRepo.findByRollNumber("2024CS001"))
                .thenReturn(Optional.of(sampleStudent));

        Student found = studentService.getByRollNumber("2024CS001");

        assertEquals("Aarav Sharma", found.getName());
    }

    @Test
    @DisplayName("getByRollNumber should throw for invalid roll number")
    void getByRollNumber_invalidRoll_shouldThrow() {
        when(studentRepo.findByRollNumber("XXXXX"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.getByRollNumber("XXXXX"));
    }

    @Test
    @DisplayName("getStudentsByCourseId should delegate to repository")
    void getStudentsByCourseId_shouldWork() {
        when(studentRepo.findByCourseId(1L))
                .thenReturn(Collections.singletonList(sampleStudent));

        List<Student> results = studentService.getStudentsByCourseId(1L);

        assertEquals(1, results.size());
        verify(studentRepo).findByCourseId(1L);
    }
}
