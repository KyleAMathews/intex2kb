/*
 * PhotoDAO.java
 *
 * Created on April 10, 2007, 1:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author tylerf
 */
public class PhotoDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PhotoDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PhotoDAO() {
    }
    
    public static synchronized PhotoDAO getInstance() {
        if (instance == null) {
            instance = new PhotoDAO();
        }
        return instance;
    }
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * There's no need for two creates because we don't need
     * a connection to create BOs.  We run the insert statement
     * later, when it get's saved for the first time.
     */
    public photoBackupBO create() throws Exception{
        String id = null;
        id = GUID.generate();
        photoBackupBO p = new photoBackupBO(id);
        Cache c = Cache.getInstance();
        c.put(p.getId(), p);
        return p;
    }
    
    public synchronized photoBackupBO read(String id) throws DataException {
        photoBackupBO photo = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            photo = (photoBackupBO)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                photo = this.read(id, conn);
                
                // release the connection
                conn.commit();
                ConnectionPool.getInstance().release(conn);
                
            }catch (ConnectionPoolException e){
                throw new DataException("Could not get a connection to the database.");
                
            }catch (SQLException e) {
                // rollback
                try {
                    conn.rollback();
                    ConnectionPool.getInstance().release(conn);
                }catch (ConnectionPoolException ce){
                    throw new DataException("There was an error with the connection to the database", ce);
                }catch (SQLException e2) {
                    throw new DataException("Big error: could not even release the connection", e2);
                }
                
                throw new DataException("Could not retrieve record for id=" + id, e);
            }
        }
        return photo;
    }
    
    /**
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized photoBackupBO read(String id, Connection conn) throws SQLException, DataException {
        photoBackupBO photo = null;
        
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            photo = (photoBackupBO)Cache.getInstance().get(id);
        }else{
            // if not in the cache, get a result set from
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"photoBackup\" WHERE \"id\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            conn.commit();
            
            
            if (rs.next()) {
                photo = new photoBackupBO(rs.getString("id"));
                photo.setM(rs.getString("membid"));
                photo.setCaption(rs.getString("caption"));
                photo.setFilename(rs.getString("filename"));
                photo.setFiletype(rs.getString("filetype"));
                photo.setFilesize(rs.getString("filesize"));
                photo.setStatus(rs.getString("status"));
                photo.setInDB(true);
                photo.setDirty(false);
                
                // save to the cache
                Cache.getInstance().put(id, photo);
                
                // Close prepared statement
                read.close();
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return photo;
    }
    
    public void save(photoBackupBO p, FileItem thumb, FileItem med, FileItem file) throws Exception {
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO photobackup (id,membid,caption,thumbnail,mediumpic,originalpic,filename,filetype,filesize,status) values (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,p.getId());
        ps.setString(2,p.getM());
        ps.setString(3,p.getCaption());
        if(thumb != null){
            ps.setBinaryStream(4,thumb.getInputStream(),(int)thumb.getSize());
        }else{
            ps.setBinaryStream(4,null,0);
        }
        if(med != null){
            ps.setBinaryStream(5,med.getInputStream(),(int)thumb.getSize());
        }else{
            ps.setBinaryStream(5,null,0);
        }
        ps.setBinaryStream(6,file.getInputStream(),(int)file.getSize());
        ps.setString(7,p.getFilename());
        ps.setString(8,p.getFiletype());
        ps.setString(9,p.getFilesize());
        ps.setString(10,p.getStatus());
        
        ps.execute();
        ps.close();
        ConnectionPool.getInstance().release(conn);
    }
    
    //public synchronized LinkedList readByMembId(String membid){
        
    //}
    
    
}
