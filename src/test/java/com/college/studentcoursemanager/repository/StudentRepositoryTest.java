package com.college.studentcoursemanager.repository;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for StudentRepository.
 * Uses H2 in-memory database instead of MySQL.
 */
@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    private Course testCourse;

    @BeforeEach
    void prepareTestData() {
        studentRepo.deleteAll();
        courseRepo.deleteAll();

        // create a course and two students for testing
        testCourse = courseRepo.save(
                new Course("Data Structures", "CS201", "Computer Science", 4, "DSA basics")
        );

        studentRepo.save(new Student("Aarav Sharma", "aarav@uni.in", "2024CS001", 3, testCourse));
        studentRepo.save(new Student("Meera Patel", "meera@uni.in", "2024CS002", 5, testCourse));
    }

    @Test
    @DisplayName("Should find student by roll number")
    void findByRollNumber_shouldReturnStudent() {
        Optional<Student> result = studentRepo.findByRollNumber("2024CS001");

        assertTrue(result.isPresent(), "Student should be found");
        assertEquals("Aarav Sharma", result.get().getName());
    }

    @Test
    @DisplayName("existsByEmail should return true for existing email")
    void existsByEmail_existingEmail_shouldReturnTrue() {
        assertTrue(studentRepo.existsByEmail("aarav@uni.in"));
    }

    @Test
    @DisplayName("existsByEmail should return false for unknown email")
    void existsByEmail_unknownEmail_shouldReturnFalse() {
        assertFalse(studentRepo.existsByEmail("nobody@uni.in"));
    }

    @Test
    @DisplayName("existsByRollNumber should detect existing roll numbers")
    void existsByRollNumber_shouldWork() {
        assertTrue(studentRepo.existsByRollNumber("2024CS001"));
        assertFalse(studentRepo.existsByRollNumber("9999XX999"));
    }

    @Test
    @DisplayName("Inner join query should return students with valid courses")
    void findAllStudentsWithCourses_shouldReturnNonEmptyList() {
        List<Student> results = studentRepo.findAllStudentsWithCourses();

        assertFalse(results.isEmpty(), "Should have at least one student");
        // each student must have a non-null course (because of inner join)
        for (Student s : results) {
            assertNotNull(s.getCourse(), "Course should not be null");
        }
    }

    @Test
    @DisplayName("Should find students filtered by course ID")
    void findByCourseId_shouldReturnCorrectCount() {
        List<Student> results = studentRepo.findByCourseId(testCourse.getId());

        assertEquals(2, results.size(), "Both students belong to the same course");
    }
}
