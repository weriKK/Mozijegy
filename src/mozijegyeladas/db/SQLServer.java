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
        
        String queryString = "SELECT title,dub,origin,director,length,id,synopsis FROM " + this.databaseName + ".MoviesView";
        
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
                resultRow[1] = resultSet.getString(2);
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