package com.college.studentcoursemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Student-Course Management application.
 * This boots up Spring and starts the embedded Tomcat server.
 */
@SpringBootApplication
public class StudentCourseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCourseManagerApplication.class, args);
        System.out.println(">>> Application is running at http://localhost:8080");
    }
}
