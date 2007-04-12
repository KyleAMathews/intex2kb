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
<%session.setAttribute("phototx",session.getAttribute("phototx"));%>
<jsp:include page="header.jsp" /> 
<div id="rightcolumn">
    <h4>MyStuff Photo Printing</h4>
</div><!--end right column-->  
<div id="body">
    <h1><%out.write(title);%></h1>
    <p>Welcome to MyStuff Online Photo Printing<br><br>
    Files uploaded.  Please make selections below and click Submit to add print order to cart.</p>
    <div>
        <form method="post" action="edu.byu.isys413.actions.PhotoCheckout.action">
            <table width="600px" cellpadding="5px" cellspacing="10px" valign="middle">
                <%if(session.getAttribute("1") != null){%><tr><td>Picture 1:</td><td><%=session.getAttribute("1")%></td>
                    <td><select NAME="PrintFormat1">
                            <% List<String> sizeList = new LinkedList<String>();
                            sizeList = printFormatDAO.getInstance().getPrintFormat();
                            for(int i = 0; i<sizeList.size(); i++){
                                String value = sizeList.get(i);
                            %>
                            <option VALUE="<%=i%>"><%=value%>
                            <%
                            session.setAttribute("FileName1",session.getAttribute("1"));}
                            %>
                    </select></td>
                    <td>Qty:<input name="qty1" type=text size="5" value="0"></td>
                    <%
                    }
                    %>
                </tr>
                <%if(session.getAttribute("2") != null){%><tr><td>Picture 2:</td><td><%=session.getAttribute("2")%></td>
                    <td><select NAME="PrintFormat2">
                            <% List<String> sizeList = new LinkedList<String>();
                            sizeList = printFormatDAO.getInstance().getPrintFormat();
                            for(int i = 0; i<sizeList.size(); i++){
                                String value = sizeList.get(i);
                            %>
                            <option VALUE="<%=i%>"><%=value%>
                            <%
                            session.setAttribute("FileName2",session.getAttribute("2"));}
                            %>
                    </select></td>
                    <td>Qty:<input name="qty2" type=text size="5" value="0"></td>
                    <%
                    }
                    %>
                </tr>
                <%if(session.getAttribute("3") != null){%><tr><td>Picture 3:</td><td><%=session.getAttribute("3")%></td>
                    <td><select NAME="PrintFormat3">
                            <% List<String> sizeList = new LinkedList<String>();
                            sizeList = printFormatDAO.getInstance().getPrintFormat();
                            for(int i = 0; i<sizeList.size(); i++){
                                String value = sizeList.get(i);
                            %>
                            <option VALUE="<%=i%>"><%=value%>
                            <%
                            session.setAttribute("FileName3",session.getAttribute("3"));}
                            %>
                    </select></td>
                    <td>Qty:<input name="qty3" type=text size="5" value="0"></td>
                    <%
                    }
                    %>
                </tr>
                <%if(session.getAttribute("4") != null){%><tr><td>Picture 4:</td><td><%=session.getAttribute("4")%></td>
                    <td><select NAME="PrintFormat4">
                            <% List<String> sizeList = new LinkedList<String>();
                            sizeList = printFormatDAO.getInstance().getPrintFormat();
                            for(int i = 0; i<sizeList.size(); i++){
                                String value = sizeList.get(i);
                            %>
                            <option VALUE="<%=i%>"><%=value%>
                            <%
                            session.setAttribute("FileName4",session.getAttribute("4"));}
                            %>   
                    </select></td>
                    <td>Qty:<input name="qty4" type=text size="5" value="0"></td>
                    <%
                    }
                    %>
                </tr>
                <%if(session.getAttribute("5") != null){%><tr><td>Picture 5:</td><td><%=session.getAttribute("5")%></td>
                    <td><select NAME="PrintFormat5">
                            <% List<String> sizeList = new LinkedList<String>();
                            sizeList = printFormatDAO.getInstance().getPrintFormat();
                            for(int i = 0; i<sizeList.size(); i++){
                                String value = sizeList.get(i);
                            %>
                            <option VALUE="<%=i%>"><%=value%>
                            <%
                            session.setAttribute("FileName5",session.getAttribute("5"));}
                            %>
                    </select></td>
                    <td>Qty:<input name="qty5" type=text size="5" value="0"></td>
                    <%
                    }
                    %>
                </tr>
                <tr><td><input type="submit" value="Submit" class="buttonSubmit" /></td>
                    <td>Pickup Location:<br>
                        <%List<Store> storeList = new LinkedList<Store>();
                        storeList = StoreDAO.getInstance().getAll();
                        for(int i = 0; i < storeList.size(); i++){
                            String value = null;
                            if(storeList.get(i).getName().matches("Online Store")){
                                break;
                            }else{
                                value = storeList.get(i).getName();
                            }
                        %>
                        <input type="radio" name="group1" value="<%=value%>"><%=value%><br>
                        <%
                        }
                        %>
                    </td>
                </tr>
                
            </table>
        </form>
    </div>
    
</div><!--end body-->
<jsp:include page="footer.jsp" />
</div><!--end bigcontainer-->
</BODY>
</html>
