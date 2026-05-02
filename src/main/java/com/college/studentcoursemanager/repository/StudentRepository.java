package com.college.studentcoursemanager.repository;

import com.college.studentcoursemanager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity.
 * Contains custom query methods along with the default CRUD ones from JpaRepository.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // look up student by their roll number
    Optional<Student> findByRollNumber(String rollNumber);

    // checks for duplicate email before saving
    boolean existsByEmail(String email);

    // checks for duplicate roll number before saving
    boolean existsByRollNumber(String rollNumber);

    /**
     * Custom JPQL query that performs an INNER JOIN between the Student
     * and Course tables. This ensures only students who have a valid
     * course assigned are returned in the result.
     */
    @Query("SELECT s FROM Student s INNER JOIN s.course c")
    List<Student> findAllStudentsWithCourses();

    // get all students for a specific course (by course id)
    List<Student> findByCourseId(Long courseId);
}
