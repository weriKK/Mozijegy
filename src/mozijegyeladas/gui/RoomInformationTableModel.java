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
public class RoomInformationTableModel extends AbstractTableModel {
    
    private SQLServer db;
    private final String[] columnNames;
    private ArrayList<Object[]> rowData;
        
    public RoomInformationTableModel(SQLServer db) {
        this.db = db;
        
        columnNames = new String[] {"Cím","Szinkron","Származás","Rendező","Hossz","Szinopszis"};
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData.get(rowIndex)[columnIndex];
    }

    private void LoadData() {
        
        if ( rowData != null)
            rowData.clear();
        
        rowData = db.GetRoomInformation();
    }
    
}
