<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Courses List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/" class="logo">🎓 StudentCourse Manager</a>
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/students">Students</a></li>
            <li><a href="${pageContext.request.contextPath}/courses" class="current">Courses</a></li>
        </ul>
    </nav>

    <div class="wrapper">
        <h2 class="section-title">All Courses</h2>

        <c:if test="${not empty msg}">
            <div class="flash-msg success">${msg}</div>
        </c:if>

        <a href="${pageContext.request.contextPath}/courses/add" class="btn btn-green" style="margin-bottom: 14px;">
            + New Course
        </a>

        <table class="tbl">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Course Name</th>
                    <th>Code</th>
                    <th>Department</th>
                    <th>Credits</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cr" items="${courseList}">
                    <tr>
                        <td>${cr.id}</td>
                        <td>${cr.courseName}</td>
                        <td>${cr.courseCode}</td>
                        <td>${cr.department}</td>
                        <td>${cr.credits}</td>
                        <td>${cr.description}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/courses/edit/${cr.id}" class="btn btn-orange">
                                Edit
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty courseList}">
                    <tr>
                        <td colspan="7" class="empty-row">No courses available. Add one using the button above.</td>
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
