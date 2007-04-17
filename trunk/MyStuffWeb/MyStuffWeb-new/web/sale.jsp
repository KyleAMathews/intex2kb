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
    
    saletx = TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
    saletx.getTxLines().get(0);
    System.out.println("customer's name " + memb.getCustomer().getFname() + " " + memb.getBackupExpDate());
}
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
 
<div id="body">
    <h1><%out.write(title);%></h1>
<div>
    <table width="600px" cellpadding="5px" cellspacing="10px">
        <tr><th>Select Category</th><th>Select Stores</th></tr>
    <tr>
    <td>
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
        <form action="edu.byu.isys413.actions.AddItems.action" method="post" target="_parent">
            <table>
                <% if(productlist != null){
                for(int i = 0; i < productlist.size(); i++){
                    Conceptual conc = (Conceptual)productlist.get(i); %>
                    <tr>
                        <td>
                            <input type="radio" value="<%=conc.getId()%>">
                        </td>
                        <td>
                            <%=conc.getName() %>
                        </td>
                        <td>
                            <%=conc.getPrice() %>
                        </td>
                    </tr>
                <%}
                }%>
                
            </table>
            
        </form>
    </td>
</table>



</div>
</div><!--end of body-->


<jsp:include page="footer.jsp" />