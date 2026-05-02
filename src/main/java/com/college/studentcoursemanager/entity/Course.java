package com.college.studentcoursemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Course in our system.
 * Each course has a name, code, department, credits, and a description.
 * A course can have multiple students enrolled in it (OneToMany relationship).
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter the course name")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotBlank(message = "Course code cannot be empty")
    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @NotBlank(message = "Department field is required")
    @Column(nullable = false)
    private String department;

    @Min(value = 1, message = "Minimum 1 credit required")
    @Max(value = 6, message = "Maximum 6 credits allowed")
    @Column(nullable = false)
    private int credits;

    @Column(length = 500)
    private String description;

    /*
     * One course -> many students
     * mappedBy tells JPA that the Student entity owns the relationship
     * CascadeType.ALL means if we delete a course, linked students get deleted too
     */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    // empty constructor needed by JPA/Hibernate
    public Course() {
    }

    // constructor with fields (used in DataLoader to seed the DB)
    public Course(String courseName, String courseCode, String department,
                  int credits, String description) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.department = department;
        this.credits = credits;
        this.description = description;
    }

    /* --- getters and setters --- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return courseName + " (" + courseCode + ")";
    }
}
