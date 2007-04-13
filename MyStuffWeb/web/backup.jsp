<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.views.*"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*"%>
<%@page import="java.util.*"%>
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
<%List<photoBackupBO> pb = new LinkedList<photoBackupBO>();
pb = (List<photoBackupBO>)session.getAttribute("files");
%>
<html>
<head><TITLE>MyStuff.com: File Backup</TITLE>
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
<%@ include file="backuprc.jsp" %>  
<div id="body">
<h1>My Stuff File Backup</h1>
<%=pb.get(0).getFilesize()%>
<p>Welcome to you My Stuff File Backup Page</p>
<div><br />
    <jsp:include page="filethumbs.jsp" />
<table width="500px" cellpadding="5px" cellspacing="10px">
    <tr><th>Kyle in Snow</th><th>Kyle is snow isn't this a long title?</th><th>Baby J</th></tr>
    <tr>
    <td>
    <a href="images/pic1.jpg" class="thickbox" rel="gallery"><img class="thickbox" src="images/pic1.jpg" title="Kyle in Snow" /></a><br /><a href="downloadfile.jsp?id=03488023840234">Download</a><a href="deletefile.jsp?id=23402384023840">Delete</a></td>	
    <td>
        <a href="images/pic2.jpg" class="thickbox" rel="gallery"><img src="images/pic2.jpg" class="thickbox" rel="gallery" /></a><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a>
    </td>
    <TD><a href="images/pic3.jpg" class="thickbox" rel="gallery"><img src="images/pic3.jpg" class="thickbox" rel="gallery" /></a><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></TD>
    </td>
</table>

<table width="500px" cellpadding="5px" cellspacing="10px">
    <tr><th>Kyle in Snow</th><th>Kyle is snow isn't this a long title?</th><th>Baby J</th></tr>
    <tr>
    <td>
    <img src="images/pic1.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></td>	
    <td>
        <img src="images/pic2.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a>
    </td>
    <TD><img src="images/pic3.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></TD>
    </td> 
</table>
</div>

</div><!--end body-->
</div><!--end container-->
<div id="footer">
    <p>Copyright 2006 MyStuff.com || All rights reserved</p>
</div><!--end footer-->
               
</div><!--end bigcontainer-->
</BODY>
</html>
