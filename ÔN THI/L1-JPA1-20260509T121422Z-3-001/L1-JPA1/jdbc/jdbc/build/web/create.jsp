<%-- 
    Document   : create
    Created on : Oct 15, 2025, 10:25:49 AM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Toy</title>
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>    
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <%--Header--%>
                    <h1>Create Toy</h1>
                    <hr/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <%--Content--%>
                    <form action="toy">
                        <div class="mb-3 mt-3">
                            <label for="id" class="form-label">Id:</label>
                            <input type="text" class="form-control" id="id" name="id" placeholder="Enter toy id" value="${param.id}">
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" class="form-control" id="name" placeholder="Enter toy name" name="name" value="${param.name}">
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price:</label>
                            <input type="number" min="1" step="0.001" class="form-control" id="price" placeholder="Enter toy price" name="price" value="${param.price}">
                        </div>
                        <div class="mb-3">
                            <label for="expDate" class="form-label">Expired date:</label>
                            <input type="date" class="form-control" id="expDate" placeholder="Enter toy expired đate" name="expDate" value="${param.expDate}">
                        </div>
                        <div class="mb-3">
                            <label for="brand" class="form-label">Brand:</label>
                            <%--<input type="text" class="form-control" id="brand" placeholder="Enter toy brand" name="brand" value="${param.brand}">--%>
                            <select id="brand" name="brand" class="form-control">
                                <c:forEach var="brand" items="${list}">
                                    <option value="${brand.id}" ${brand.id==param.brand?"selected":""}>${brand.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="hidden" name="op" value="create_handler"/>
                        <button type="submit" class="btn btn-outline-primary" name="choice" value="create"><i class="bi bi-check-lg"></i> Create</button>
                        <button type="submit" class="btn btn-outline-primary" name="choice" value="cancel"><i class="bi bi-x-lg"></i> Cancel</button>
                    </form>
                    <i style="color:red">${message}</i>
                </div>
                <div class="col-sm-6">
                    <img src="pictures/toy.png" alt=""/>
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
