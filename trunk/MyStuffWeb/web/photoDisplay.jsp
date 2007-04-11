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
<jsp:include page="header.jsp" /> 
<div id="rightcolumn">
    <h4>MyStuff Photo Printing</h4>
</div><!--end right column-->  
<div id="body">
    <h1><%out.write(title);%></h1>
    <p>Welcome to MyStuff Online Photo Printing<br><br>
    Files uploaded.  Please make selections below and click Submit to add print order to cart.</p>
    <div>
        <form method="post" action="edu.byu.isys413.actions.PhotoUpload.action" enctype="multipart/form-data">
            <table width="600px" cellpadding="5px" cellspacing="10px" valign="middle">
                <%if(session.getAttribute("1") != null){%><tr><td>Picture 1:</td><td><%=session.getAttribute("1")%></td>
                <td><select NAME="PrintFormat">
                        <% List<String> sizeList = new LinkedList<String>();
                        sizeList = printFormatDAO.getInstance().getPrintFormat();
                        for(int i = 0; i<sizeList.size(); i++){
                            String value = sizeList.get(i);
                        %>
                        <option VALUE="<%=i%>"><%=value%>
                        <%
                        }
                        %>
                </select></td>
                <td>Qty:<input name="qty1" type=text size="5"></td>
                <%
                }
                %>
                <%if(session.getAttribute("2") != null){%><tr><td>Picture 2:</td><td><%=session.getAttribute("2")%></td>
                <td><select NAME="PrintFormat">
                        <% List<String> sizeList = new LinkedList<String>();
                        sizeList = printFormatDAO.getInstance().getPrintFormat();
                        for(int i = 0; i<sizeList.size(); i++){
                            String value = sizeList.get(i);
                        %>
                        <option VALUE="<%=i%>"><%=value%>
                        <%
                        }
                        %>
                </select></td>
                <td>Qty:<input name="qty2" type=text size="5"></td>
                <%
                }
                %>
                <%if(session.getAttribute("3") != null){%><tr><td>Picture 3:</td><td><%=session.getAttribute("3")%></td>
                <td><select NAME="PrintFormat">
                        <% List<String> sizeList = new LinkedList<String>();
                        sizeList = printFormatDAO.getInstance().getPrintFormat();
                        for(int i = 0; i<sizeList.size(); i++){
                            String value = sizeList.get(i);
                        %>
                        <option VALUE="<%=i%>"><%=value%>
                        <%
                        }
                        %>
                </select></td>
                <td>Qty:<input name="qty3" type=text size="5"></td>
                <%
                }
                %>
                <%if(session.getAttribute("4") != null){%><tr><td>Picture 4:</td><td><%=session.getAttribute("4")%></td>
                <td><select NAME="PrintFormat">
                        <% List<String> sizeList = new LinkedList<String>();
                        sizeList = printFormatDAO.getInstance().getPrintFormat();
                        for(int i = 0; i<sizeList.size(); i++){
                            String value = sizeList.get(i);
                        %>
                        <option VALUE="<%=i%>"><%=value%>
                        <%
                        }
                        %>   
                </select></td>
                <td>Qty:<input name="qty4" type=text size="5"></td>
                <%
                }
                %>
                <%if(session.getAttribute("5") != null){%><tr><td>Picture 5:</td><td><%=session.getAttribute("5")%></td>
                <td><select NAME="PrintFormat">
                        <% List<String> sizeList = new LinkedList<String>();
                        sizeList = printFormatDAO.getInstance().getPrintFormat();
                        for(int i = 0; i<sizeList.size(); i++){
                            String value = sizeList.get(i);
                        %>
                        <option VALUE="<%=i%>"><%=value%>
                        <%
                        }
                        %>
                </select></td>
                <td>Qty:<input name="qty5" type=text size="5"></td>
                <%
                }
                %>
                <tr><td><input type="submit" value="Submit" class="buttonSubmit" /></td></tr>
            </table>
        </form>
    </div>
    
</div><!--end body-->
<jsp:include page="footer.jsp" />
</div><!--end bigcontainer-->
</BODY>
</html>
