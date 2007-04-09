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
<%String title = "Login";
String login = "";
%>
<%if ((String)request.getAttribute("login") == "1") {
    login = "target=_parent";
    }else {
    login = "";
    }%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="StyleSheet" type="text/css" media="all" href="style.css" />
        
        <style>
            body{
            background-color: #ddccff;
            background-image: url();
                }
        </style>
    </head>
    <body>
    
    <div id="login">   
        <h1 style="padding-left: 95px; text-decoration: underline;"><%=title%></h1>
        <p style="color: red; background-color: yellow"><%=request.getAttribute("message")%>
        
        <form style="margin: 0 auto;" action="edu.byu.isys413.actions.LoginWeb.action" method="post" <%=login%>>
            <label for="email">Email: </label>
            <input type="text" name="email" ><br />
            <label for="password">Password: </label>
            <input type="password" name="password"><br />
            <input style="float: right; margin-right: 5px; padding-right: 0px;" type="submit" value="Submit">
        </form>
        <div class="clear"></div>
    </div><!--end login-->
    
    
    </body>
</html>
