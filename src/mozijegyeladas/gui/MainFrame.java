/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import mozijegyeladas.db.SQLServer;
import mozijegyeladas.action.ActionHandler;

/**
 *
 * @author Peter
 */
public class MainFrame extends JFrame {
    
    public MainFrame() 
    {
        super("Foablak");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CreateMenuBar();
        
        this.pack();
        this.setVisible(true);
        
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

    private void CreateMenuBar() {
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        // Menusav letrehozasa
        menuBar = new JMenuBar();
        
        // Fajl menu
        // =========================================
        menu = new JMenu("Fájl");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        // Kilepes
        menuItem = new JMenuItem();
        menuItem.setAction(ActionHandler.Exit);
        menu.add(menuItem);
        
        // Adatok menu
        // =========================================
        menu = new JMenu("Adatok");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        
        // Termek
        menuItem = new JMenuItem();
        menuItem.setAction(ActionHandler.ShowRoomInformation);
        menu.add(menuItem);
        
        // Filmek
        menuItem = new JMenuItem();
        menuItem.setAction(ActionHandler.ShowMovieInformation);
        menu.add(menuItem);        
        
       
        
        
        
        this.setJMenuBar(menuBar);
    }
    
    
}