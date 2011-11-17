/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
} 