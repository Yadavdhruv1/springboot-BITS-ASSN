<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Students List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/" class="logo">🎓 StudentCourse Manager</a>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/students" class="current">Students</a></li>
            <li><a href="${pageContext.request.contextPath}/courses">Courses</a></li>
        </ul>
    </nav>

    <div class="wrapper">
        <h2 class="section-title">All Students (with Course Info via Inner Join)</h2>

        <!-- show success flash message if present -->
        <c:if test="${not empty msg}">
            <div class="flash-msg success">${msg}</div>
        </c:if>

        <a href="${pageContext.request.contextPath}/students/add" class="btn btn-green" style="margin-bottom: 14px;">
            + Add Student
        </a>

        <table class="tbl">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Student Name</th>
                    <th>Email</th>
                    <th>Roll No</th>
                    <th>Semester</th>
                    <th>Enrolled Course</th>
                    <th>Department</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${studentList}">
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.name}</td>
                        <td>${s.email}</td>
                        <td>${s.rollNumber}</td>
                        <td>${s.semester}</td>
                        <td>${s.course.courseName}</td>
                        <td>${s.course.department}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/students/edit/${s.id}" class="btn btn-orange">
                                Edit
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty studentList}">
                    <tr>
                        <td colspan="8" class="empty-row">No students yet. Click "Add Student" to get started.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="page-footer">
        &copy; 2026 Student Course Manager &mdash; Built with Spring Boot, JPA & JSP
    </div>

</body>
</html>
