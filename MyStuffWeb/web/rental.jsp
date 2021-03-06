<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "java.util.*" %>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>

<%
    String title = "Rental";
    Transaction rentaltx = null;
    List<Category> categoryList = CategoryDAO.getInstance().getCategorys();
    String category = (String)request.getAttribute("category");
    
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

<jsp:include page="header.jsp" />
 
<div id="body">
<h1><%out.write(title);%></h1>
<p>Please select a category and choose a store location.</p>
<div>
    <table width="600px" cellpadding="5px" cellspacing="10px">
        <tr><th>Select Category</th><th>Select Store</th></tr>
    <tr>
    <td>
        <form action="edu.byu.isys413.actions.GetRentals.action" method="post" target=_parent> 
        <select NAME="category">
            <% 
        for(int i = 0; i<categoryList.size(); i++){
               Category c = categoryList.get(i);
                %>
                <option VALUE="<%= c.getId() %>" <% if( (category != null) && (c.getId().matches((String)request.getParameter("category"))) ){out.print("selected");};%>><%= c.getName()%><br><%}%>
          </select> 
    </td>	
    <td>
        
        <input type="radio" name="Store" value="000001117284553c0014b20a500442" CHECKED> Provo<br>
        <input type="radio" name="Store" value="000001117284553c0014b20a500443"> Logan<br>
        <input type="radio" name="Store" value="000001117284553c0014b20a500444"> Murray<br>
        
    </td>
</table>
 <input type="submit" value="Search"><br><br><br>
        </form>
The following items are available for rent
        <table width="600px" cellpadding="5px" cellspacing="10px">
            <form action = "edu.byu.isys413.actions.AddTXLine.action" method ="post" target=_parent>
                <tr>
                    
                    <td>Select Product</td>
                    <td>Name</td>
                    <td>Price Per Day</td>
                </tr>
                <%if (request.getAttribute("forrent") != null) {
                List<ForRent> rental = new LinkedList<ForRent>();
                rental = (List) request.getAttribute("forrent");
                for(int i = 0; i<rental.size(); i++){
                double price = ConceptualRentalDAO.getInstance().getRentalPrice(rental.get(i));
                String name = ConceptualRentalDAO.getInstance().getRentalName(rental.get(i));
                //String id = (String) rental.get(i).getId();
                %>
                <tr>
                    <td><input type="radio" name ="Rental" value = "<%=rental.get(i).getId()%>"></td>
                    <td><%=name%></td>
                    <td><%=price%></td>
                </tr>
                <%
                }//end for
                
                }//end if
                %>
        </table>
        <input type="submit" value="Add"></form><br><br><br>
            The following items will be added to your shopping cart.
<table>
        
        <tr>
            <td>Name</td>
            <td>Price Per Day</td>
        </tr>
        
        <%
        List<TransactionLine> txline = new LinkedList<TransactionLine>();
        txline = rentaltx.getTxLines();
        for(int i = 0; i<txline.size(); i++){
            double txlineprice = txline.get(i).calculateSubtotal();
            String id = txline.get(i).getRevenueSource().getId();
            System.out.println(id);
            Rental rn = (Rental)Cache.getInstance().get(id);
            ForRent fr = ForRentDAO.getInstance().getByRentalID(rn.getId());
            //String frid = (String) session.getAttribute("frid");
            //System.out.println(frid);
            //ForRent fr = (ForRent)Cache.getInstance().get(frid);
            System.out.println("Here is the for rental guid");
            System.out.println(fr.getId());
            String txlinename = ConceptualRentalDAO.getInstance().getRentalName(fr);
            %> 
            <tr>
                    <td><%=txlinename%></td>
                    <td><%=txlineprice%></td>
                </tr>
            
            <%
        }
        System.out.println(rentaltx.getId());
        %>
    </td>
</table>
<a href = "rentaldisplay.jsp"> Edit your rental items </a> 
</div>

</div><!--end body-->
<jsp:include page="footer.jsp" />
