
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

<%@ include file="checkout.jsp" %>
<jsp:include page="footer.jsp" />