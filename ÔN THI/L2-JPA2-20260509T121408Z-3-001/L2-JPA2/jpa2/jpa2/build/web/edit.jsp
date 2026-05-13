<%-- 
    Document   : edit
    Created on : Apr 3, 2026, 1:44:33 PM
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
        <h1>Toy Edit</h1>
        <hr/>
        <form action="toy">
            Id:<br/>
            <input type="text" name="id" value="${toy.id}" readonly/><br/>
            Name:<br/>
            <input type="text" name="name" value="${toy.name}"/><br/>
            Price:<br/>
            <input type="number" step="0.01" name="price" value="${toy.price}"/><br/>
            Expired date:<br/>
            <input type="date" name="expDate" value="<fmt:formatDate value="${toy.expDate}" pattern="yyyy-MM-dd" />"/><br/>
            Brand:<br/>
            <select name="brandId">
                <c:forEach var="brand" items="${list}">
                    <option value="${brand.id}" ${brand.id==toy.brand.id?"selected":""}>${brand.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="action" value="edit_handler"/><br/>
            <button type="submit" name="op" value="update">Update</button>
            <button type="submit" name="op" value="cancel">Cancel</button>
        </form>
    </body>
</html>
