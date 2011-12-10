/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class ShowListPanel extends JPanel {
    
    SQLServer db;
    JTable table;
    ShowListTableModel tableData;
    TableRowSorter<ShowListTableModel> tableSorter;
    
    MovieBookingDialog movieBookingDialog;
    
    JLabel  lFilter;
    JTextField txtFilter;

    public ShowListPanel(SQLServer db) {
        super(true);
        
        this.db = db;
        
        InitializePanel();
    }

    private void InitializePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        InitializeTable();
        InitializeFilters();
    }

    private void InitializeTable() {
        this.tableData = new ShowListTableModel(db);
        
        this.table = new JTable(this.tableData);
        this.table.setAutoCreateRowSorter(false); 
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
        
        // Filter 
        
        tableSorter = new TableRowSorter<ShowListTableModel>(tableData);
        this.table.setRowSorter(tableSorter);
        
//        List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
//        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
//        tableSorter.setSortKeys(sortKeys);
        
        
        // Ha rendezunk vagy szurunk, frissiteni kell a kivalasztott sor indexet
        // table.convertRowIndexToModel(table.getSelectedRow());
        
        // Dupla kattintas
       this.table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                 if (e.getClickCount() == 2) {

                     // Tabla aktualis nezetenek indexeit vissza kell
                     // alakitani a modellnek megfelelo indexekre
                     // Szures soran ezek elcsuszhattak
                     JTable target = (JTable)e.getSource();
                     int viewRow = table.getSelectedRow();

                     if ( viewRow < 0 ) {
                         // Semmi sincs kijelolve az aktualis nezetben
                         return;
                     }
                     
                     int modelRow = target.convertRowIndexToModel(target.getSelectedRow());
//                     System.out.println("Double clicked on: "+tableData.showID(modelRow));
                     OpenMovieBookingDialog(modelRow);
                 }
            }
       });
    }

    private void InitializeFilters() {
        lFilter = new JLabel("Szűrő: ");
        txtFilter = new JTextField();
        txtFilter.setMaximumSize(new Dimension(Short.MAX_VALUE,25));
        
        // Esemeny kezeles
        txtFilter.addKeyListener(new KeyListener() {

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
                ApplyRowFilter();
            }
        });        
        
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel,BoxLayout.X_AXIS));
        filterPanel.add(lFilter);
        filterPanel.add(txtFilter);  
        
        this.add(Box.createRigidArea(new Dimension(Short.MAX_VALUE,3)));
        this.add(filterPanel);
    }
    
    private void ApplyRowFilter() {
        
        RowFilter<ShowListTableModel,Object> rowFilter = null;
        
        try {
            // a 2. es 3. oszlop alapjan szurjon (Terem,Film)
            rowFilter = RowFilter.regexFilter(txtFilter.getText(), 2,3);
        }
        catch ( java.util.regex.PatternSyntaxException ex )
        {
            // Ha a filter nem jo, ne tortenjen semmi
            return;
        }
        
        this.tableSorter.setRowFilter(rowFilter);
        
    }
    
    private void OpenMovieBookingDialog(int row) {
        
        ArrayList<Object> showData = new ArrayList<Object>();
        
        for ( int i = 0; i < this.tableData.getColumnCount(); i++) {
            showData.add(this.tableData.getValueAt(row, i));
        }
        
        System.out.println(showData);

        
        movieBookingDialog = new MovieBookingDialog(this.db,showData);
        
        movieBookingDialog.setVisible(true);
        
    }
    
}
