/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class MovieInformationDialog extends OKCancelDialog {
    
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
        
        this.add(buttonPanel);
        this.setOKButtonText("Módosít");
        this.disableOK();
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                disableOK();
            }
        });
        
        this.pack();    
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);        
        
    }

    private void InitializeSynopsisArea() {
        this.synopseArea = new JTextArea();
        this.synopseArea.setRows(8);
        this.synopseArea.setEditable(false);
        this.synopseArea.setLineWrap(true);
        this.synopseArea.setWrapStyleWord(true);
        
        this.synopseArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                UpdateSynopsisData();
            }
        });
        
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
                
                //int idx = table.getSelectionModel().getLeadSelectionIndex();
                int idx = table.getSelectedRow();
                
                //if ( idx >= 0 && idx < tableData.getRowCount()) {
                if ( idx != -1 ) {
                    synopseArea.setText(tableData.getSynopse(idx));
                    synopseArea.setEditable(true);
                }
                else {
                    synopseArea.setText(null);
                    synopseArea.setEditable(false);
                }
            }
            
        });
        
        this.tableData.addTableModelListener( new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                enableOK();
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

    private void UpdateSynopsisData() {
        int idx = table.getSelectedRow();
        if ( idx != -1 ) {
            tableData.setSynopse(idx, synopseArea.getText());
        }
        enableOK();
    } 
    
    @Override
    protected boolean processOK() {
        if ( this.tableData.UpdateTable(true) ) {
            this.disableOK();
            return true;
        }
        
        return false;
    }

    @Override
    protected void processCancel() {
        this.tableData.UpdateTable(false);
    }
}
