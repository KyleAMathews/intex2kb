<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.views.*"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.*"%>
<%@page import="java.util.*"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<%List<photoBackupBO> pb = new LinkedList<photoBackupBO>();
pb = (List<photoBackupBO>)session.getAttribute("files");
if (pb.isEmpty()) {pb.add(new photoBackupBO("No Pictures"));
pb.get(0).setCaption("No Picture");
}
%>

<jsp:include page="header.jsp" />

<div id="rightcolumn">
    <h4>MyStuff Backup</h4>
    <p class="small">75% Used of 10GB</p>
    <br />
    <h4>Actions:</h4>
    <ul>
        <li><a href="showimage.jsp?id=<%=pb.get(0).getId()%>" class="thickbox" rel="gallery">View Slideshow</a></li>
        <li><a href="fileupload.jsp">Upload File(s)</a></li>
        <li><a href="backuptx.jsp">Add Backup Space</a></li>
        <li><a href="">Help</a></li>
    </ul>
</div><!--end right column-->  

<div id="body">
<h1>My Stuff File Backup</h1>
<%=pb.get(0).getFilesize()%>
<%=pb.get(0).getId()%>
<%=pb.get(0).getM()%>
<p>Welcome to you My Stuff File Backup Page</p>
<div><br />
<% if (pb != null){
    Iterator<photoBackupBO> it = pb.iterator();
    
// outer loop
    while(it.hasNext()){
        out.write("<table width=\"500px\" cellpadding=\"5px\" cellspacing=\"10px\"> ");
        
        List<String> id = new LinkedList<String>();
        List<String> caption = new LinkedList<String>();
        String caption1 = null;
        photoBackupBO pbBO = null;
        int j = 0;
// inner loop
        for (int i = 0; i < 4; i++){
            if(it.hasNext()) {pbBO = it.next();
            id.add(pbBO.getId());
            caption1 = pbBO.getCaption();
            if (caption1 == null){caption1 = "caption";}
            caption.add(caption1);
            j++;
            } else{break;}
            
        }
        out.write("<tr>");
        for (int i = 0; i < j; i++){
            
            out.write("<th>");
            out.write("   " + caption.get(i));
            out.write("</th>");
            
        }
        out.write("</tr>");
        out.write("<tr>");
        for (int i = 0; i < j; i++){
            out.write("<td><a href=\"showimage.jsp?id=" + id.get(i) + "\" class=\"thickbox\" rel=\"gallery\"><img class=\"thickbox\" src=\"showthumbnail.jsp?id=" + id.get(i) + "\" title=" + caption.get(i) + "\" /></a><br /><a href=\"downloadfile.jsp?id=" + id.get(i) + "\">Download</a><a href=\"deletefile.jsp?id=" + id.get(i) + "\">Delete</a></td>");
        }
        out.write("</tr>");
        out.write("</table>");
    }
}
%>
<table width="500px" cellpadding="5px" cellspacing="10px">
    <tr><th>Kyle in Snow</th><th>Kyle is snow isn't this a long title?</th><th>Baby J</th></tr>
    <tr>
    <td>
    <a href="images/pic1.jpg" class="thickbox" rel="gallery"><img class="thickbox" src="images/pic1.jpg" title="Kyle in Snow" /></a><br /><a href="downloadfile.jsp?id=03488023840234">Download</a><a href="deletefile.jsp?id=23402384023840">Delete</a></td>	
    <td>
        <a href="images/pic2.jpg" class="thickbox" rel="gallery"><img src="images/pic2.jpg" class="thickbox" rel="gallery" /></a><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a>
    </td>
    <TD><a href="images/pic3.jpg" class="thickbox" rel="gallery"><img src="images/pic3.jpg" class="thickbox" rel="gallery" /></a><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></TD>
    </td>
</table>

<table width="500px" cellpadding="5px" cellspacing="10px">
    <tr><th>Kyle in Snow</th><th>Kyle is snow isn't this a long title?</th><th>Baby J</th></tr>
    <tr>
    <td>
    <img src="images/pic1.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></td>	
    <td>
        <img src="images/pic2.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a>
    </td>
    <TD><img src="images/pic3.jpg" /><br /><a href="download.action">Download</a><a href="deletepic.action">Delete</a></TD>
    </td> 
</table>
</div>

</div><!--end body-->

<jsp:include page="footer.jsp" />
