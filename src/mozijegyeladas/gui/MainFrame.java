/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    
    private ActionHandler actionHandler;
    private SQLServer db;
    
    private ShowListPanel showListPanel;
    
    public MainFrame() 
    {
        super("Programozási Technológia 2 - 1. Beadandó - KOPOABI.ELTE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        InitializeDatabase();        
        
        actionHandler = new ActionHandler(this,this.db);
        
        CreateMenuBar();
        
        CreateMainPanel();
        
        //this.pack();
        this.setSize(800, 600);        
        this.setLocationRelativeTo(null);        
        this.setVisible(true);
        
        
    }
    
    private void InitializeDatabase()
    {
        try {
            db = new SQLServer("localhost",3306,"MoziDB", "root", "root");

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
        menuItem.setAction(actionHandler.Exit);
        menu.add(menuItem);
        
        // Adatok menu
        // =========================================
        menu = new JMenu("Adatok");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        
        // Termek
        menuItem = new JMenuItem();
        menuItem.setAction(actionHandler.ShowRoomInformation);
        menu.add(menuItem);
        
        // Filmek
        menuItem = new JMenuItem();
        menuItem.setAction(actionHandler.ShowMovieInformation);
        menu.add(menuItem); 
        
        // Separator
        menu.addSeparator();
        
        // Uj Film
        menuItem = new JMenuItem();
        menuItem.setAction(actionHandler.ShowNewMovie);
        menu.add(menuItem);
        
        // Előadások menu
        // =========================================
        menu = new JMenu("Előadások");
        menu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menu);
        
        // Hozzáadás
        menuItem = new JMenuItem();
        menuItem.setAction(actionHandler.ShowShowInformation);
        menu.add(menuItem);   
        
        // Separator
        menu.addSeparator();        
        
        // Hozzáadás
        menuItem = new JMenuItem();
        menuItem.setAction(actionHandler.NewShowInformation);
        menu.add(menuItem);
        
       
        
        
        
        this.setJMenuBar(menuBar);
    }

    private void CreateMainPanel() {
        
        showListPanel = new ShowListPanel(db);
        
        this.add(showListPanel);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                
                showListPanel.UpdatePanel();

            }
        });         
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    
}