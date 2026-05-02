<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${course.id != null ? 'Edit Course' : 'Add Course'}</title>
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
        <h2 class="section-title">
            <c:choose>
                <c:when test="${course.id != null}">Edit Course Details</c:when>
                <c:otherwise>Add a New Course</c:otherwise>
            </c:choose>
        </h2>

        <c:if test="${not empty errorMsg}">
            <div class="flash-msg error">${errorMsg}</div>
        </c:if>

        <div class="form-card">
            <c:set var="actionUrl" value="${course.id != null
                    ? pageContext.request.contextPath.concat('/courses/update/').concat(course.id)
                    : pageContext.request.contextPath.concat('/courses/save')}" />

            <form:form action="${actionUrl}" method="post" modelAttribute="course">

                <div class="field">
                    <label for="courseName">Course Name</label>
                    <form:input path="courseName" id="courseName" placeholder="e.g. Data Structures" />
                    <form:errors path="courseName" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="courseCode">Course Code</label>
                    <form:input path="courseCode" id="courseCode" placeholder="e.g. CS201" />
                    <form:errors path="courseCode" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="department">Department</label>
                    <form:input path="department" id="department" placeholder="e.g. Computer Science" />
                    <form:errors path="department" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="credits">Credits</label>
                    <form:input path="credits" id="credits" type="number" min="1" max="6" placeholder="1 to 6" />
                    <form:errors path="credits" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="description">Description</label>
                    <form:textarea path="description" id="description" placeholder="Short description of the course" />
                    <form:errors path="description" cssClass="field-error" />
                </div>

                <div class="form-btns">
                    <button type="submit" class="btn btn-blue">
                        ${course.id != null ? 'Update' : 'Save'} Course
                    </button>
                    <a href="${pageContext.request.contextPath}/courses" class="btn btn-grey">Cancel</a>
                </div>

            </form:form>
        </div>
    </div>

    <div class="page-footer">
        &copy; 2026 Student Course Manager &mdash; Built with Spring Boot, JPA & JSP
    </div>

</body>
</html>
