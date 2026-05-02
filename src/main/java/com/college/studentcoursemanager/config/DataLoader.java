package com.college.studentcoursemanager.config;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.entity.Student;
import com.college.studentcoursemanager.repository.CourseRepository;
import com.college.studentcoursemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Populates the database with initial sample data when the app starts.
 * Only runs if the courses table is empty (so it won't duplicate data on restart).
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Override
    public void run(String... args) throws Exception {

        // skip if data already exists
        if (courseRepo.count() > 0) {
            System.out.println(">> Database already contains data, skipping seed.");
            return;
        }

        System.out.println(">> Seeding database with sample courses and students...");

        // --- 10 sample courses ---
        Course dsa = new Course("Data Structures & Algorithms", "CS201", "Computer Science", 4,
                "Covers arrays, linked lists, trees, graphs, sorting algorithms");
        Course dbms = new Course("Database Management Systems", "CS301", "Computer Science", 4,
                "SQL, normalization, ER diagrams, transactions");
        Course os = new Course("Operating Systems", "CS302", "Computer Science", 3,
                "Process scheduling, memory management, file systems");
        Course cn = new Course("Computer Networks", "CS303", "Computer Science", 3,
                "OSI model, TCP/IP, routing protocols, sockets");
        Course webDev = new Course("Web Technologies", "CS401", "Computer Science", 3,
                "HTML, CSS, JavaScript, Spring Boot, REST APIs");
        Course ml = new Course("Machine Learning", "CS501", "Computer Science", 4,
                "Regression, classification, neural networks, clustering");
        Course de = new Course("Digital Electronics", "EC201", "Electronics", 3,
                "Boolean algebra, logic gates, flip-flops, counters");
        Course sp = new Course("Signal Processing", "EC301", "Electronics", 4,
                "Fourier transform, z-transform, FIR and IIR filters");
        Course maths = new Course("Engineering Mathematics", "MA101", "Mathematics", 4,
                "Calculus, linear algebra, probability, complex analysis");
        Course physics = new Course("Engineering Physics", "PH101", "Physics", 3,
                "Quantum mechanics, optics, semiconductor physics");

        List<Course> savedCourses = courseRepo.saveAll(
                Arrays.asList(dsa, dbms, os, cn, webDev, ml, de, sp, maths, physics)
        );

        // --- 10 sample students ---
        studentRepo.saveAll(Arrays.asList(
                new Student("Aarav Sharma", "aarav.sharma@university.in", "2024CS001", 3, dsa),
                new Student("Meera Patel", "meera.patel@university.in", "2024CS002", 3, dbms),
                new Student("Karan Gupta", "karan.gupta@university.in", "2024CS003", 5, os),
                new Student("Riya Iyer", "riya.iyer@university.in", "2024CS004", 5, dsa),
                new Student("Dev Malhotra", "dev.malhotra@university.in", "2024CS005", 3, webDev),
                new Student("Nisha Rao", "nisha.rao@university.in", "2024EC001", 3, de),
                new Student("Aryan Kapoor", "aryan.kapoor@university.in", "2024CS006", 7, ml),
                new Student("Pooja Menon", "pooja.menon@university.in", "2024EC002", 5, sp),
                new Student("Sahil Verma", "sahil.verma@university.in", "2024MA001", 1, maths),
                new Student("Tanvi Joshi", "tanvi.joshi@university.in", "2024PH001", 1, physics)
        ));

        System.out.println(">> Done! Loaded " + savedCourses.size() + " courses and 10 students.");
    }
}
