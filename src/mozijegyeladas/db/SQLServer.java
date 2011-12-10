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
    
    public boolean AddNewMovie(Object[] rowData) {
        
//        for ( Object o : rowData) 
//            System.out.println(o.toString());
        
        String insertString = "INSERT INTO " + this.databaseName + ".movies " +
                "(title, origin, dubbing, director, synopsis, length) " +
                "VALUES (?,?,?,?,?,?)";
        
        PreparedStatement insertMovie = null;
        
        try {
            this.connection.setAutoCommit(false);
            
            insertMovie = this.connection.prepareStatement(insertString);

            insertMovie.setString(1,rowData[0].toString()); // title
            insertMovie.setString(2,rowData[1].toString()); // origin                
            insertMovie.setInt(3,(Integer)rowData[2]); // dubbing
            insertMovie.setString(4,rowData[3].toString()); // director
            insertMovie.setString(5,rowData[5].toString()); // synopsis
            insertMovie.setInt(6,(Integer)rowData[4]); // length  
            
            // Az adatbazis frissitese
            insertMovie.executeUpdate();
            this.connection.commit();
            
        } catch ( SQLException ex ) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);   
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            return false;
            
        } finally {

            if ( insertMovie != null ) {
                try {
                    insertMovie.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
        }
    }
    
    
    public ArrayList<Object[]> GetRoomNames() {
        
        String queryString = "SELECT name,id_room FROM " + this.databaseName + ".rooms";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                Object[] resultRow = new Object[2];
                
                resultRow[0] = resultSet.getString(1);
                resultRow[1] = resultSet.getInt(2);
                
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

    public boolean AddNewShow(Object[] rowData) {
//        for ( Object o : rowData) 
//            System.out.println(o.toString());
        
        String insertString = "INSERT INTO " + this.databaseName + ".shows " +
                "(movie, start_date, room) VALUES (?,?,?)";
        
        PreparedStatement insertShow = null;
        
        try {
            this.connection.setAutoCommit(false);
            
            insertShow = this.connection.prepareStatement(insertString);

            insertShow.setInt(1,(Integer)rowData[0]); // title
            insertShow.setString(2,rowData[1].toString()); // origin                
            insertShow.setInt(3,(Integer)rowData[2]); // dubbing
            
            // Az adatbazis frissitese
            insertShow.executeUpdate();
            this.connection.commit();
            
        } catch ( SQLException ex ) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);   
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            return false;
            
        } finally {

            if ( insertShow != null ) {
                try {
                    insertShow.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
        }
    }

    public int GetShowsRunningAtDate(String date, int length, int roomID)
    {
        
        String queryString = "SELECT id_show, start_date, length FROM "+this.databaseName+".shows,"+this.databaseName+".movies "+
        "WHERE shows.movie = movies.id_movie " +
        "AND room = ? "+
        "AND ((? BETWEEN start_date AND (start_date + INTERVAL length MINUTE)) "+
        "OR ((? + INTERVAL ? MINUTE) BETWEEN start_date AND (start_date + INTERVAL length MINUTE)) "+
        "OR (? < start_date AND (? + INTERVAL ? MINUTE) > (start_date + INTERVAL length MINUTE)))";

        /*
            ujfilm_kezdete between jelenlegi_kezdete or jelenlegi_vege OR
            ujfilm_vege between jelenlegi_kezdete or jelenlegi_vege OR
            ujfilm_kezdete < jelenlegi_kezdete AND ujfilm_vege > jelenlegi_vege
         */
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        int size = 0;
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            
            queryStatement.setInt(1, roomID);   // room = ?
            queryStatement.setString(2, date);  // date
            queryStatement.setString(3, date);  // date            
            queryStatement.setInt(4, length);   // length
            queryStatement.setString(5, date);  // date
            queryStatement.setString(6, date);  // date
            queryStatement.setInt(7, length);   // length
            
//            System.out.println(queryStatement.toString());
            resultSet = queryStatement.executeQuery();
            
        
            // Lekerdezett adatok feldolgozasa
            
            // Talalatok megszamolasa
            resultSet.beforeFirst();
            resultSet.last();
            size = resultSet.getRow();
                     
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
        
        return size;
        
    }

    public ArrayList<Object[]> GetShowInformation() {
        /*
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date FROM shows,movies,rooms 
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room
         * 
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date,
(SELECT COUNT(*) FROM seats WHERE `show`=id_show) AS fog
FROM (shows,movies,rooms)
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room          
         * 
         * 
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date,
(SELECT COUNT(*) FROM seats WHERE `show`=id_show AND state=1) AS fog
FROM (shows,movies,rooms)
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room
         */
        String queryString = "SELECT id_show,title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date, "+
                "(SELECT COUNT(*) FROM seats WHERE `show`=id_show AND state=1) AS booked "+
                "FROM (shows,movies,rooms) "+
                "WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                // csak azokat az eloadasokat gyujtsuk ossze
                // amikre nem lett foglalva vagy eladva jegy
//                if ( resultSet.getInt(6) == 0)
//                {
                    Object[] resultRow = new Object[6];

                    resultRow[0] = resultSet.getInt(1);
                    resultRow[1] = resultSet.getString(2);
                    resultRow[2] = resultSet.getString(3);                
                    resultRow[3] = resultSet.getString(4).replace(".0", "");                
                    resultRow[4] = resultSet.getString(5).replace(".0", "");                                
                    resultRow[5] = resultSet.getInt(6);

                    result.add(resultRow);
//                }
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
    
    public boolean DeleteShow(int showID) {
        
        String deleteString = "DELETE FROM "+this.databaseName+".shows WHERE id_show=? LIMIT 1";
        
        PreparedStatement deleteShow = null;
        
        try {
            this.connection.setAutoCommit(false);
            
            deleteShow = this.connection.prepareStatement(deleteString);
            deleteShow.setInt(1,showID);
            deleteShow.executeUpdate();
            
            this.connection.commit();
            
        } catch ( SQLException ex ) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);   
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            return false;
            
        } finally {

            if ( deleteShow != null ) {
                try {
                    deleteShow.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
        }
    }
    
    public ArrayList<Object[]> GetShowListData() {
        /*
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date FROM shows,movies,rooms 
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room
         * 
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date,
(SELECT COUNT(*) FROM seats WHERE `show`=id_show) AS fog
FROM (shows,movies,rooms)
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room          
         * 
         * 
SELECT  id_show, title,name,start_date,(start_date + INTERVAL length MINUTE) AS end_date,
(SELECT COUNT(*) FROM seats WHERE `show`=id_show AND state=1) AS fog
FROM (shows,movies,rooms)
WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room
         * 
         * 
         * "show_id","Kezdet","Terem","Film","Hossz","Foglalt","Kiadott","Szabad"
         */
        String queryString = "SELECT id_show,title,name,start_date,length,rows,columns, "+
                "(SELECT COUNT(*) FROM seats WHERE `show`=id_show AND state=1) AS booked, "+
                "(SELECT COUNT(*) FROM seats WHERE `show`=id_show AND state=2) AS sold, id_room "+
                "FROM (shows,movies,rooms) "+
                "WHERE shows.movie = movies.id_movie AND shows.room = rooms.id_room "+
                "ORDER BY start_date ASC";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                Object[] resultRow = new Object[11];

                resultRow[0] = resultSet.getInt(1); // show_id
                resultRow[1] = resultSet.getString(4).replace(".0", ""); // start date
                resultRow[2] = resultSet.getString(3); // name               
                resultRow[3] = resultSet.getString(2); // title
                resultRow[4] = resultSet.getInt(5); // length
                resultRow[5] = resultSet.getInt(8); // booked
                resultRow[6] = resultSet.getInt(9); // sold
                
                int roomSize = resultSet.getInt(6) * resultSet.getInt(7);
                                
                resultRow[7] = roomSize - resultSet.getInt(8) - resultSet.getInt(9); // free
                
                resultRow[8] = resultSet.getInt(6); // row count
                resultRow[9] = resultSet.getInt(7); // column count
                resultRow[10] = resultSet.getInt(10); // column count

                result.add(resultRow);
                
                //System.out.println(Arrays.toString(resultRow));
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
    
    
    
    public ArrayList<int[]> GetShowSeatStates(int show_id) {
        
        String queryString = "SELECT * "+
                "FROM "+ this.databaseName+".seats "+
                "WHERE seats.show=? "+
                "ORDER BY state ASC";
        
        PreparedStatement queryStatement = null;
        ResultSet resultSet = null;
        ArrayList<int[]> result = new ArrayList<int[]>();
        
        try {
            
            queryStatement = this.connection.prepareStatement(queryString,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            queryStatement.setInt(1, show_id);
            resultSet = queryStatement.executeQuery();
        
            // Lekerdezett adatok feldolgozasa
            while (resultSet.next()) {
                
                int[] resultRow = new int[6];

                resultRow[0] = resultSet.getInt(1); // seat_id
                resultRow[1] = resultSet.getInt(2); // show_id
                resultRow[2] = resultSet.getInt(3); // room_id
                resultRow[3] = resultSet.getInt(4); // rows
                resultRow[4] = resultSet.getInt(5); // columns
                resultRow[5] = resultSet.getInt(6); // seat state
                
                result.add(resultRow);
                
                //System.out.println(Arrays.toString(resultRow));
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
    
    
    
    public boolean SetSeatStatus(int[] rowData) {
        
        String insertString = "REPLACE INTO " + this.databaseName + ".seats " +
                "(`show`,`room_id`,`row`,`column`,`state`) "+
                "VALUES (?,?,?,?,?)";
        
        PreparedStatement insertSeat = null;
        
        try {
            this.connection.setAutoCommit(false);
            insertSeat = this.connection.prepareStatement(insertString);
            
            insertSeat.setInt(1,rowData[0]); // show
            insertSeat.setInt(2,rowData[1]); // room
            insertSeat.setInt(3,rowData[2]); // row
            insertSeat.setInt(4,rowData[3]); // col
            insertSeat.setInt(5,rowData[4]); // status

            // Az adatbazis frissitese
            insertSeat.executeUpdate();
            this.connection.commit();
            
        } catch ( SQLException ex ) {
            Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);   
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            return false;
            
        } finally {
            if ( insertSeat != null ) {
                try {
                    insertSeat.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SQLServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return true;
        }
    }    
    
    
    
            
} 