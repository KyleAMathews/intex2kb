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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%String title = "Photo Development";%>
<jsp:include page="header.jsp" /> 
<div id="navigation">
    <ul>
        <LI style="margin-top: 0;"><a href="sale.jsp">Sale</a></LI>
        <LI><a href="rental.jsp">Rental</a></LI>
        <LI><a href="photo.jsp">Photo</a></LI>
        <LI><a href="backup.jsp">Backup</a></LI>
    </ul>
</div><!--end navigation-->
<div id="rightcolumn">
    <h4>MyStuff Photo Printing</h4>
    <hr><%MembershipDAO.getInstance().read(session.getAttribute("membid"))%></hr>
</div><!--end right column-->  
<div id="body">
    <h1><%out.write(title);%></h1>
    <p>Welcome to MyStuff Online Photo Printing<br><br>
    Files uploaded.  Please make selections below and click Submit to add print order to cart.</p>
    <div>
        <form method="post" action="edu.byu.isys413.actions.PhotoUpload.action" enctype="multipart/form-data">
            <table width="600px" cellpadding="5px" cellspacing="10px" valign="middle">
                <tr><td><input type="submit" value="Submit" class="buttonSubmit" /></td></tr>
            </table>
        </form>
    </div>
    
</div><!--end body-->
<jsp:include page="footer.jsp" />
</div><!--end bigcontainer-->
</BODY>
</html>
