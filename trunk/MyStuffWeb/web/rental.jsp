<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "java.util.*" %>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>

<%
    String title = "Rental";
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

<jsp:include page="header.jsp" />
 
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
 <input type="submit" value="Search"><br><br>
        </form>

<table width="600px" cellpadding="5px" cellspacing="10px">
    <tr><th>Available Products</th><th>Shopping Cart</th></tr>
    <tr>
    <td>
        <table>
            <form action = "edu.byu.isys413.actions.AddTXLine.action" method ="post" target=_parent>
            <tr>
                <td>Select Product</td>
                <td>Name</td>
                <td>Price</td>
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
            <input type="submit" value="Add"></form><br><br>
        </table><br>
        
    </td>
    <td>
        The following items will be added to your shopping cart.
        <%
        List<TransactionLine> txline = new LinkedList<TransactionLine>();
        txline = rentaltx.getTxLines();
        System.out.println("preparing to print out the transaction lines");
        System.out.println(txline);
        for(int i = 0; i<txline.size(); i++){
            double txlineprice = txline.get(i).calculateSubtotal();
            Rental rn = new Rental(txline.get(i).getRevenueSource().getId());
            ForRent fr = ForRentDAO.getInstance().getByRentalID(rn.getId());
            String txlinename = ConceptualRentalDAO.getInstance().getRentalName(fr);
            %>The Item Name = <%=txlinename%> The Item Price = <%=txlineprice%><br> <%
        }
        System.out.println(rentaltx.getId());
        %>
    </td>
</table>
</div>

</div><!--end body-->
<jsp:include page="footer.jsp" />
