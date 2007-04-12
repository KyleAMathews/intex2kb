
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>

<%
formatNumber fmt = new formatNumber();

Transaction tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
Membership memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
TransactionLine txLine1 = tx.getTxLines().get(0);
Customer cust = memb.getCustomer();
backup bckup = (backup)txLine1.getRevenueSource();
%>

<jsp:include page="header.jsp" />

<p></p>
<table>
    <thead>
        <tr>
            <th>Backup Purchase Details</th>
            
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
               GBs purchase: <%=bckup.getSize()%>
            </td>
            <td>Price <%=txLine1.getRevenueSource().getPrice()%></td>
        </tr>
        <tr>
            <td>Total (after tax)</td>
            <td>$<%=tx.calculateTotal()%></td>
        </tr>
    </tbody>
</table>
<input style="float: right; margin-right: 5px; padding-right: 0px;" type="submit" value="Checkout">
</form>

<%@ include file="checkout.jsp" %>
<jsp:include page="footer.jsp" />