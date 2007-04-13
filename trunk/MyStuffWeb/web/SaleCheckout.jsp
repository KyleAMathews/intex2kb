<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%@page import="java.util.*" %>

<%
String title = "Sale Checkout";
Transaction saletx = null;
Membership memb = null;
List<TransactionLine> transLines = null;


saletx = (Transaction)TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
transLines = saletx.getTxLines();
memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="header.jsp" />
 
<div id="body">
    <h1><%out.write(title);%></h1>
<div>
    <p> Please review your cart's contents for accuracy.  Please press the "Edit Cart" button if you need to change the contents of your cart in any way.
    Once you are satisfied with your cart's contents please click the "Continue" button below.</p><br>
    <table width="100%" cellpadding="3px" cellspacing="0">
        <tr><th colspan="3">Shopping Cart</th></tr>
        <tr>
            <td colspan="2">
                <table width="100%">
                    <% for(int i=0; i < transLines.size(); i++){
                        TransactionLine tl = transLines.get(i); 
                        Sale s = (Sale)tl.getRevenueSource();
                        Conceptual c = (Conceptual)s.getProduct();%>
                        <tr>
                            <td width="3"><%=s.getQuantity() %></td>
                            <td><%=c.getName() %></td>
                            <td align="right"><%=formatNumber.fmt(tl.calculateSubtotal()) %></td></form>
                        </tr><% } %>
                        <tr>
                            <td colspan="3" align="right" style="border-top: 1"><b>Subtotal&nbsp;&nbsp; $<%=formatNumber.fmt(saletx.calculateSubtotal()) %></b></td>
                        </tr>
                        <tr>
                            <td colspan="2" align="left"><form action="sale.jsp" methode="post" target="_parent"><input type="submit" value="Edit Cart"></form></td>
                            <form action="SaleConfirmation.jsp" method="post" target="_parent">
                            <td align="center"><input style="float:right" type="submit" value="Continue"></td>
                            </form>
                        </tr>
                </table>
            </td>
        </tr>
    </table>



</div>
</div><!--end of body-->


<jsp:include page="footer.jsp" />