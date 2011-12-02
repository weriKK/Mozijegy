/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.OptionPaneUI;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class ShowInformationDialog extends OKCancelDialog {
    
    SQLServer db;
    JTable table;
    ShowInformationTableModel tableData;
    
    public ShowInformationDialog(Window owner, SQLServer db) {
        super(owner,"Előadás törlése",Dialog.ModalityType.APPLICATION_MODAL);
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
        
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.setOKButtonText("Törlés");
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);        
        
        InitializeTable();  
        
        this.add(buttonPanel);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                tableData.UpdateTable(false);
                UpdateDialog();
            }            
        }); 
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);        

    }

    private void InitializeTable() {
        
        this.tableData = new ShowInformationTableModel(this.db);
        this.table = new JTable(this.tableData);
        this.table.setAutoCreateRowSorter(true); 
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(640,200));
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // elrejtuk az ID oszlopot
        this.table.removeColumn(table.getColumnModel().getColumn(0));
        
        this.table.getColumnModel().getColumn(1).setPreferredWidth(15);        
        this.table.getColumnModel().getColumn(4).setPreferredWidth(5);
        
        // Hozzaadjuk a parbeszed ablakhoz
        this.add(new JScrollPane(this.table));
    }

    @Override
    protected boolean processOK() {
       
        
        if ( this.tableData.seatsAreBooked( this.table.getSelectedRow() ) )
        {
            JOptionPane.showMessageDialog(
                    this, "A kijelölt előadásra már foglaltak helyet, törölni nem lehet!",
                    "Előadás törlése...",
                    JOptionPane.WARNING_MESSAGE);
            
            return false;
        }
        
        DeleteShowAndUpdateDialog();        
        
        return false;
    }

    @Override
    protected void processCancel() {
        this.tableData.UpdateTable(false);
    }

    private void DeleteShowAndUpdateDialog()
    {
        boolean successfulDelete = db.DeleteShow( this.tableData.showID( this.table.getSelectedRow() ) );
        
        if ( !successfulDelete )
        {
            JOptionPane.showMessageDialog(
                    this, "Az előadás törlése során hiba lépett fel!",
                    "Hiba!",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        this.tableData.UpdateTable(false);
        this.tableData.fireTableDataChanged();
        
       
    }
}
