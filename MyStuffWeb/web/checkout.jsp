<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.*"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="header.jsp" />
<%Membership m = MembershipDAO.getInstance().read(session.getAttribute("membid"));
Customer cust = m.getCustomer();
Transaction tx = null;
%>
<div id="body">
    <% String txType = (String)request.getAttribute("checkoutTxType");
    if (txType.equals("ba")){
    out.write("<jsp:include page=\"backupCheckout.jsp\" />");    
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
    }else if(txType.equals("re")){
    out.write("<jsp:include page=\"rentalCheckout.jsp\" />"); 
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("rentaltx"));
    }else if(txType.equals("po")){
    out.write("<jsp:include page=\"photoCheckout.jsp\" />");    
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("tx"));
    }else if(txType.equals("sa")){
    out.write("<jsp:include page=\"saleCheckout.jsp\" />");  
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
    }
    %>
    <%//=checkoutView.getMembInfo()%>
    
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
                <td><%=request.getAttribute("Address1")%><br><%=request.getAttribute("Address2")%></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><%=cust.getEmail()%></td>
            </tr>
            <tr>
                <td>Payment</td>
                <td>Your credit card will be charged: $<%=tx.calculateTotal()%></td>
            </tr>
        </tbody>
    </table>
</div>

<jsp:include page="footer.jsp" />
