/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class RoomInformationDialog extends JDialog {
    
    SQLServer db;
    JTable table;
    RoomInformationTableModel tableData;
    
    public RoomInformationDialog(Window owner, SQLServer db) {
        super(owner,"Terem információk",Dialog.ModalityType.APPLICATION_MODAL);
        this.db = db;
        
        InitializeDialog();

        // default close operation, if we hide it, we need
        // an update method we can call in the action handler to update it
        // or just dispose of it on close, not the most elegant solution :)
        
    }
    
    public void UpdateDialog() {
        this.tableData.fireTableDataChanged();
    }

    private void InitializeDialog() {
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE); 
        
        InitializeTable();  
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);        

    }

    private void InitializeTable() {
        
        this.tableData = new RoomInformationTableModel(this.db);
        this.table = new JTable(this.tableData);
        this.table.setAutoCreateRowSorter(true);        
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(480,200));
        
        // Hozzaadjuk a parbeszed ablakhoz
        this.add(new JScrollPane(this.table));
    }
}
