<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%//@page import="edu.byu.isys413.cbb54.intex2kb.data"%>
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
            <h1 style="padding-left: 95px; text-decoration: underline;">Login</h1>
            <p style="color: red; background-color: yellow"><%if (request.getAttribute("message") != null) {out.print(request.getAttribute("message"));}%>
            
            <% //edu.byu.isys413.cbb54.intex2kb.data.Customer c = edu.byu.isys413.cbb54.intex2kb.data.CustomerDAO.getInstance().create();
            //out.write(c.getId());
            %>
            <form style="margin: 0 auto;" action="edu.byu.isys413.actions.LoginWeb.action" method="post" target=_parent>
                <label for="email">Email: </label>
                <input type="text" name="email" /><br />
                <label for="password">Password: </label>
                <input type="password" name="password" /><br />
                <input style="float: right; margin-right: 5px; padding-right: 0px;" type="submit" value="Submit">
            </form>
            <div class="clear"></div>
        </div><!--end login-->

        
    </body>
</html>
