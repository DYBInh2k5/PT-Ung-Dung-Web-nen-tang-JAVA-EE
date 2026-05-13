<%-- 
    Document   : student
    Created on : Apr 10, 2026, 2:21:42 PM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student List</h1>
        <hr/>
        <table border="1" cellspacing="0" cellpadding="4">
            <tr>
                <th>Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Date Of Birth</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Email</th>
            </tr>
            <c:forEach var="student" items="${list}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.firstName}</td>
                    <td>${student.lastName}</td>
                    <td>${student.dateOfBirth}</td>
                    <td>${student.address}</td>
                    <td>${student.phone}</td>
                    <td>${student.email}</td>
                </tr>
            </c:forEach>
        </table>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="student?page=${i}"">${i}</a>
        </c:forEach>
    </body>
</html>
