<%-- 
    Document   : api
    Created on : Dec 8, 2017, 10:02:42 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>  
    </head>

    <body onload="window.history.forward(0)">

        <c:if test="${regname!=null}">
            <label style="color: red;"><c:out value="${regname}"></c:out></label>
        </c:if>
        <c:if test="${logMsg!=null}">
            <label style="color: red;"><c:out value="${logMsg}"></c:out></label>
        </c:if>


        <div style="text-align: center;" >
            <div >
                <form action="CommonApi" method="post">

                    <h1>Login Page</h1>

                    <input type="text" placeholder="Enter Username" name="username"/><br><br>
                    <button type="submit" value="submit">Submit</button>

                </form>
                <br>
            </div>
        </div>
    </body>
</html>
