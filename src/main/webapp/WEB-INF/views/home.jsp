<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Student Course Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <!-- top navigation bar -->
    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/" class="logo">🎓 StudentCourse Manager</a>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/" class="current">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/students">Students</a></li>
            <li><a href="${pageContext.request.contextPath}/courses">Courses</a></li>
        </ul>
    </nav>

    <div class="wrapper">
        <div class="hero-section">
            <h1>Student & Course Management</h1>
            <p class="subtitle">A Spring Boot web application to manage student enrollments and course records.</p>

            <div class="card-grid">
                <div class="info-card">
                    <div class="emoji">👨‍🎓</div>
                    <h3>Students</h3>
                    <p>Add new students, view their details along with enrolled courses, or update existing records.</p>
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-blue">Go to Students</a>
                </div>

                <div class="info-card">
                    <div class="emoji">📘</div>
                    <h3>Courses</h3>
                    <p>Create new courses, see all available courses, and modify course information as needed.</p>
                    <a href="${pageContext.request.contextPath}/courses" class="btn btn-green">Go to Courses</a>
                </div>
            </div>
        </div>
    </div>

    <div class="page-footer">
        &copy; 2026 Student Course Manager &mdash; Built with Spring Boot, JPA & JSP
    </div>

</body>
</html>
