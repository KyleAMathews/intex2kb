<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.actions.checkoutView"%>
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
<div id="body">
    <%=checkoutView.getTx((String)session.getAttribute("checkoutTxType"))%>
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
                <td></td>
            </tr>
            <tr>
                <td>Address</td>
                <td></td>
            </tr>
            <tr>
                <td>Email</td>
                <td></td>
            </tr>
            <tr>
                <td>Payment</td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>
<jsp:include page="footer.jsp" />
