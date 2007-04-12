
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>

<%Membership m = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
Customer cust = null; 
cust = m.getCustomer();
Transaction tx = null;
formatNumber fmt = new formatNumber();

tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
%>

<jsp:include page="header.jsp" />

backup transaction

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
                <td>Your credit card will be charged: $<%=fmt.fmt(tx.calculateTotal())%></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" class="buttonSubmit" /></td>
                <td></td>
            </tr>
        </tbody>
    </table>
    </div>
</form>

<jsp:include page="footer.jsp" />