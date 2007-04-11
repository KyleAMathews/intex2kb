<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>
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
<%session.setAttribute("tx",TransactionDAO.getInstance().create());%>
<div id="rightcolumn">
    <h4>MyStuff Photo Printing</h4>
</div><!--end right column-->  
<div id="body">
    <h1><%out.write(title);%></h1>
    <p>Welcome to MyStuff Online Photo Printing<br><br>
    Please use the following input boxes to upload up to five (5) photos to be printed.</p>
    <div>
        <form method="post" action="edu.byu.isys413.actions.PhotoUpload.action" enctype="multipart/form-data">
            <table width="600px" cellpadding="5px" cellspacing="10px" valign="middle">
                <tr><td>Picture 1:</td><td><input type="file" name="datafile1" size="32"></td></tr>
                <tr><td>Picture 2:</td><td><input type="file" name="datafile2" size="32"></td></tr>
                <tr><td>Picture 3:</td><td><input type="file" name="datafile3" size="32"></td></tr>
                <tr><td>Picture 4:</td><td><input type="file" name="datafile4" size="32"></td></tr>
                <tr><td>Picture 5:</td><td><input type="file" name="datafile5" size="32"></td></tr>
                <tr><td><input type="submit" value="Submit" class="buttonSubmit" /></td></tr>
            </table>
        </form>
    </div>
    
</div><!--end body-->
<jsp:include page="footer.jsp" />
</div><!--end bigcontainer-->
</BODY>
</html>
