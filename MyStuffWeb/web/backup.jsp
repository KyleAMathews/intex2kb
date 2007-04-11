<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.views.backupView"%>
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
<%String title = "Backup";%>
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
    <h4>MyStuff Backup</h4>
    <p class="small">75% Used of 10GB</p>
    <br />
    <h4>Actions:</h4>
    <ul>
        <li><a href="">View Slideshow</a></li>
        <li><a href="">Upload File(s)</a></li>
        <li><a href="backuptx.jsp">Add Backup Space</a></li>
        <li><a href="">Help</a></li>
    </ul>
</div><!--end right column-->  
<div id="body">
<h1><%out.write(title);%></h1>
<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Quisque purus. Mauris sed arcu id felis vestibulum luctus. Praesent fringilla nonummy eros. Suspendisse eget ligula eu nisi luctus pulvinar. Morbi feugiat convallis nibh. Quisque suscipit, ligula eget molestie accumsan, quam velit pellentesque libero, id placerat urna ligula feugiat erat. Suspendisse et mauris tincidunt eros fermentum nonummy. Pellentesque sed nulla malesuada turpis pharetra faucibus. Aenean feugiat fringilla orci. Vestibulum dignissim pellentesque magna. Aliquam in sem at justo pretium elementum. Nunc ultricies velit eget urna. Duis massa.</p>
<div><br />
    <%="kyle"%>
    <%out.print(session.getAttribute("membid"));%>
    <%out.print(backupView.readPhotos((String)session.getAttribute("membid")));%>
<table width="500px" cellpadding="5px" cellspacing="10px">
    <tr><th>Kyle in Snow</th><th>Kyle is snow isn't this a long title?</th><th>Baby J</th></tr>
    <tr>
    <td>
    <a href="images/pic1.jpg" class="thickbox" rel="gallery"><img src="images/pic1.jpg" title="Kyle in Snow" /></a><br /><a href="downloadfile.jsp?id=03488023840234">Download</a><a href="deletefile.jsp?id=23402384023840">Delete</a></td>	
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
