package com.college.studentcoursemanager.service;

import com.college.studentcoursemanager.entity.Student;
import com.college.studentcoursemanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Student business logic.
 * Contains methods for CRUD + the custom inner join query.
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    // returns all students (plain findAll)
    public List<Student> listAll() {
        return studentRepo.findAll();
    }

    // get one student or throw an error
    public Student getById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Student with id " + id + " does not exist"));
    }

    // add a new student with duplicate checks on email and roll number
    public Student addStudent(Student student) {
        // make sure email is unique
        if (studentRepo.existsByEmail(student.getEmail())) {
            throw new RuntimeException(
                    "Email '" + student.getEmail() + "' is already registered to another student!");
        }
        // make sure roll number is unique
        if (studentRepo.existsByRollNumber(student.getRollNumber())) {
            throw new RuntimeException(
                    "Roll number '" + student.getRollNumber() + "' is already taken!");
        }
        return studentRepo.save(student);
    }

    // update existing student details
    public Student updateStudent(Long id, Student newData) {
        Student current = getById(id);

        current.setName(newData.getName());
        current.setEmail(newData.getEmail());
        current.setRollNumber(newData.getRollNumber());
        current.setSemester(newData.getSemester());
        current.setCourse(newData.getCourse());

        return studentRepo.save(current);
    }

    /**
     * Uses the custom JPQL inner join query from StudentRepository
     * to get students along with their associated course info.
     */
    public List<Student> getStudentsWithCourseInfo() {
        return studentRepo.findAllStudentsWithCourses();
    }

    // find by roll number
    public Student getByRollNumber(String rollNumber) {
        return studentRepo.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException(
                        "No student found with roll number: " + rollNumber));
    }

    // filter students by a particular course
    public List<Student> getStudentsByCourseId(Long courseId) {
        return studentRepo.findByCourseId(courseId);
    }
}
