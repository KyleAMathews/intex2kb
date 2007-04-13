/*
 * GUID.java - Creates Genuine Unique ID (GUID)
 *
 * Created on September 13, 2006, 2:25 PM
 *
 * By @author Cameron, <cameronb62@byu.edu>, ISys 403, Section 2, Group K
 */

package edu.byu.isys413.cbb54.intex2kb.data;




import java.net.*;


/**
 * This class generates a GUID from the host's IP address, the current
 * time, and a random number
 */
public class GUID {
    
    /*
     * Make IP, random, lasttime, and MAX_RANGE static variables and therefore 
     * make them global variables.
     */
    private static String ip = "";
    private static int random = 0;
    private static int begRand = 0;
    private static long lasttime = 0;
    
    
    /** Creates a new instance of GUID */
    public GUID() {
    }
    
    /**
     * Generates a new GUID
     * @return String(guid)
     * @throws java.lang.Exception Thrown when there is an error generating the guid
     */
    
    public static synchronized String generate(String rsType) throws Exception {
        String guid = generate();
        StringBuffer type = new StringBuffer(guid);
        type.insert(0, rsType);
        return type.toString();
    }
    
    /**
     * Generates a guid
     */
    public static synchronized String generate() throws Exception {
        
       // Initalize GUID string
       String GUID = "";

        
        /*
         * Get the System Time and convert to 16 digit hexadecimal
         */

        long now = System.currentTimeMillis();
        String nowhex = Long.toHexString(now);

        // Pad Hex system time to 16 digits
        nowhex = pad(nowhex,16);

        // Add hex time String to main GUID String
        GUID = GUID + nowhex;


         /*
         *  Generate six character random hex number.  If timestamp being 
         *  used in this GUID is the same as in the last, increment this
         *  random number by one and continue.  If timestamp is different
         *  than the last, generate a new random number.
         *
         *  The program must watch to make sure the random number does not
         *  exceed 16,777,215 (the limit of a six digit hex).  If the 
         *  random number reaches 16,777,214 we will reset the random number
         *  to zero.  
         *
         *  If the random number does make a complete cycle (from the 
         *  starting random number up to 16,777,214 and then from zero back
         *  to the starting random number) the program will "sleep" for two
         *  milliseconds and then continue.
         */

         // True if system time now is the same as the system time for the last GUID
         if(now == lasttime){
            
             // Increment  random by one.
             random = random + 1;
             
             // Reset random number to zero if it reaches the ceiling of six digit hex
             if(random == 16777214){
                 random = 0;
             }

             // Have the system wait for two milliseconds if all possible hex digits have 
             // been used within the same millisecond
             if(random == begRand){
                 Thread.sleep(2);
                 now = System.currentTimeMillis();
                 random = (int)(Math.random()*100000);
             }
         // Else, System time is not the same as the last GUID and a new random number is generated    
         }else{
             random = (int)(Math.random()*100000);
             begRand = random;
         }

        // Conver random number to hex
        String randomHex = Integer.toHexString(random);
        
        // Pad random hex number to six digits
        randomHex = pad(randomHex,6);

        // Add padded random hex number to GUID
        GUID = GUID + randomHex;
        
        // Set variable lasttime (meaning the time the last GUID was made) to the time current GUID was made
        lasttime = now;




        /*
         * Get computer's IP address and convert to 8 digit hexadecimal
         */

        // If variable IP has not been set (which only needs to be done once every time the program runs)
        // call the IP method.
        if(ip == ""){
            
            // Call IP() method to generate a hex representation of the IP address
            ip = IP();
        }
        
        // Add hex IP to GUID
        GUID = GUID + ip;


        // Return final GUID
        return GUID;
    } // End of GUID generate()
    
    
    
    /*
     *  The IP method retrieves the IP address from the system, splits it into
     *  four parts, pads the four parts to three characters and converts to hex,
     *  pads the hex to two characters, combines the four hex parts and then 
     *  returns the IP in a hex string.
     */
    /**
     * Returns the host's IP address in hexadecimal
     * @return String(ip)
     * @throws java.net.UnknownHostException Thrown when a host does not have an active ip address
     */
    public static String IP() throws UnknownHostException{
        
            // Initialize variables used to create hex ip address
            String ip = "";
            String IPpart = "";
            String HexIP = "";
            String[] fields;
            
            // Retrieve IP address from system
            ip = java.net.InetAddress.getLocalHost().getHostAddress();
           
            // Split IP address into four parts and place into "fields" array
            fields = ip.split("[.]");
            
            /*
             *  The following for-loop loops through the "fields" array created 
             *  above, padding each part of the IP address to three characters,
             *  converts each IP part to hex, then returns the complete hex IP.
             */
            
            for(int ipPart = 0; ipPart < 4; ipPart++){
                
                // Pad each IP part to three characters
                String Padded = pad(fields[ipPart],3);

                // Convert each three character IP part to hex
                IPpart = Integer.toHexString(Integer.parseInt(Padded));
                
                // Pad the hex character to two character if needed
                if(IPpart.length() != 2){
                    IPpart = pad(IPpart,2);
                }
                
                // Add converted IP part to HexIP string
                HexIP = HexIP + IPpart;
                
            } // End of for loop
            
            // Return the Hex IP
            return HexIP;
        } // End of IP()
        
    
    
    
        /*
         *  The pad method accepts a String variable (to be padded) and an Int
         *  value, which is the length the String should be padded to.  Then
         *  it returns the padded string.
         */
    /**
     * Pads a string with zeros to a certain number of characters
     * @param str String
     * @param length Integer
     * @return String(padded string)
     */
        public static String pad(String str, int length){
            
            // While-loop which adds zeros to the front of the string until it
            // reaches the desired length
            while(str.length() < length){
                str = "0" + str;
            }

            return str;
        } // End of pad()
    

}
