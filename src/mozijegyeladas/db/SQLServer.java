/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class SQLServer {
    
    private String hostName;
    private int hostPort;    
    private String databaseName;
    private String userName;
    private String password;
    
    private Connection connection;
    
    public SQLServer(String hostName, int hostPort, String databaseName, String userName, String password) throws SQLException, Exception
    {
        this.hostName = hostName;
        this.hostPort = hostPort;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        
        // Connect to the database
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String sqlUrl = "jdbc:mysql://" + this.hostName + ":" + this.hostPort + "/" + this.databaseName; 
            
        this.connection = DriverManager.getConnection(sqlUrl, this.userName, this.password);
        
    }
    
    public ArrayList<Object[]> GetMovieInformation() {
        
        String queryString = "SELECT title,dubbing,origin,director,length,id_movie,synopsis FROM " + this.databaseName + ".movies";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                Object[] resultRow = new Object[7];
                
                resultRow[0] = resultSet.getString(1);
                resultRow[1] = resultSet.getInt(2);
                resultRow[2] = resultSet.getString(3);
                resultRow[3] = resultSet.getString(4);
                resultRow[4] = resultSet.getInt(5);
                resultRow[5] = resultSet.getInt(6);                
                resultRow[6] = resultSet.getString(7);
                
                result.add(resultRow);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {

            // Eroforrasok felszabaditasa
            // forditott sorrendben
            if ( resultSet != null ) {
                
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                resultSet = null;                                
            }
            
            if ( queryStatement != null ) {
                
                try {
                    queryStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                queryStatement = null;                                
            }                        
            
        }
        
        return result;        
    }
    
    public boolean SetMovieInformation(ArrayList<Object[]> rowData) {
        
        String updateString = "UPDATE " + this.databaseName + ".movies " +
                "SET title = ?, dubbing = ?, origin = ?, director = ?, length = ?, synopsis = ? " +
                "WHERE id_movie = ?";
        
        PreparedStatement updateMovies = null;
        
        try {
            this.connection.setAutoCommit(false);
            updateMovies = this.connection.prepareStatement(updateString);
            
            for ( Object[] row : rowData ) {
                updateMovies.setString(1,row[0].toString()); // title
                updateMovies.setInt(2,(Integer)row[1]); // dubbing                
                updateMovies.setString(3,row[2].toString()); // origin
                updateMovies.setString(4,row[3].toString()); // director
                updateMovies.setInt(5,(Integer)row[4]); // length                
                updateMovies.setString(6,row[6].toString()); // synopsis
                
                updateMovies.setInt(7,(Integer)row[5]); // id

                // Az adatbazis frissitese
                updateMovies.executeUpdate();
                this.connection.commit();
            }
        } catch ( SQLException ex ) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);   
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            return false;
            
        } finally {
            if ( updateMovies != null ) {
                try {
                    updateMovies.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
        }
    }

    public ArrayList<Object[]> GetRoomInformation() {
        
        String queryString = "SELECT name,rows,columns FROM " + this.databaseName + ".rooms";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                Object[] resultRow = new Object[4];
                
                resultRow[0] = resultSet.getString(1);
                resultRow[1] = resultSet.getInt(2);
                resultRow[2] = resultSet.getInt(3);
                resultRow[3] = (Integer)resultRow[1] * (Integer)resultRow[2];
                
                result.add(resultRow);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {

            // Eroforrasok felszabaditasa
            // forditott sorrendben
            if ( resultSet != null ) {
                
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                resultSet = null;                                
            }
            
            if ( queryStatement != null ) {
                
                try {
                    queryStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                queryStatement = null;                                
            }                        
            
        }
        
        return result;
        
    }    
        
} 