<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Java Creed | Spring MVC Error Handling Example - Error Page</title>
  </head>
  <body>
    <h1>Unexpected Error</h1>
    <p>The server has encountered an unexpected error and our engineers were notified about this problem.  Any inconvenience caused is very much regretted.</p>
    <!--
    ---------------------------------------------------------------
    Error
    ---------------------------------------------------------------
    Exception:  ${exception.message}
    <c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
    </c:forEach>
    ---------------------------------------------------------------
    -->
  </body>
</html>