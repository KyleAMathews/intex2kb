<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%@page import= "java.util.*" %>

<%
String title = "Confirmation";
Transaction tx = null;
Membership memb = null;
Customer cust = null;
List<TransactionLine> transLines = null;


tx = (Transaction)TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
transLines = tx.getTxLines();
memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
cust = memb.getCustomer();
session.setAttribute("tx",tx.getId());
session.setAttribute("cust",cust.getId());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="header.jsp" />   

<div id="body">
<p>Rental Purchase Details.</p>


<table>
        
        <tr>
            <th colspan="3">Shopping Cart</th>
        </tr>
        
       <% for(int i=0; i < transLines.size(); i++){
            TransactionLine tl = transLines.get(i); 
            Sale s = (Sale)tl.getRevenueSource();
            Conceptual c = (Conceptual)s.getProduct();%>
            <tr>
                <td width="3"><%=s.getQuantity() %></td>
                <td><%=c.getName() %></td>
                <td align="right"><%=formatNumber.fmt(tl.calculateSubtotal()) %></td></form>
            </tr><% } %>
    </td>
</table>


<form method="post" action="edu.byu.isys413.actions.submitTX.action">
    <table>
        <thead>
            <tr>
                <th>Billing + Shipping Info</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Name</td>
                <td><%=cust.getFname() + " " + cust.getLname()%></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><%=cust.getAddress1()%><br><%=cust.getAddress2()%><br>
                <%=cust.getCity()%>, <%=cust.getState()%> <%=cust.getZip()%></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><%=cust.getEmail()%></td>
            </tr>
            <tr>
                <td>Payment</td>
                <td>Your credit card will be charged: $<%=formatNumber.fmt(tx.calculateTotal())%></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" class="buttonSubmit" /></td>
                <td></td>
            </tr>
        </tbody>
    </table>
    </div>
</form>
    
</div><!--end body-->
<jsp:include page="footer.jsp" />