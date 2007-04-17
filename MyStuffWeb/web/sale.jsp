<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%@page import="java.util.*" %>

<%

// initialize variables
String title = "Sale";
Transaction saletx = null;
Membership memb = null;
String category = null;
List<Conceptual> productlist = null;
List<Category> categoryList = CategoryDAO.getInstance().getCategorys();
List<TransactionLine> transLines = null;

// create a new sale transaction for the session if one does not already exist
if (session.getAttribute("saletx") == null){
    saletx = TransactionDAO.getInstance().create();
    Cache.getInstance().put(saletx.getId(),saletx);
    Store store = new Store("010001117284553c0014b20b500444");
    store.setIsInDB(true);
    store.setDirty(false);
    saletx.setType("sale");
    saletx.setStore(store);
    Employee emp = new Employee("120001117284553c0014b60a500442");
    emp.setInDB(true);
    emp.setDirty(false);
    saletx.setEmployee(emp);
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
    saletx.setCustomer(memb.getCustomer());
    session.setAttribute("saletx", saletx.getId());
    
// retrieve the transaction, transactionlines, and membership from the session    
}else{
    saletx = (Transaction)TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
    transLines = saletx.getTxLines();
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
}


// catch and pass the category if it exists
if (request.getAttribute("category") != null){
    category = (String)request.getAttribute("category");
    request.setAttribute("category", category);
}

// catch and pass the products if they exists
if(request.getAttribute("products") != null){
    productlist = (List)request.getAttribute("products");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
 
<div id="body">
    <h1><%out.write(title);%></h1>
<div>
    <p>Please select items to purchase by selecting a category from the drop-down menu first and then selecting the individual item.  When you are satisfied with your cart's contents please click the "Checkout" button below.</p><br>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr><th colspan="2">Select Category</th><th>Select Product</th></tr>
    <tr>
        <td width="10%" valign="top" align="center">
            <form action="edu.byu.isys413.actions.GetItems.action" method="post" target=_parent>
            <select NAME="category" style="display: inline;">
                <% 
            // loop through category list and populate the category drop-down
            for(int i = 0; i<categoryList.size(); i++){
                   Category c = categoryList.get(i);
                    %>
                    <option VALUE="<%= c.getId() %>" <% if( (category != null) && (c.getId().matches((String)request.getParameter("category"))) ){out.print("selected");};%>><%= c.getName()%><br><%}%>
              </select> </td>
              <td width="10%" valign="top"><input type="submit" style="float:right" value="Get Items">
              
            </form>
        </td>	
        <td>
            <% 
            // display the products for the category if they exist
            if(productlist != null){ %>
            <form action="edu.byu.isys413.actions.AddItems.action" method="post" target="_parent">
                <input type="hidden" name="category" value="<%=category %>">
                <table width="100%"><% for(int i = 0; i < productlist.size(); i++){Conceptual conc = (Conceptual)productlist.get(i); %>
                    <tr>
                        <td valign="center" align="center">
                            <input type="radio" value="<%=conc.getId()%>" name="item">
                        </td>
                        <td>
                            <%=conc.getName() %>
                        </td>
                        <td align="right">
                            <%=formatNumber.fmt(conc.getPrice()) %>
                        </td>
                    </tr><%} %>
                    <tr>
                        <td colspan="3"><input align="right" type="submit" value="Add To Cart"></td>
                    </tr>
                </table>
            </form>
           <% }%>
        </td>
    </tr>
    <tr>
        <th colspan="3">Items in Cart</th>
    </tr>
    
    
    <tr>
        <td colspan="3">
            <table width="100%">
                <% 
                     // loop through the transaction lines and display
                 if(transLines != null){
                     for(int i=0; i < transLines.size(); i++){
                        TransactionLine tl = transLines.get(i); 
                        Sale s = (Sale)tl.getRevenueSource();
                        Conceptual c = (Conceptual)s.getProduct();%>
                        <tr><form action="edu.byu.isys413.actions.UpdateQuantity.action" method="post" target="_parent"><input type="hidden" name="category" value="<%=category %>">
                                <td width="3"><input type="text" size=1 name="quantity" value="<%=s.getQuantity() %>"></td>
                                <td width="5"><input type="hidden" value="<%=i %>" name="update"><input type="submit" value="Update Qty"></td>
                                <td><%=c.getName() %></td>
                            <td align="right"><%=formatNumber.fmt(tl.calculateSubtotal()) %></td></form>
                    </tr>
                    <% } 
                } %>
                    <tr>
                        <td colspan="4" align="right" style="border-top: 1"><b>Subtotal&nbsp;&nbsp; $<%=formatNumber.fmt(saletx.calculateSubtotal()) %></b></td>
                    </tr>
                    <tr>
                        <form action="SaleCheckout.jsp" method="post" target="_parent">
                        <td colspan="3">&nbsp;</td>
                        <td align="center"><input style="float:right" type="submit" value="Checkout"></td>
                        </form>
                    </tr>
            </table>
        </td>
    </tr>
</table>



</div>
</div><!--end of body-->


<jsp:include page="footer.jsp" />