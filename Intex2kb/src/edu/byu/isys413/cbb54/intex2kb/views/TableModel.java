/*
 * TableModel.java
 *
 * Created on November 8, 2006, 2:22 PM
 * Author: Cameron Burgon  cameronb62@byu.edu
 *
 */

package edu.byu.isys413.cbb54.intex2kb.views;
import edu.byu.isys413.cbb54.intex2kb.data.*;



import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.*;



public class TableModel extends AbstractTableModel{
    
    private List<String> header = new LinkedList<String>(); // List to hold header information
    static List<List> data = new LinkedList<List>();        // List to hold song information
    static Connection conn = connection();
    static Statement stmt = null;
    boolean change = false;
    
    public static Connection connection(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/intex2;user=dbuser;password=dbuser;");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    
    /** Constructor */
    public TableModel() {
        
//        checkTable();
        
        // Set column headings
        header.add("#");
        header.add("Description");
        header.add("Qty");
        header.add("Price");
        
        
        
//        try{
//
//
//            stmt = conn.createStatement();
//            ResultSet srs = stmt.executeQuery("SELECT * FROM data");
//
//            while (srs.next()) {
//                List<String> list = new LinkedList<String>();
//                list.add(srs.getString("guid"));
//                list.add(srs.getString("title"));
//                list.add(srs.getString("artist"));
//                list.add(srs.getString("album"));
//
//                addRow(list);
//            }
//
//             stmt.close();
//        } catch (Exception c){
//            c.printStackTrace();
//        }
        
        
    }
    
    public void updateTable(Transaction tx){
        List<TransactionLine> txlnList = tx.getTxLines();
        List<String> temp = new LinkedList<String>();
        
        for (int i = 0;i < txlnList.size();i++){
            temp.add(txlnList.get(i).getId());
            temp.add(txlnList.get(i).getRsType());
            temp.add(Double.toString(txlnList.get(i).getRevenueSource().getPrice()));
        }
        
        data.add(temp);
        fireTableRowsInserted(0,data.size());
    }
    
//    public void checkTable(){
//        boolean tableExists = false;
//
//        try {
//            // Gets the database metadata
//            DatabaseMetaData dbmd = conn.getMetaData();
//
//            // Specify the type of object; in this case we want tables
//            String[] types = {"TABLE"};
//            ResultSet resultSet = dbmd.getTables(null, null, "%", types);
//
//            // Get the table names
//            while (resultSet.next()) {
//                // Get the table name
//                String tableName = resultSet.getString(3);
//
//                // Set tableExists to TRUE if it exists
//                if(tableName.equals("DATA")){
//                    tableExists = true;
//                }
//            }
//
//            // If table does not exists, create table and sample data
//            if(tableExists == false){
//                PreparedStatement create = conn.prepareStatement("CREATE TABLE data(guid varchar(30),title varchar(30),artist varchar(30),album varchar(30) )");
//
//                create.executeUpdate();
//                conn.commit();
//                create.close();
//
//                PreparedStatement insert = conn.prepareStatement("INSERT INTO data VALUES" +
//                        "('0000010f283e9daf000000c0a80264','Oh Very Young','Cat Stevens','Greatest Hits')," +
//                        "('0000010f283e9daf000001c0a80264','Running Out of Days','3 Doors Down','Away from the Sun')," +
//                        "('0000010f283e9daf000002c0a80264','Not Gonna Be Alone Tonight','Eve 6','It''s All In Your Head')," +
//                        "('0000010f283e9daf000003c0a80264','Kristina','Howie Day','Howie Day')," +
//                        "('0000010f283e9daf000004c0a80264','Christmas Nights In Blue','Trans-Siberian Orchestra','The Lost Christmas Eve')," +
//                        "('0000010f283e9daf000005c0a80264','I''ve Got My Eyes On You','Jessica Simpson','Sweet Kisses')," +
//                        "('0000010f283e9daf000006c0a80264','Summertime','Lonestar','Let''s Be Us Again')," +
//                        "('0000010f283e9daf000007c0a80264','See Groove','Nickelback','Curb')," +
//                        "('0000010f283e9daf000008c0a80264','Carry Me','Katherine Nelson','Sometimes He Lets It Rain')");
//
//                insert.executeUpdate();
//                conn.commit();
//                insert.close();
//            }
//
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null,"There was an error creating the sample data.");
//        }
//
//    }
    
    public int getRowCount() {
        return data.size();
    }
    
    public int getColumnCount() {
        return header.size();
    }
    
    public String getColumnName(int col) {
        return header.get(col);
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
    
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }
    
    
    // addRow()
//    public void addRow() throws SQLException, Exception{
//        // Create list to add
//        List<String> temp = new LinkedList();
//
//
//        String title = JOptionPane.showInputDialog("Enter song title");
//        String artist = JOptionPane.showInputDialog("Enter artist name");
//        String album = JOptionPane.showInputDialog("Enter album name");
//        String guid = GUID.generate();
//
//
//        try{
//            PreparedStatement insert = conn.prepareStatement(
//                "INSERT INTO data(guid,title,artist,album) VALUES(?,?,?,?)");
//            insert.setString(1, guid);
//            insert.setString(2, title);
//            insert.setString(3, artist);
//            insert.setString(4, album);
//            insert.executeUpdate();
//            conn.commit();
//            insert.close();
//
//            // Add row to table and update table
//            temp.add(guid);
//            temp.add(title);
//            temp.add(artist);
//            temp.add(album);
//
//            data.add(temp);
//            fireTableRowsInserted(0,data.size());
//        } catch (SQLException e){
//            conn.rollback();
//            throw e;
//        }
//
//    }
    
    // addRow() for constructor()
    public void addRow(List song) {
        
        // add list to data
        data.add(song);
        fireTableRowsInserted(0,data.size());
    }
    
    public void deleteRow(int row) throws SQLException {
        
        // Retrieve the GUID of the row to be deleted
        String guid = getValueAt(row,0).toString().trim();
        
        // Prepare delete statement and execute
        try{
            PreparedStatement delete = conn.prepareStatement(
                    "DELETE FROM data WHERE guid = ?");
            delete.setString(1, guid);
            delete.executeUpdate();
            conn.commit();
            delete.close();
            
            data.remove(row);
            fireTableRowsDeleted(0,row);
        }catch (SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
    
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
    public void updateRow(int oldLine) throws SQLException {
        
        // Retrieve the value from the data list
        String title = getValueAt(oldLine,1).toString().trim();
        String artist = getValueAt(oldLine,2).toString().trim();
        String album = getValueAt(oldLine,3).toString().trim();
        String guid = getValueAt(oldLine,0).toString().trim();
        
        // Prepare the update statement using the variables above and execute
        try{
            PreparedStatement update = conn.prepareStatement(
                    "UPDATE data SET title=?, artist=?, album=? WHERE guid=?");
            update.setString(1, title);
            update.setString(2, artist);
            update.setString(3, album);
            update.setString(4, guid);
            update.executeUpdate();
            conn.commit();
            update.close();
        } catch (SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
    
    
    
    
}
