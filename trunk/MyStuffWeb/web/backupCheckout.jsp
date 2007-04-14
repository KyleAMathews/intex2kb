
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>

<%
formatNumber fmt = new formatNumber();

Transaction tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
Membership memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
TransactionLine txLine1 = tx.getTxLines().get(0);
Customer cust = memb.getCustomer();
backup bckup = (backup)txLine1.getRevenueSource();

session.setAttribute("tx",session.getAttribute("backuptx"));%>

<jsp:include page="header.jsp" />

<p></p>
<table>
    <thead>
        <tr>
            <th>Details of Backup Purchase</th>
            
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
               GBs purchased
            </td>
            <td>
                <%=bckup.getSize()%>
            </td>
        </tr>
        <tr>
            <td>Subtotal</td>
            <td>$<%=fmt.fmt(txLine1.getRevenueSource().getPrice())%></td>
        </tr>
        <tr>
            <td>Total $/month (after tax)</td>
            <td>$<%=fmt.fmt(tx.calculateTotal())%></td>
        </tr>
        
    </tbody>
</table>
<br />
<%@ include file="checkout.jsp" %>
<jsp:include page="footer.jsp" />