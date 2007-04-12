<form method="post" action="edu.byu.isys413.actions.submitTX.action">
    <table>
        <thead>
            <tr>
                <th>Billing + Shipping Info</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Name</td>
                <td><%=cust.getFname() + " " + cust.getLname()%></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><%=cust.getAddress1()%><br><%=cust.getAddress2()%><br>
                <%=cust.getCity()%>, <%=cust.getState()%> <%=cust.getZip()%></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><%=cust.getEmail()%></td>
            </tr>
            <tr>
                <td>Payment</td>
                <td>Your credit card will be charged: $<%=fmt.fmt(tx.calculateTotal())%></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" class="buttonSubmit" /></td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>
