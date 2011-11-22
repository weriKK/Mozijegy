/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class RoomInformationTableModel extends AbstractTableModel {
    
    private SQLServer db;
    private final String[] columnNames;
    private ArrayList<Object[]> rowData;
        
    public RoomInformationTableModel(SQLServer db) {
        this.db = db;
        
        columnNames = new String[] {"Terem Név","Sorok Száma","Oszlopok Száma","Férőhelyek"};
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
    
    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    private void LoadData() {
        
        if ( rowData != null)
            rowData.clear();
        
       
        rowData = db.GetRoomInformation();
    }
    
}
