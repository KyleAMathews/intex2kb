<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<% TransactionDAO.getInstance()              %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
<div id="rightcolumn">
    <h4>MyStuff Backup</h4>
    <p class="small">75% Used of 10GB</p>
    <br />
    <h4>Actions:</h4>
    <ul>
        <li><a href="">View Slideshow</a></li>
        <li><a href="">Upload File(s)</a></li>
        <li><a href="">Add Backup Space</a></li>
        <li><a href="">Help</a></li>
    </ul>
</div><!--end right column-->  
<div id="body">
    
    
</div><!--end of body-->


<jsp:include page="footer.jsp" />