package com.college.studentcoursemanager.controller;

import com.college.studentcoursemanager.entity.Course;
import com.college.studentcoursemanager.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles all HTTP requests related to Course operations.
 * URL pattern: /courses/**
 */
@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // GET /courses -> show the list of all courses
    @GetMapping
    public String showCourseList(Model model) {
        model.addAttribute("courseList", courseService.listAll());
        return "course-list";
    }

    // GET /courses/add -> display empty form for creating a new course
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    // POST /courses/save -> handle the form submit for new course
    @PostMapping("/save")
    public String handleCreate(@Valid @ModelAttribute("course") Course course,
                               BindingResult bindingResult,
                               RedirectAttributes flash,
                               Model model) {
        // if validation failed, show form again with errors
        if (bindingResult.hasErrors()) {
            return "course-form";
        }

        try {
            courseService.addCourse(course);
            flash.addFlashAttribute("msg", "Course has been added successfully!");
        } catch (RuntimeException ex) {
            // duplicate code or other integrity error
            model.addAttribute("errorMsg", ex.getMessage());
            return "course-form";
        }

        return "redirect:/courses";
    }

    // GET /courses/edit/{id} -> load the edit form with existing data
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("course", courseService.getById(id));
            return "course-form";
        } catch (RuntimeException ex) {
            // if course doesn't exist, just go back to list
            return "redirect:/courses";
        }
    }

    // POST /courses/update/{id} -> save the edited course
    @PostMapping("/update/{id}")
    public String handleUpdate(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("course") Course course,
                               BindingResult bindingResult,
                               RedirectAttributes flash,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "course-form";
        }

        try {
            courseService.updateCourse(id, course);
            flash.addFlashAttribute("msg", "Course details updated!");
        } catch (RuntimeException ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "course-form";
        }

        return "redirect:/courses";
    }
}
