package com.college.studentcoursemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller that serves the home/landing page of the application.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
}
