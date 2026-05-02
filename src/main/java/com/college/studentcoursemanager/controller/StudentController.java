package com.college.studentcoursemanager.controller;

import com.college.studentcoursemanager.entity.Student;
import com.college.studentcoursemanager.service.CourseService;
import com.college.studentcoursemanager.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for Student CRUD operations.
 * Maps to /students and its sub-paths.
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    // GET /students -> display the student listing page
    // uses the inner join custom query so we get course info too
    @GetMapping
    public String showStudentList(Model model) {
        model.addAttribute("studentList", studentService.getStudentsWithCourseInfo());
        return "student-list";
    }

    // GET /students/add -> show the blank add-student form
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allCourses", courseService.listAll());
        return "student-form";
    }

    // POST /students/save -> process the new student submission
    @PostMapping("/save")
    public String handleCreate(@Valid @ModelAttribute("student") Student student,
                               BindingResult bindingResult,
                               RedirectAttributes flash,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCourses", courseService.listAll());
            return "student-form";
        }

        try {
            studentService.addStudent(student);
            flash.addFlashAttribute("msg", "New student registered successfully!");
        } catch (RuntimeException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            model.addAttribute("allCourses", courseService.listAll());
            return "student-form";
        }

        return "redirect:/students";
    }

    // GET /students/edit/{id} -> load student data into the form for editing
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("student", studentService.getById(id));
            model.addAttribute("allCourses", courseService.listAll());
            return "student-form";
        } catch (RuntimeException ex) {
            return "redirect:/students";
        }
    }

    // POST /students/update/{id} -> save the edited student info
    @PostMapping("/update/{id}")
    public String handleUpdate(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("student") Student student,
                               BindingResult bindingResult,
                               RedirectAttributes flash,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCourses", courseService.listAll());
            return "student-form";
        }

        try {
            studentService.updateStudent(id, student);
            flash.addFlashAttribute("msg", "Student details have been updated!");
        } catch (RuntimeException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            model.addAttribute("allCourses", courseService.listAll());
            return "student-form";
        }

        return "redirect:/students";
    }
}
