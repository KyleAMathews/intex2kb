<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "java.util.*" %>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%
    Transaction rentaltx = null;
      if (session.getAttribute("rentaltx") == null){
        System.out.println("System didn't catch the same transaction");
      rentaltx = TransactionDAO.getInstance().create();
      Store store = new Store("010001117284553c0014b20b500444");
      store.setIsInDB(true);
      store.setDirty(false);
      rentaltx.setType("rental");
      rentaltx.setStore(store);
      Employee emp = new Employee("120001117284553c0014b60a500442");
      emp.setInDB(true);
      emp.setDirty(false);
      rentaltx.setEmployee(emp);
      session.setAttribute("rentaltx", rentaltx.getId());
}else{
        String rentalid = null;
        System.out.println("I remembered!");
        rentalid = (String) session.getAttribute("rentaltx");
        rentaltx = TransactionDAO.getInstance().read(rentalid);
}
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
        <input type="checkbox" name="StoreOpt1" value="1"> Provo<br>
        <input type="checkbox" name="StoreOpt2" value="2"> Logan<br>
        <input type="checkbox" name="StoreOpt3" value="3"> Murray<br>
        
    </td>
</table>
 <input type="submit" value="Search"><br>
        </form>

<table width="600px" cellpadding="5px" cellspacing="10px">
    <tr><th>Available Products</th><th>Shopping Cart</th></tr>
    <tr>
    <td>
        <%if (request.getAttribute("forrent") != null) {
                List<ForRent> rental = new LinkedList<ForRent>();
                rental = (List) request.getAttribute("forrent");
                for(int i = 0; i<rental.size(); i++){
                    double price = ConceptualRentalDAO.getInstance().getRentalPrice(rental.get(i));
                    String name = ConceptualRentalDAO.getInstance().getRentalName(rental.get(i));
                %>The Item Name = <%=name%> The Item Price = <%=price%><br> <%
                    }//end for
                
                }//end if
                %>
    </td>	
    <td>
        The following items will be added to your shopping cart.
        <%
        List<TransactionLine> txline = new LinkedList<TransactionLine>();
        txline = rentaltx.getTxLines();
        for(int i = 0; i<txline.size(); i++){
            double txlineprice = txline.get(i).calculateSubtotal();
            Rental tn = new Rental(txline.get(i).getRevenueSource().getId());
            String txlinename = ConceptualRentalDAO.getInstance().getRentalName(fr);
            %>The Item Name = <%=txlinename%> The Item Price = <%=txlineprice%><br> <%
        }
        System.out.println(rentaltx.getId());
        %>
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
