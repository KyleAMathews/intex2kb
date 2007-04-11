<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.TransactionDAO"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.Transaction"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.TransactionLineDAO"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.TransactionLine"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.MembershipDAO"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.Membership"%>
<%@page import="edu.byu.isys413.cbb54.intex2kb.data.Customer"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%// check if transaction already exists, if not, create one %>
<% Transaction tx = null;
Membership memb = null;
TransactionLine txLine1 = null;

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
    
    System.out.println("backup price these days: " + txLine1.getRevenueSource().getPrice());
    List<TransactionLine> txLineList = new LinkedList<TransactionLine>();
    
    txLineList.add(txLine1);
    
    tx.setTxLines(txLineList);
    System.out.println("customer's name " + memb.getCustomer().getFname() + " " + memb.getBackupExpDate() + " " + memb.getBackupSize());
}else{
    tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
    memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
    txLine1 = tx.getTxLines().get(0);
    System.out.println("customer's name " + memb.getCustomer().getFname() + " " + memb.getBackupExpDate() + " " + memb.getBackupSize());
} 
%>


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
    <h1>Add backup space to your account!</h1>
    <h3>Hi <%=tx.getCustomer().getFname()%></h3>
    <p>You have <%=memb.getBackupSize()%> GB of backup space</p>
    <p>Your backup will expire on <%=memb.getBackupExpDate()%>
    <%=tx.getTxLines().get(0).calculateSubtotal()%>
   <table>
  <caption>Buy more backup space</caption>
  <thead>
    <tr>
      <th>Buy More Backup Space</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td># of GBs (form)</td>
      <td>Price <%=txLine1.getRevenueSource().getPrice()%></td>
    </tr>
    <tr>
      <td>Total</td>
      <td><%=tx.calculateTotal()%></td>
    </tr>
  </tbody>
</table>
</div><!--end of body-->


<jsp:include page="footer.jsp" />