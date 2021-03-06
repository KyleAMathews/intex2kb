<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%@page import= "java.util.*" %>
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
<%String title = "Photo Development";%>
<%Membership m = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
Customer cust = null; 
cust = m.getCustomer();
Transaction tx = null;
formatNumber fmt = new formatNumber();
tx = TransactionDAO.getInstance().read((String)session.getAttribute("phototx"));
List<TransactionLine> txLnList = tx.getTxLines();
%>
<jsp:include page="header.jsp" />
<p></p>
<table>
    <thead>
        <tr>
            <th>Print Order Purchase Details</th>
        </tr>
    </thead>
    <tbody>
        <%
        formatNumber format = new formatNumber();
        int index = 1;
        for(int i = 0; i < txLnList.size();i++){
        String desc = (String)session.getAttribute("FileName" + (index));
        String pf = (String)session.getAttribute("pf" + (index));
        String qty = (String)session.getAttribute("qty" + (index));
        String lnTotal = format.fmt(txLnList.get(i).calculateSubtotal());
        %>
        <tr>
            <td><%=desc%></td>
            <td><%=pf%></td>
            <td><%=qty%></td>
            <td><%=lnTotal%></td>
        </tr>
        <%
        index++;}
        %>
    </tbody>
</table>
<%session.setAttribute("tx",tx.getId());%>
<%@ include file="checkout.jsp" %>
<jsp:include page="footer.jsp" />
</div><!--end bigcontainer-->
</BODY>
</html>
