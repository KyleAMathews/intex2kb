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
<%
String rentalid = null;
        rentalid = (String) session.getAttribute("rentaltx");
     Transaction rentaltx = TransactionDAO.getInstance().read(rentalid);
     String storeid = (String) session.getAttribute("storerental");
     Store store = StoreDAO.getInstance().read(storeid);
%>

<jsp:include page="header.jsp" />  

<div id="body">
<p>Edit Rental Details.</p>
    
<table>
    <form action="edu.byu.isys413.actions.RentalCheckout.action" method="post" target=_parent>
        <tr>
            <td>Store</td>
            <td>Name</td>
            <td>Price Per Day</td>
            <td>Number of Days</td>
        </tr>
        
        <%
        List<TransactionLine> txline = new LinkedList<TransactionLine>();
        txline = rentaltx.getTxLines();
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
            <td><%=store.getName()%></td>
            <td><%=txlinename%></td>
            <td><%=txlineprice%></td>
            <td><select NAME="<%=txline.get(i).getId()%>">
                <option VALUE="1">1
                <option VALUE="2">2
                <option VALUE="3">3
                <option VALUE="4">4
                <option VALUE="5">5
                <option VALUE="6">6
                <option VALUE="7">7
                <option VALUE="8">8
                <option VALUE="9">9
                <option VALUE="10">10
            </select>
            <%
            }
            %>
        </tr>
        </td>
    </table>
<input type="submit" value="Checkout"></form>


</div><!--end body-->
<jsp:include page="footer.jsp" />
