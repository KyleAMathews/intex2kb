/*
 * tester.java
 *
 * Created on February 21, 2007, 4:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;


import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A simple tester class for my data layer.
 *
 * @author Conan C. Albrecht
 */
public class tester {
    
    public void main(String args[]) {
        
        try {
            
            // clear out the database (you'd never do this in production)
            Connection conn = ConnectionPool.getInstance().get();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM \"customer\" ");
            stmt.executeUpdate("DELETE FROM \"membership\" ");
            stmt.executeUpdate("DELETE FROM \"interest\" ");
            stmt.close();
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            /**
             *
             *    TEST CUSTOMERS
             *
             */
            
            System.out.println("CUSTOMER TESTING");
            // create a test customer
            Customer cust1 = CustomerDAO.getInstance().create();
            cust1.setFname("Test");
            cust1.setLname("Entry");
            cust1.setAddress1("123 Somewhere");
            cust1.setAddress2(null);
            cust1.setCity("Over The");
            cust1.setState("Rainbow");
            cust1.setZip("12345");
            cust1.setPhone("5555555555");
            cust1.setEmail("jollypeople@mymonkeylovesme.com");
            CustomerDAO.getInstance().save(cust1);
            
            Customer cust2 = CustomerDAO.getInstance().create();
            cust2.setFname("Test");
            cust2.setLname("Entry");
            cust2.setAddress1("123 Somewhere");
            cust2.setAddress2(null);
            cust2.setCity("Over The");
            cust2.setState("Rainbow");
            cust2.setZip("12345");
            cust2.setPhone("3456443454");
            CustomerDAO.getInstance().save(cust2);
            
            // test that the exact same object will come out of the cache if asked for
            Customer cust3 = CustomerDAO.getInstance().read(cust1.getId());
            System.out.println("cust1==cust2 -> " + (cust1 == cust3));
            
            // test if all three customer objects are in the Cache
            System.out.println("cust1 in Cache -> " + Cache.getInstance().containsKey(cust1.getId()) );
            System.out.println("cust2 in Cache -> " + Cache.getInstance().containsKey(cust2.getId()) );
            System.out.println("cust3 in Cache -> " + Cache.getInstance().containsKey(cust3.getId()) );
            
            // test the update(save)
            cust2.setFname("Tester");
            cust2.setPhone("1234567890");
            CustomerDAO.getInstance().save(cust2);
            
            // test getAll()
            List allCustomers = (List)CustomerDAO.getInstance().getAll();
            System.out.println(allCustomers.size());
            for(int i = 0; i < allCustomers.size(); i++){
                List temp = (List)allCustomers.get(i);
                
                System.out.println( temp.get(1).toString() + " " + temp.get(2).toString() + " " + temp.get(3).toString());
            }
            
            // test getByName()
            List testCustomer = (List)CustomerDAO.getInstance().getByName("Test","Entry");
            for(int i = 0; i < testCustomer.size(); i++){
                Customer custTest = (Customer)testCustomer.get(i);
                
                System.out.println("Finding customer by name \"Test Entry\" :" + custTest.getFname() + " " + custTest.getLname() );
            }
            
            // test getByPhone()
            List testCustPhone = (List)CustomerDAO.getInstance().getByPhone("1234567890");
            for(int i = 0; i < testCustPhone.size(); i++){
                Customer custPhone = (Customer)testCustPhone.get(i);
                System.out.println("Finding Customer by phone \"1234567890\" :" + custPhone.getFname() + " " + custPhone.getLname() );
            }
            
            
            /**
             *
             *  TEST MEMBERSHIP
             *
             */
            System.out.println("\n\nMEMBERSHIP TESTING");
            // create a test customer
            Membership mem1 = MembershipDAO.getInstance().create(cust1.getId());
            mem1.setStartDate("1-jul-2006");
            mem1.setEndDate("1-jul-2007");
            mem1.setCreditCard("1234567890123456");
            mem1.setCcExpiration("06/07");
            mem1.setCustomer(cust1);
            mem1.setNewsletter(true);
            mem1.setBackupSize(2);
            mem1.setBackupExpDate(System.currentTimeMillis() + 123123);
            mem1.setInterests(null);
            MembershipDAO.getInstance().save(mem1);
            cust1.setMembership(mem1);
            
            Membership mem2 = MembershipDAO.getInstance().create(cust2.getId());
            mem2.setStartDate("1-jul-2006");
            mem2.setEndDate("1-jul-2007");
            mem2.setCreditCard("9934567890123456");
            mem2.setCcExpiration("06/07");
            mem2.setCustomer(cust2);
            mem1.setBackupSize(2);
            mem1.setBackupExpDate(System.currentTimeMillis() + 123123);
            mem2.setNewsletter(true);
            mem2.setInterests(null);
            MembershipDAO.getInstance().save(mem2);
            
            // test that the exact same object will come out of the cache if asked for
            Membership mem3 = MembershipDAO.getInstance().read(mem1.getId());
            System.out.println("mem1==mem3 -> " + (mem1 == mem3));
            
            // test if all three customer objects are in the Cache
            System.out.println("mem1 in Cache -> " + Cache.getInstance().containsKey(mem1.getId()) );
            System.out.println("mem2 in Cache -> " + Cache.getInstance().containsKey(mem2.getId()) );
            System.out.println("mem3 in Cache -> " + Cache.getInstance().containsKey(mem3.getId()) );
            
            // test the update(save)
            mem2.setEndDate("never");
            MembershipDAO.getInstance().save(mem2);
            
            // test getByCustomerID()
            Membership testMembership = (Membership)MembershipDAO.getInstance().getByCustomerID(cust1.getId(),conn);
            System.out.println("The membership retrieved from cust1's ID is " + testMembership.getId());
            
            
            /**
             *
             *  TEST INTERESTS
             *
             */
            System.out.println("\n\nINTERESTS");
            // create a test interests
            Interest interest1 = InterestDAO.getInstance().create();
            interest1.setTitle("B&W Photography");
            interest1.setDescription("The art of black and white photography");
            InterestDAO.getInstance().save(interest1);
            
            Interest interest2 = InterestDAO.getInstance().create();
            interest2.setTitle("HDR photographs");
            interest2.setDescription("The technique of High Dynamic Range photographs");
            InterestDAO.getInstance().save(interest2);
            
            // test that the exact same object will come out of the cache if asked for
            Interest interest3 = InterestDAO.getInstance().read(interest1.getId());
            System.out.println("interest1==interest3 -> " + (interest1 == interest3));
            
            // test if all three customer objects are in the Cache
            System.out.println("interest1 in Cache -> " + Cache.getInstance().containsKey(interest1.getId()) );
            System.out.println("interest2 in Cache -> " + Cache.getInstance().containsKey(interest2.getId()) );
            System.out.println("interest3 in Cache -> " + Cache.getInstance().containsKey(interest3.getId()) );
            
            // test the update(save)
            interest2.setTitle("HDR Photography");
            InterestDAO.getInstance().save(interest2);
            
            // test getAll()
            List allInterests = (List)InterestDAO.getInstance().getAll();
            System.out.println(allInterests.size());
            for(int i = 0; i < allInterests.size(); i++){
                List temp = (List)allInterests.get(i);
                
                System.out.println( temp.get(0).toString() + " " + temp.get(1).toString() + " " + temp.get(2).toString());
            }
            
            
            /**
             *
             *  TEST MEMBER/INTERESTS
             *
             */
            System.out.println("\n\nTEST MEMBER/INTERESTS");
            
            // create a test interest list
            List<String> intList = new LinkedList<String>();
            intList.add(interest1.getId());
            intList.add(interest2.getId());
            
            // send list with membership 1
            MemberInterestDAO.getInstance().create(mem1.getId(), intList);
            
            // retrieve interests for membership 1
            List<String> mem1list = MemberInterestDAO.getInstance().read(mem1.getId(),conn);
            System.out.println("Interests for Member 1 :");
            for(int i = 0; i < mem1list.size(); i++){
                Interest interest = InterestDAO.getInstance().read(mem1list.get(i));
                System.out.println("\t" + interest.getTitle());
            }
            
            /**
             *  TESTING THE RESAVE OF CASCADE
             *
             */
            
            System.out.println("\n\nTESTING THE CASCADE");
            
            try{
                MembershipDAO.getInstance().save(mem1);
                CustomerDAO.getInstance().save(cust2);
                System.out.println("Success");
            }catch (Exception e1){
                throw new Exception(e1);
            }
            
            /**
             *
             * TESTING THE CREATION AND RETRIEVALE OF EMPLOYEE
             */
            
            System.out.println("\n\nEMPLOYEE\n");
            
            System.out.println("Reading Employee from the database");
            Employee emp = EmployeeDAO.getInstance().read("000001117284553c0014b10a500442");
            System.out.println("Employee name: " + emp.getFname() +" " + emp.getLname());
            
            System.out.println("\nCreating new employee");
            Employee NewEmp = EmployeeDAO.getInstance().create();
            NewEmp.setFname("New");
            NewEmp.setLname("Employee");
            NewEmp.setAddress1("123 Another Street");
            NewEmp.setAddress2("Appartment 3");
            NewEmp.setCity("Orem");
            NewEmp.setState("Utah");
            NewEmp.setZip("84569");
            NewEmp.setPhone("801-666-7777");
            NewEmp.setEmail("New@Employee.com");
            NewEmp.setSsNumber("0987-65-4321");
            NewEmp.setHireDate("05/04");
            NewEmp.setSalary(4578.00);
            NewEmp.setStoreID("000001117284553c0014b20a500442");
            EmployeeDAO.getInstance().save(NewEmp);
            
            System.out.println("New employee name: " + NewEmp.getFname() + " " + NewEmp.getLname());
            
            
            /**
             *
             * Testing the STORE
             *
             */
            System.out.println("\n\nSTORE\n");
            
            Store store = StoreDAO.getInstance().read("000001117284553c0014b20a500442");
            System.out.println("Reading store from the database");
            System.out.println(store.getName());
            
            System.out.println("\nCreating new Store");
            
            Store NewStore = StoreDAO.getInstance().create();
            NewStore.setName("Orem Central");
            NewStore.setAddress1("457 Center Street");
            NewStore.setAddress2("Suite 5");
            NewStore.setCity("Orem");
            NewStore.setState("Utah");
            NewStore.setZip("25686");
            NewStore.setPhone("456-756-3446");
            NewStore.setFax("342-443-4343");
            NewStore.setManagerID(NewEmp.getId());
            
            StoreDAO.getInstance().save(NewStore);
            
            System.out.println("New Store name: " + NewStore.getName());
            Employee manager = EmployeeDAO.getInstance().read(NewStore.getManagerID());
            System.out.println("New Store manager: " + manager.getFname() + " " + manager.getLname());
            
            
            /**
             *
             * TESTING THE SESSION
             *
             */
            System.out.println("\n\nSESSION\n");
            System.out.println("Creating new Session");
            Session session = new Session(NewStore, NewEmp);
            
            System.out.println("New session store: " + session.getStore().getName() + " \nNew Session Employee: " + session.getEmployee().getFname() + " " + session.getEmployee().getLname());
            
            
            /**
             *
             * TESTING THE TRANSACTION
             *
             */
            
            System.out.println("\n\nTRANSACTION\n");
            System.out.println("Creating new Transaction");
            
            Transaction tx = TransactionDAO.getInstance().create();
            tx.setCustomer(cust1);
            tx.setEmployee(session.getEmployee());
            tx.setStore(session.getStore());
            tx.setStatus("pending");
            tx.setType("Sale");
            
            
            /**
             *
             * TESTING TRANSACTIONLINE
             *
             */
            
            System.out.println("Creating a new backup TransactionLine");
            TransactionLine txLine1 = TransactionLineDAO.getInstance().create(tx, "ba");
            
            System.out.println("Creating a new Photo Order TransactionLine");
            TransactionLine txLine2 = TransactionLineDAO.getInstance().create(tx, "po");
            
            System.out.println("Creating a new Rental TransactionLine");
            TransactionLine txLine3 = TransactionLineDAO.getInstance().create(tx, "rn");
            
            System.out.println("Creating a new Repair TransactionLine");
            TransactionLine txLine5 = TransactionLineDAO.getInstance().create(tx, "rp");
            
            System.out.println("Creating a new Sale TransactionLine");
            TransactionLine txLine6 = TransactionLineDAO.getInstance().create(tx, "34820842342");
            
            System.out.println("Adding TransactionLines to Transaction");
            List<TransactionLine> txLineList = new LinkedList<TransactionLine>();
            
            txLineList.add(txLine1);
            txLineList.add(txLine2);
            
            tx.setTxLines(txLineList);
            
            /**
             *
             * TESTING REVENUESOURCE
             *
             */
            
            System.out.println("\n\nTESTING REVENUESOURCE\n");
            System.out.println("\n");
            //System.out.println("Read backupRS = orignal: " + (txLine1.getRevenueSource().getId() == ((RevenueSource)Cache.getInstance().get(txLine1.getRevenueSource())).getId()));
            System.out.println("Read backupRS = orignal: " + (txLine1.getRevenueSource().getId() == RevenueSourceDAO.getInstance().read(txLine1.getRevenueSource().getId()).getId()));
            System.out.println("Read PhotoOrderRS = orginal: " + (txLine2.getRevenueSource().getId() == RevenueSourceDAO.getInstance().read(txLine2.getRevenueSource().getId()).getId()));
            System.out.println("Read RentalRS = orginal: " + (txLine3.getRevenueSource().getId() == RevenueSourceDAO.getInstance().read(txLine3.getRevenueSource().getId()).getId()));
            System.out.println("Read RepairRS = orginal: " + (txLine5.getRevenueSource().getId() == RevenueSourceDAO.getInstance().read(txLine5.getRevenueSource().getId()).getId()));
            System.out.println("Read SaleRS = orginal: " + (txLine6.getRevenueSource().getId() == RevenueSourceDAO.getInstance().read(txLine6.getRevenueSource().getId()).getId()));
            
            txLine1.getRevenueSource().setPrice(12);
            System.out.println("Saving backupRS: "); RevenueSourceDAO.getInstance().save(txLine1.getRevenueSource());
            
            /**
             *TESTING PrintOrder (includes PhotoSet, PrintFormat)
             *
             */
            
            PhotoSet ps = PhotoSetDAO.getInstance().create();
            ps.setDescription("Test photoset");
            ps.setNumPhotos(3);
            //PhotoSetDAO.getInstance().save(ps);
            
            PhotoSet ps2 = PhotoSetDAO.getInstance().create();
            ps2.setDescription("Test photoset 2");
            ps2.setNumPhotos(5);
            PhotoSetDAO.getInstance().save(ps2);
            
            PhotoSet ps3 = PhotoSetDAO.getInstance().read(ps2.getId());
            System.out.println("ps2==ps3 -> " + (ps2 == ps3));
            
            // test if all three customer objects are in the Cache
            System.out.println("ps in Cache -> " + Cache.getInstance().containsKey(ps.getId()) );
            System.out.println("ps2 in Cache -> " + Cache.getInstance().containsKey(ps2.getId()) );
            System.out.println("ps3 in Cache -> " + Cache.getInstance().containsKey(ps3.getId()) );
            
            // test the update(save)
            ps.setDescription("HDR Photography");
            PhotoSetDAO.getInstance().save(ps);
            
            //PRINT FORMAT
            printFormat pf = printFormatDAO.getInstance().create();
            pf.setSize("3x5");
            pf.setPaperType("Glossy");
            pf.setSourceType("Film");
            pf.setPrice(.30);
            printFormatDAO.getInstance().save(pf);
            
            printFormat pf2 = printFormatDAO.getInstance().create();
            pf2.setSize("4x6");
            pf2.setPaperType("matte");
            pf2.setSourceType("Digital");
            pf2.setPrice(.33);
            printFormatDAO.getInstance().save(pf2);
            
            printFormat pf3 = printFormatDAO.getInstance().read(pf2.getId());
            System.out.println("pf2==pf3 -> " + (pf2 == pf3));
            
            // test if all three customer objects are in the Cache
            System.out.println("pf in Cache -> " + Cache.getInstance().containsKey(pf.getId()) );
            System.out.println("pf2 in Cache -> " + Cache.getInstance().containsKey(pf2.getId()) );
            System.out.println("pf3 in Cache -> " + Cache.getInstance().containsKey(pf3.getId()) );
            
            pf2.setPaperType("Other");
            printFormatDAO.getInstance().save(pf2);
            
            //PRINT ORDER
            printOrder po = (printOrder)PrintOrderDAO.getInstance().create();
            po.setPhotoSet(PhotoSetDAO.getInstance().read(ps.getId()));
            po.setPrintFormat(printFormatDAO.getInstance().read(pf.getId()));
            po.setQuantity(3);
            po.setPrice((po.getPrintFormat().getPrice() * po.getQuantity()));
            RevenueSourceDAO.getInstance().save(po);
            
            /*
             *Testing ConversionOrder (includes conversiontype)
             */
            
            conversionTypeBO ct = conversionTypeDAO.getInstance().create();
            ct.setDestinationType("DVD");
            ct.setSourceType("VHS");
            ct.setPrice(.1);
            conversionTypeDAO.getInstance().save(ct);
            
            conversionTypeBO ct2 = conversionTypeDAO.getInstance().create();
            ct.setDestinationType("MP4");
            ct.setSourceType("VHS");
            ct.setPrice(.4);
            conversionTypeDAO.getInstance().save(ct);
            
            conversionTypeBO ct3 = conversionTypeDAO.getInstance().read(ct2.getId());
            System.out.println("ct2==ct3 -> " + (ct2 == ct3));
            
            // test if all three customer objects are in the Cache
            System.out.println("ct in Cache -> " + Cache.getInstance().containsKey(ct.getId()) );
            System.out.println("ct2 in Cache -> " + Cache.getInstance().containsKey(ct2.getId()) );
            System.out.println("ct3 in Cache -> " + Cache.getInstance().containsKey(ct3.getId()) );
            
            ct.setDestinationType("Gold");
            conversionTypeDAO.getInstance().save(ct);
            
            conversionBO conv = (conversionBO)ConversionDAO.getInstance().create();
            conv.setConversionType(ct);
            conv.setQuantity(60);
            conv.setPrice((conv.getQuantity() * conv.getConversionType().getPrice()));
            RevenueSourceDAO.getInstance().save(conv);
            
           /*
             *Testing Rentals
             */ 
            
             System.out.println();
             System.out.println();
            System.out.println("Testing Rentals");
            System.out.println();
            
            System.out.println("Creating a new Rental TransactionLine");
            TransactionLine txLine10 = TransactionLineDAO.getInstance().create(tx, "rn");
            
            Rental rn = (Rental) RentalDAO.getInstance().create();
            rn.setDateOut(87654);
            rn.setDateDue(97654);
            RevenueSourceDAO.getInstance().save(rn);
            
            String test = ForRentDAO.getInstance().getBySerial("5543256543565");
            System.out.println(test);
            RentalReturn rr = RentalReturnDAO.getInstance().create(test);
            rr.setDatein(14321432);
            RentalReturnDAO.getInstance().save(rr);
            System.out.println("rental return save complete");
            
            /*
             *Testing Batch
             */ 
            System.out.println();
             System.out.println();
            System.out.println("TESTING RENTAL BATCH");
            System.out.println();
            
            RentalBatch.getInstance().movetosale();
            
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }//main
    
    private String fmt(double number) {
        DecimalFormat formatter = new DecimalFormat("###,##0.00");
        return formatter.format(number);
        
    }
    
    
}//class
