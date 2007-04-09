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

<%
String title = "NumberGame";
%>
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
                    <a id="nolink" id="logo" alt="Home" href="index.jsp">
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
                    <p><%=request.getAttribute("message")%></p>
                    <p><%=session.getAttribute("secretnumber")%></p>
                    <div id="form">
                        <form method="post" action="edu.byu.isys413.actions.NumberGame.action">
                            <label for="guess">NumberGuess: </label>
                            <input type="text" name="guess" size="30" /><br /><br />
                            <input type="submit" value="Submit" class="buttonSubmit" />
                            
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
