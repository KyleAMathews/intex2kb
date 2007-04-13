<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.backup"%>
<%@page import= "edu.byu.isys413.cbb54.intex2kb.data.*" %>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%// check if transaction already exists, if not, create one %>
<% Transaction tx = null;
Membership memb = null;
TransactionLine txLine1 = null;
backup bck = null;
formatNumber fmt = new formatNumber();

// if a backup transaction has not been created, one is created and populated with a customer object
// else, if previously created, the transaction is read off the database
if (session.getAttribute("backuptx") == null) {
    tx = TransactionDAO.getInstance().create();
    session.setAttribute("backuptx", tx.getId());
    System.out.println(session.getAttribute("membid"));
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
    System.out.println("customer's name " + memb.getCustomer().getFname());
    tx.setCustomer(memb.getCustomer());
    
// create blank transactionline
    System.out.println("Creating a new TransactionLine");
    txLine1 = TransactionLineDAO.getInstance().create(tx, "ba");
    txLine1.getRevenueSource().setPrice(0.0);
    
//cast for backup object
    bck = (backup)txLine1.getRevenueSource();
    
    System.out.println("backup price these days: " + bck.getBkPrice());
    List<TransactionLine> txLineList = new LinkedList<TransactionLine>();
    
    txLineList.add(txLine1);
    
    tx.setTxLines(txLineList);
    System.out.println("customer's name " + memb.getCustomer().getFname() + " " + memb.getBackupExpDate() + " " + memb.getBackupSize());
    tx.setType("backup");
}else{
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
    tx.setType("backup");
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
    txLine1 = tx.getTxLines().get(0);
    
//cast for backup object
    bck = (backup)txLine1.getRevenueSource();
} 
%>


<jsp:include page="header.jsp" />
<%session.setAttribute("checkoutTxType","ba");%>

<div id="rightcolumn">
    <h4>MyStuff Backup</h4>
    <p class="small">75% Used of 10GB</p>
    <br />
    <h4>Actions:</h4>
    <ul>
        <li><a href="">View Slideshow</a></li>
        <li><a href="fileupload.jsp">Upload File(s)</a></li>
        <li><a href="backuptx.jsp">Add Backup Space</a></li>
        <li><a href="">Help</a></li>
    </ul>
</div><!--end right column-->  

<div id="body">
<h1>Add backup space to your account!</h1>
<h3>Hi <%=tx.getCustomer().getFname()%></h3>
<p>You have <%=memb.getBackupSize()%> GB of backup space</p>
<p>Your backup will expire on <%=memb.getBackupExpDate()%>

<table>
<thead>
    <tr>
        <th>Buy More Backup Space</th>
        <th>Price per GB per Month: <%=fmt.fmt(bck.getBkPrice())%></th>
    </tr>
</thead>
<tbody>
<tr>
    <td>
        <form action="edu.byu.isys413.actions.backuptx.action" method="post">
        <label for="numGBs"># of GBs to add to account</label>
        <input type="text" name="numGBs" />
    </td>
    <td><%=bck.getSize()%> GBs will be added to your account</td>
</tr>
<tr>
<td><input style="float: right; margin-right: 5px; padding-right: 0px;" type="submit" value="Checkout">
</form></td>
<td>Total $/month (after tax) $<%=fmt.fmt(tx.calculateTotal())%></td>
</tr>
</tbody>
</table>
<br />

</div><!--end of body-->


<jsp:include page="footer.jsp" />