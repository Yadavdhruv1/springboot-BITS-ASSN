package com.college.studentcoursemanager.repository;

import com.college.studentcoursemanager.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Course entity.
 * Spring Data JPA auto-generates the implementation at runtime.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // find a course using its unique code like "CS201"
    Optional<Course> findByCourseCode(String courseCode);

    // quickly check if a particular course code is already taken
    boolean existsByCourseCode(String courseCode);
}
