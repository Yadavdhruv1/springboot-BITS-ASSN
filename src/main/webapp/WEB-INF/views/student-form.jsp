<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${student.id != null ? 'Edit Student' : 'Add Student'}</title>
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
        <h2 class="section-title">
            <c:choose>
                <c:when test="${student.id != null}">Edit Student</c:when>
                <c:otherwise>Register New Student</c:otherwise>
            </c:choose>
        </h2>

        <!-- display error message if validation or integrity issue -->
        <c:if test="${not empty errorMsg}">
            <div class="flash-msg error">${errorMsg}</div>
        </c:if>

        <div class="form-card">
            <!-- decide action URL based on whether this is an add or edit -->
            <c:set var="actionUrl" value="${student.id != null
                    ? pageContext.request.contextPath.concat('/students/update/').concat(student.id)
                    : pageContext.request.contextPath.concat('/students/save')}" />

            <form:form action="${actionUrl}" method="post" modelAttribute="student">

                <div class="field">
                    <label for="name">Full Name</label>
                    <form:input path="name" id="name" placeholder="Enter student's full name" />
                    <form:errors path="name" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="email">Email</label>
                    <form:input path="email" id="email" type="email" placeholder="student@university.in" />
                    <form:errors path="email" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="rollNumber">Roll Number</label>
                    <form:input path="rollNumber" id="rollNumber" placeholder="e.g. 2024CS001" />
                    <form:errors path="rollNumber" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="semester">Semester</label>
                    <form:input path="semester" id="semester" type="number" min="1" max="8" placeholder="1 to 8" />
                    <form:errors path="semester" cssClass="field-error" />
                </div>

                <div class="field">
                    <label for="courseSelect">Select Course</label>
                    <form:select path="course.id" id="courseSelect">
                        <form:option value="" label="-- Choose a course --" />
                        <c:forEach var="c" items="${allCourses}">
                            <form:option value="${c.id}" label="${c.courseName} (${c.courseCode})" />
                        </c:forEach>
                    </form:select>
                    <form:errors path="course" cssClass="field-error" />
                </div>

                <div class="form-btns">
                    <button type="submit" class="btn btn-blue">
                        ${student.id != null ? 'Update' : 'Save'} Student
                    </button>
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-grey">Cancel</a>
                </div>

            </form:form>
        </div>
    </div>

    <div class="page-footer">
        &copy; 2026 Student Course Manager &mdash; Built with Spring Boot, JPA & JSP
    </div>

</body>
</html>
