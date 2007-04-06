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
   <%String title = "index";%>
   <html>
       <head><TITLE>MyStuff.com: <%out.write(title);%></TITLE>
           <link rel="StyleSheet" type="text/css" media="all" href="style.css" />
           <script type="text/javascript" src="jquery.js"></script>
           <script type="text/javascript" src="jq-corner.js"></script>
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
                       <a id="nolink" id="logo" alt="Home" href="index.html">
                           <div id="logoHeader">
                               <img src="mystuff.png" />
                               <!--<h1>MyStuff.com</h1>-->
                           </div>
                       </a>
                   </div><!--end header-->
                   
                   <div id="navigation">
                       <ul>
                           <LI><a href="">Nav 1</a></LI>
                           <LI><a href="">Nav 2</a></LI>
                           <LI><a href="">Nav 3</a></LI>
                           <LI><a href="">Nav 4</a></LI>
                           <LI><a href="">Nav 5</a></LI>
                       </ul>
                   </div><!--end navigation-->
                   
                   <div id="body">
                       <h1><%out.write(title);%></h1>
                       <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Quisque purus. Mauris sed arcu id felis vestibulum luctus. Praesent fringilla nonummy eros. Suspendisse eget ligula eu nisi luctus pulvinar. Morbi feugiat convallis nibh. Quisque suscipit, ligula eget molestie accumsan, quam velit pellentesque libero, id placerat urna ligula feugiat erat. Suspendisse et mauris tincidunt eros fermentum nonummy. Pellentesque sed nulla malesuada turpis pharetra faucibus. Aenean feugiat fringilla orci. Vestibulum dignissim pellentesque magna. Aliquam in sem at justo pretium elementum. Nunc ultricies velit eget urna. Duis massa.</p>
                   </div><!--end body-->
               </div><!--end container-->
               <div id="footer">
                   <p>Copyright 2006 MyStuff.com || All rights reserved</p>
               </div><!--end footer-->
               
           </div><!--end bigcontainer-->
       </BODY>
   </html>