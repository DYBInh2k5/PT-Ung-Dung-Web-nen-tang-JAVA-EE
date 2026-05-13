<%-- 
    Document   : delete
    Created on : Oct 22, 2025, 9:08:42 AM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Toy</title>
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>    
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-6">
                    <h1>Delete Confirmation</h1>
                    <hr/>
                    <form action="toy">
                        Are you sure to delete this toy with id = ${param.id}?
                        <br/>
                        <input type="hidden" name="op" value="delete_handler" />
                        <input type="hidden" name="id" value="${param.id}" />
                        <button type="submit" class="btn btn-outline-primary" name="choice" value="yes"><i class="bi bi-check-lg"></i> Yes</button>
                        <button type="submit" class="btn btn-outline-primary" name="choice" value="no"><i class="bi bi-x-lg"></i> No</button>
                    </form>
                    <i style="color:red">${message}</i>
                </div>
                <div class="col-sm-6">

                </div>
            </div>
        </div>
    </body>
</html>
