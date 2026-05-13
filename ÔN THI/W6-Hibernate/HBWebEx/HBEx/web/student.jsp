<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>

        <h2>Student List</h2>

        <form action="student">
            <input type="hidden" name="action" value="create"/>
            Name: <br/>
            <input type="text" name="name"/><br/>
            Age: <br/>
            <input type="text" name="age"/><br/>
            <input type="submit" value="Add"/><br/>
        </form>

        <table border="1" cellspacing="0" cellpadding="4">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Action</th>
            </tr>

            <c:forEach var="s" items="${list}">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.name}</td>
                    <td>${s.age}</td>
                    <td>
                        <a href="student?action=delete&id=${s.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
