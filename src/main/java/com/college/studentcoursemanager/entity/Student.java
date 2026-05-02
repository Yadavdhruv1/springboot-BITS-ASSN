package com.college.studentcoursemanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a Student in our system.
 * Each student is linked to exactly one course (ManyToOne).
 * Fields: name, email, rollNumber, semester, course
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Email cannot be left blank")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Roll number is mandatory")
    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false)
    private int semester;

    /*
     * Many students belong to a single course.
     * FetchType.EAGER means the course data is loaded immediately with the student.
     * JoinColumn specifies the foreign key column in the students table.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "You must select a course")
    private Course course;

    public Student() {
        // default constructor for JPA
    }

    public Student(String name, String email, String rollNumber,
                   int semester, Course course) {
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.semester = semester;
        this.course = course;
    }

    /* --- Getters & Setters --- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return name + " [" + rollNumber + "] - Sem " + semester;
    }
}
