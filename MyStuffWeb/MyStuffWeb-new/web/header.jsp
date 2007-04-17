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
<head><TITLE>MyStuff.com</TITLE>
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
        <LI><a href="edu.byu.isys413.actions.backupFile.action">Backup</a></LI>
    </ul>
</div><!--end navigation-->