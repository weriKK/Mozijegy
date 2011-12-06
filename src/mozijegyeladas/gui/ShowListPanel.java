/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class ShowListPanel extends JPanel {
    
    SQLServer db;
    JTable table;
    ShowListTableModel tableData;

    public ShowListPanel(SQLServer db) {
        super(true);
        
        this.db = db;
        
        InitializePanel();
    }

    private void InitializePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        InitializeTable();
    }

    private void InitializeTable() {
        this.tableData = new ShowListTableModel(db);
        
        this.table = new JTable(this.tableData);
        this.table.setAutoCreateRowSorter(true); 
        this.table.setFillsViewportHeight(true);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // elrejtuk az ID oszlopot
        this.table.removeColumn(table.getColumnModel().getColumn(0));  
        
        this.table.getColumnModel().getColumn(0).setMinWidth(135); 
        this.table.getColumnModel().getColumn(0).setMaxWidth(135);         
        
        this.table.getColumnModel().getColumn(1).setMaxWidth(100);        
        //this.table.getColumnModel().getColumn(2).setMaxWidth(10);                
        this.table.getColumnModel().getColumn(3).setMaxWidth(60);
        this.table.getColumnModel().getColumn(4).setMaxWidth(60);        
        this.table.getColumnModel().getColumn(5).setMaxWidth(60);        
        this.table.getColumnModel().getColumn(6).setMaxWidth(55);        
        
        this.add(new JScrollPane(this.table));
    }
    
    
    
}
