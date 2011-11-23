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
public class MovieInformationTableModel extends AbstractTableModel {
    
    private SQLServer db;
    private final String[] columnNames;
    private ArrayList<Object[]> rowData;
        
    public MovieInformationTableModel(SQLServer db) {
        this.db = db;
        
        // az adat table tartalmaz meg ket oszlopot, id es szinopsis
        // de ezt nem akarjuk a tablazatban megjeleniteni oket
        // erzert kulon metodust irtunk es a tablatol elrejtuk ezteket a mezoket
        columnNames = new String[] {"Cím","Szinkron","Származás","Rendező","Hossz"};
        rowData = null;
        
        LoadData();
    }
    

    @Override
    public int getRowCount() {
        return rowData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length-1;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if ( columnIndex == 1 )
            return (Integer)rowData.get(rowIndex)[columnIndex] == 1 ? "szinkronizált" : "feliratos";
        
        return rowData.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if ( columnIndex >= this.columnNames.length )
            return;
        
        if ( columnIndex == 1)
            rowData.get(rowIndex)[columnIndex] = ((String)aValue).equals("szinkronizált") ? 1 : 2;
        else
            rowData.get(rowIndex)[columnIndex] = aValue;
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    // Szinopszis az utolso mezo
    // oszlopok szama+2 re
    public String getSynopse(int rowIndex) {
        return (rowData.get(rowIndex)[this.columnNames.length+1]).toString();
    }
    
    public void setSynopse(int rowIndex, String value) {
        rowData.get(rowIndex)[this.columnNames.length+1] = value;
    }
    
    // movie_id az utolso elotti mezo
    // oszlopok szama+1 re
    public int getRowId(int rowIndex) {
        return (Integer)rowData.get(rowIndex)[this.columnNames.length];
    }
    
    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
        
        //LoadData();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    
    private void LoadData() {
        
        if ( rowData != null)
            rowData.clear();
        
        rowData = db.GetMovieInformation();
    }
    
    public boolean UpdateTable(boolean updateDatabase) {
        
        if ( !updateDatabase ) {
            LoadData();
            return true;
        }
        
        // Adatbazis frissitese
                
        return db.SetMovieInformation(rowData);        
    }
    
}
