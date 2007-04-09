<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello (Customer)</h1>
    <h1>What would you like to do?</h1>
    <form action = "sale.jsp" method = "post">
        <input type="submit" value ="Purchase Items for Sale">
    </form>
    <form action = "rental.jsp" method = "post">
        <input type="submit" value ="Find Items to Rent">
    </form>
    <form action = "print.jsp" method = "post">
        <input type="submit" value ="Purchase Digital Prints">
    </form>
    <form action = "backup.jsp" method = "post">
        <input type="submit" value ="Backup your pictures">
    </form>
    
    </body>
</html>
