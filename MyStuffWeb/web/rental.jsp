<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "java.util.*" %>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.CategoryDAO" %>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%
   // Transaction tx = TransactionDAO.getInstance().create();
  //  tx.setType(rental);
   // session.setAttribute("transaction", tx);
   //String value = "";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%String title = "Rental";%>
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
 
<div id="body">
<h1><%out.write(title);%></h1>
<p>Please select a category and choose a store location.</p>
<div>
    <table width="600px" cellpadding="5px" cellspacing="10px">
        <tr><td>Select Category</td><td>Select Stores</td></tr>
    <tr>
    <td>
        
        <select NAME="Category">
            <% List<String> categoryList = new LinkedList<String>();
            categoryList = CategoryDAO.getInstance().getCategoryList();
        for(int i = 0; i<categoryList.size(); i++){
               String value = categoryList.get(i);
                %>
                <option VALUE="<%=i%>"><%=value%>
                <%
                }
                %>
          </select>
    </td>	
    <td>
        <form action="edu.byu.isys413.actions.GetRentals.action" method="post" target=_parent> 
        <input type="checkbox" name="StoreOpt1" value="000001117284553c0014b20a500442"> Provo<br>
        <input type="checkbox" name="StoreOpt2" value="000001117284553c0014b20a500443"> Logan<br>
        <input type="checkbox" name="StoreOpt3" value="000001117284553c0014b20a500444"> Murray<br>
        
    </td>
</table>
 <input type="submit" value="Search">
        </form>

<table width="600px" cellpadding="5px" cellspacing="10px">
    <tr><th>Available Products</th><th>Shopping Cart</th></tr>
    <tr>
    <td>
        Canon SLR 500X<br>
        Second Digital Camera<br>
        Third Digital Camera<br>
    </td>	
    <td>
        The transaction text goes here.
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
