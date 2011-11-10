/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import javax.swing.JFrame;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class MainFrame extends JFrame {
    
    public MainFrame() 
    {
        super("Foablak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
        
    }
    
    private void InitializeDatabase()
    {
        SQLServer sql = null;

        try {
            sql = new SQLServer("localhost",3306,"MoziDB", "root", "root");

            //CoursesTableWindow ctw = new CoursesTableWindow(sql.getConnection());
            //ctw.setVisible(true);

        } catch (Exception e) {
            System.err.println("Hiba: " + e.getMessage());
        }        
    }
    
    
}
