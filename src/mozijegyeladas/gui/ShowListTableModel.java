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
public class ShowListTableModel extends AbstractTableModel {
    
    private SQLServer db;
    private final String[] columnNames;
    private ArrayList<Object[]> rowData;
        
    public ShowListTableModel(SQLServer db) {
        this.db = db;
        
        columnNames = new String[] {"show_id","Kezdet","Terem","Film","Hossz","Foglalt","Kiadott","Szabad"};
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
    
    public int getRoomRowCount(int rowIndex) {
        return (Integer)rowData.get(rowIndex)[8];
    }
    
    public int getRoomColumnCount(int rowIndex) {
        return (Integer)rowData.get(rowIndex)[9];
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
        
       
        rowData = db.GetShowListData();
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

    Object getRoomID(int rowIndex) {
        return (Integer)rowData.get(rowIndex)[10];
    }
    
}
