<%-- 
    Document   : index
    Created on : Mar 20, 2026, 1:58:39 PM
    Author     : PHT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calculator</title>
    </head>
    <body>
        <h1>Calculator</h1>
        <hr/>
        <form action="calculator">
            Number 1:<br/>
            <input type="number" step="0.01" name="num1" value="${param.num1}" /><br/>
            Number 2:<br/>
            <input type="number" step="0.01" name="num2" value="${param.num2}" /><br/>
            <button type="submit" name="op" value="add">Add</button>
            <button type="submit" name="op" value="sub">Sub</button>
            <button type="submit" name="op" value="mul">Mul</button>
            <button type="submit" name="op" value="div">Div</button>
        </form>
        Result: ${model.result}
    </body>
</html>
