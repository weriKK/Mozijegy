/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class MovieInformationDialog extends JDialog {
    
    SQLServer db;
    MovieInformationTableModel tableData;
    JTable table;
    JTextArea synopseArea;
    
    public MovieInformationDialog(Window owner, SQLServer db) {
        super(owner,"Film információk",Dialog.ModalityType.APPLICATION_MODAL);
        this.db = db;
        
        InitializeDialog();

    }
    
    public void UpdateDialog() {
        this.tableData.fireTableDataChanged();
    }
    
    private void InitializeDialog() {

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setSize(640, 480);
        
        InitalizeTable();
        InitializeSynopsisArea();
        
        this.pack();    
        
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);        
        
    }

    private void InitializeSynopsisArea() {
        this.synopseArea = new JTextArea(tableData.getSynopse(0));
        this.synopseArea.setRows(8);
        this.synopseArea.setEditable(false);
        this.synopseArea.setLineWrap(true);
        this.synopseArea.setWrapStyleWord(true);
        
        this.add(new JLabel("Szinopszis:",SwingConstants.LEFT));
        this.add(new JScrollPane(this.synopseArea));        
    }

    private void InitalizeTable() {
        
        tableData = new MovieInformationTableModel(this.db);
        
        this.table = new JTable(tableData);
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(640,200));        
        this.table.setAutoCreateRowSorter(true);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                // Ha az adat meg valtozasban van
                if ( event.getValueIsAdjusting() )
                    return;
                
                synopseArea.setText(tableData.getSynopse(table.getSelectionModel().getLeadSelectionIndex()));
            }
            
        });
        
        
        // Adatok szerkesztese
        TableColumn dubbingColumn = this.table.getColumnModel().getColumn(1);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("szinkronizált");
        comboBox.addItem("feliratos");
        dubbingColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
        this.add(new JScrollPane(this.table));
    }
}
