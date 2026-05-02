package com.college.studentcoursemanager.service;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Course-related business logic.
 * Acts as a middle layer between the controller and the repository.
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepo;

    // fetch every course from the database
    public List<Course> listAll() {
        return courseRepo.findAll();
    }

    // get a single course by its primary key
    public Course getById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No course found with id = " + id));
    }

    // save a brand-new course after checking for duplicate codes
    public Course addCourse(Course course) {
        if (courseRepo.existsByCourseCode(course.getCourseCode())) {
            throw new RuntimeException(
                    "A course with code '" + course.getCourseCode() + "' already exists in the database!");
        }
        return courseRepo.save(course);
    }

    // update the fields of an existing course
    public Course updateCourse(Long id, Course updatedData) {
        Course existing = getById(id); // will throw if not found

        existing.setCourseName(updatedData.getCourseName());
        existing.setCourseCode(updatedData.getCourseCode());
        existing.setDepartment(updatedData.getDepartment());
        existing.setCredits(updatedData.getCredits());
        existing.setDescription(updatedData.getDescription());

        return courseRepo.save(existing);
    }

    // lookup by course code (used internally)
    public Course findByCode(String code) {
        return courseRepo.findByCourseCode(code)
                .orElseThrow(() -> new RuntimeException(
                        "No course found with code = " + code));
    }
}
