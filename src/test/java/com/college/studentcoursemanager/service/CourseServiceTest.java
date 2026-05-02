package com.college.studentcoursemanager.service;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CourseService using Mockito to mock the repository layer.
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepo;

    @InjectMocks
    private CourseService courseService;

    private Course sampleCourse;

    @BeforeEach
    void init() {
        sampleCourse = new Course("Data Structures", "CS201", "Computer Science", 4, "DSA basics");
        sampleCourse.setId(1L);
    }

    @Test
    @DisplayName("listAll should return all courses from repository")
    void listAll_shouldReturnAllCourses() {
        Course another = new Course("DBMS", "CS301", "CS", 4, "Database course");
        another.setId(2L);

        when(courseRepo.findAll()).thenReturn(Arrays.asList(sampleCourse, another));

        List<Course> courses = courseService.listAll();

        assertEquals(2, courses.size());
        assertEquals("Data Structures", courses.get(0).getCourseName());
        verify(courseRepo).findAll();
    }

    @Test
    @DisplayName("getById should return course when it exists")
    void getById_existingId_shouldReturnCourse() {
        when(courseRepo.findById(1L)).thenReturn(Optional.of(sampleCourse));

        Course found = courseService.getById(1L);

        assertNotNull(found);
        assertEquals("CS201", found.getCourseCode());
    }

    @Test
    @DisplayName("getById should throw when course doesn't exist")
    void getById_missingId_shouldThrowException() {
        when(courseRepo.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            courseService.getById(999L);
        });

        assertTrue(ex.getMessage().contains("No course found"));
    }

    @Test
    @DisplayName("addCourse should save when course code is unique")
    void addCourse_uniqueCode_shouldSave() {
        Course newCourse = new Course("Web Tech", "CS401", "CS", 3, "Web development");

        when(courseRepo.existsByCourseCode("CS401")).thenReturn(false);
        when(courseRepo.save(newCourse)).thenReturn(newCourse);

        Course saved = courseService.addCourse(newCourse);

        assertNotNull(saved);
        assertEquals("Web Tech", saved.getCourseName());
        verify(courseRepo).save(newCourse);
    }

    @Test
    @DisplayName("addCourse should throw on duplicate course code")
    void addCourse_duplicateCode_shouldThrowException() {
        Course duplicate = new Course("Duplicate", "CS201", "CS", 3, "Dup");

        when(courseRepo.existsByCourseCode("CS201")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            courseService.addCourse(duplicate);
        });

        assertTrue(ex.getMessage().contains("already exists"));
        verify(courseRepo, never()).save(any());
    }

    @Test
    @DisplayName("updateCourse should modify and save existing course")
    void updateCourse_shouldUpdateFields() {
        Course updatedData = new Course("DSA Advanced", "CS201", "CS", 5, "Advanced DSA");

        when(courseRepo.findById(1L)).thenReturn(Optional.of(sampleCourse));
        when(courseRepo.save(any(Course.class))).thenReturn(sampleCourse);

        Course result = courseService.updateCourse(1L, updatedData);

        assertNotNull(result);
        verify(courseRepo).findById(1L);
        verify(courseRepo).save(any(Course.class));
    }

    @Test
    @DisplayName("findByCode should return course for valid code")
    void findByCode_validCode_shouldReturnCourse() {
        when(courseRepo.findByCourseCode("CS201")).thenReturn(Optional.of(sampleCourse));

        Course found = courseService.findByCode("CS201");

        assertEquals("Data Structures", found.getCourseName());
    }

    @Test
    @DisplayName("findByCode should throw for invalid code")
    void findByCode_invalidCode_shouldThrow() {
        when(courseRepo.findByCourseCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.findByCode("INVALID"));
    }
}
