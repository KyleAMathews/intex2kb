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
<%String title = "Photo Development";%>
<html>
<head><TITLE>MyStuff.com: <%out.write(title);%></TITLE>
    <link rel="StyleSheet" type="text/css" media="all" href="style.css" />
    <link rel="stylesheet" href="thickbox.css" type="text/css" media="screen" />
    <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript" src="jq-corner.js"></script>
    <script type="text/javascript" src="thickbox.js"></script>
    <script type="text/javascript">
	$(function(){	// shorthand for $(document).ready() BTW
        $("#navigation ul li a").corner();
	$("#bigcontainer").corner();
	});
$(document).ready(function(){
   // Your code here
 });
    </script>
</head>

<BODY>
<div id="bigcontainer">
<div id="container">
<div id="header">
    <a id="nolink" id="logo" alt="Home" href="index.jsp">
        <div id="logoHeader">
            <img src="mystuff.png" />
            <!--<h1>MyStuff.com</h1>-->
        </div>
    </a>
</div><!--end header-->
                   
<div id="navigation">
    <ul>
        <LI style="margin-top: 0;"><a href="sale.jsp">Sale</a></LI>
        <LI><a href="rental.jsp">Rental</a></LI>
        <LI><a href="photo.jsp">Photo</a></LI>
        <LI><a href="backup.jsp">Backup</a></LI>
    </ul>
</div><!--end navigation-->
<div id="rightcolumn">
    <h4>MyStuff Photo Printing</h4>
</div><!--end right column-->  
<div id="body">
<h1><%out.write(title);%></h1>
<p>Welcome to MyStuff Online Photo Printing<br><br>
Please use the following input boxes to upload up to five (5) photos to be printed.</p>
<div>
    <form method="post" action="edu.byu.isys413.actions.photo.action">
        <table width="600px" cellpadding="5px" cellspacing="10px" valign="middle">
            <tr><td>Picture 1:</td><td><input type="file" name="datafile1" size="32"></td></tr>
            <tr><td>Picture 2:</td><td><input type="file" name="datafile2" size="32"></td></tr>
            <tr><td>Picture 3:</td><td><input type="file" name="datafile3" size="32"></td></tr>
            <tr><td>Picture 4:</td><td><input type="file" name="datafile4" size="32"></td></tr>
            <tr><td>Picture 5:</td><td><input type="file" name="datafile5" size="32"></td></tr>
            <tr><td><input type="submit" value="Submit" class="buttonSubmit" /></td></tr>
        </table>
    </form>
</div>

</div><!--end body-->
</div><!--end container-->
<div id="footer">
    <p>Copyright 2006 MyStuff.com || All rights reserved</p>
</div><!--end footer-->
               
</div><!--end bigcontainer-->
</BODY>
</html>
