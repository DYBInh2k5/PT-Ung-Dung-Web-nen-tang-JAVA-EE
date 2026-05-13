<%-- 
    Document   : toy
    Created on : Oct 8, 2025, 10:06:28 AM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <%--Header--%>
                    <h1>Toy List</h1>
                    <hr/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <%--Content--%>
                    <a href="toy?op=create">Add New</a>
                    <table class="table table-striped">
                        <tr>
                            <th>No.</th>
                            <th>Id</th>
                            <th>Name</th>
                            <th style="text-align: right">Price</th>
                            <th>Expired Date</th>
                            <th>Brand</th>
                            <th>Operations</th>
                        </tr>
                        <c:forEach var="toy" items="${list}" varStatus="loop">
                            <tr>
                                <td>${loop.count}</td>
                                <td>${toy.id}</td>
                                <td>${toy.name}</td>
                                <td style="text-align: right">
                                    <fmt:formatNumber value="${toy.price}" type="currency" />                                    
                                </td>
                                <td>
                                    <fmt:formatDate value="${toy.expDate}" />                                    
                                </td>
                                <td>${toy.brand}</td>
                                <td>
                                    <a href="toy?op=edit&id=${toy.id}">Edit</a>
                                    <a href="toy?op=delete&id=${toy.id}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    ${message}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <%--Footer--%>
                    <hr/>
                    Copyrights &copy; HSU Students
                </div>
            </div>
        </div>
    </body>
</html>
