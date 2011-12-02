/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class ShowInformationTableModel extends AbstractTableModel {
    
    private SQLServer db;
    private final String[] columnNames;
    private ArrayList<Object[]> rowData;
        
    public ShowInformationTableModel(SQLServer db) {
        this.db = db;
        
        columnNames = new String[] {"show_id","Film","Terem","Kezdet","Vége","Foglalások"};
        rowData = null;

        LoadData();

    }
    

    @Override
    public int getRowCount() {
        return rowData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData.get(rowIndex)[columnIndex];
    }
    
    public boolean seatsAreBooked(int rowIndex) {
        return ( (Integer)rowData.get(rowIndex)[5] > 0 );
    }
    
    public int showID(int rowIndex) {
        return (Integer)rowData.get(rowIndex)[0];
    }
    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    private void LoadData() {
        
        if ( rowData != null)
            rowData.clear();
        
       
        rowData = db.GetShowInformation();
    }
    
    public boolean UpdateTable(boolean updateDatabase) {
        
        if ( !updateDatabase ) {
            LoadData();
            return true;
        }
        
        // Adatbazis frissitese
                
        //return db.SetMovieInformation(rowData);        
        return true;
    }    
    
}
