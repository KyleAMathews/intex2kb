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

tx = TransactionDAO.getInstance().read((String)session.getAttribute("rentaltx"));
List<TransactionLine> txLnList = tx.getTxLines();
tx.setCustomer(cust);
%>
   
<jsp:include page="header.jsp" />   

<div id="body">
<p>Rental Purchase Details.</p>


<table>
        
        <tr>
            <td>Store</td>
            <td>Name</td>
            <td>Total price for rental</td>
        </tr>
        
        <%
        List<TransactionLine> txline = new LinkedList<TransactionLine>();
        txline = tx.getTxLines();
        for(int i = 0; i<txline.size(); i++){
            double txlineprice = txline.get(i).calculateSubtotal();
            String id = txline.get(i).getRevenueSource().getId();
            System.out.println(id);
            Rental rn = (Rental)Cache.getInstance().get(id);
            ForRent fr = ForRentDAO.getInstance().getByRentalID(rn.getId());
            //String frid = (String) session.getAttribute("frid");
            //System.out.println(frid);
            //ForRent fr = (ForRent)Cache.getInstance().get(frid);
            System.out.println("Here is the for rental guid");
            System.out.println(fr.getId());
            String txlinename = ConceptualRentalDAO.getInstance().getRentalName(fr);
            %> 
            <tr>
                    <td>Store</td>
                    <td><%=txlinename%></td>
                    <td><%=txlineprice%></td>
                </tr>
            
            <%
        }
        %>
    </td>
</table>
<%session.setAttribute("tx",tx.getId());%>
<%@ include file="checkout.jsp" %>    

    
</div><!--end body-->
<jsp:include page="footer.jsp" />