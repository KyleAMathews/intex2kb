<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*"%>
<%@page import="java.util.*" %>

<%
String title = "Sale";
String category = (String)request.getAttribute("category");
List<Conceptual> productlist = (List)request.getAttribute("products");
Transaction saletx = null;
Membership memb = null;
List<Category> categoryList = CategoryDAO.getInstance().getCategorys();
List<TransactionLine> transLines = null;


if (request.getAttribute("saletx") == null){
    saletx = TransactionDAO.getInstance().create();
    Store store = new Store("010001117284553c0014b20b500444");
    store.setIsInDB(true);
    store.setDirty(false);
    saletx.setType("sale");
    saletx.setStore(store);
    Employee emp = new Employee("120001117284553c0014b60a500442");
    emp.setInDB(true);
    emp.setDirty(false);
    saletx.setEmployee(emp);
    session.setAttribute("saletx", saletx);
//TransactionLine txln = TransactionLineDAO.getInstance().create(tx, "rn");
//rentaltx.addTxLine(txln);
}else{
    
    saletx = (Transaction)session.getAttribute("saletx");
    transLines = saletx.getTxLines();
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
}
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
 
<div id="body">
    <h1><%out.write(title);%></h1>
<div>
    <table width="100%" cellpadding="5px" cellspacing="1px">
        <tr><th>Select Category</th><th>Select Product</th></tr>
    <tr>
        <td width="25%" valign="top" align="center">
            <form action="edu.byu.isys413.actions.GetItems.action" method="post" target=_parent>
            <select NAME="category">
                <% 
            for(int i = 0; i<categoryList.size(); i++){
                   Category c = categoryList.get(i);
                    %>
                    <option VALUE="<%= c.getId() %>" <% if( (category != null) && (c.getId().matches((String)request.getParameter("category"))) ){out.print("selected");};%>><%= c.getName()%><br><%}%>
              </select> 
              <input type="submit" value="Get Items"><br>
            </form>
        </td>	
        <td>
            <% if(productlist != null){ %>
            <form action="edu.byu.isys413.actions.AddItems.action" method="post" target="_parent">
                <table width="100%">
                    
                    <% for(int i = 0; i < productlist.size(); i++){
                        Conceptual conc = (Conceptual)productlist.get(i); %>
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
                        </tr>
                    <%} %>
                    
                    <tr>
                        <td colspan="3"><input type="submit" value="Add To Cart"></td>
                    </tr>
                </table>
            </form>
           <% }%>
        </td>
    </tr>
    <tr>
        <th colspan="2">Items in Cart</th>
    </tr>
    
    <% if(transLines != null){%>
    <tr>
        <td colspan="2">
            <table>
                    <% for(int i=0; i < transLines.size(); i++){
                        TransactionLine tl = transLines.get(i); 
                        Sale s = (Sale)tl.getRevenueSource();
                        Conceptual c = (Conceptual)s.getProduct();%>
                        <tr>
                            <td><%=c.getName() %></td>
                            <td><%=c.getPrice() %></td>
                        </tr>
                    <% } %>
            </table>
        </td>
    </tr>
    <% } %>
</table>



</div>
</div><!--end of body-->


<jsp:include page="footer.jsp" />