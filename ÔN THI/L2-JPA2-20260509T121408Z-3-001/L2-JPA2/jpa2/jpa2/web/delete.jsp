<%-- 
    Document   : delete
    Created on : Apr 3, 2026, 5:46:21 PM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Delete Confirmation</h1>
        <hr/>
        <form action="toy">
            Are you sure to delete this toy with id=${param.id}?<br/>
            <input type="hidden" name="id" value="${param.id}"/>
            <input type="hidden" name="action" value="delete_handler"/><br/>
            <button type="submit" name="op" value="yes">Yes</button>
            <button type="submit" name="op" value="no">No</button>
        </form>
    </body>
</html>
