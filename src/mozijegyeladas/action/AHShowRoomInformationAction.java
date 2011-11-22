/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.action;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import mozijegyeladas.db.SQLServer;
import mozijegyeladas.gui.RoomInformationDialog;

/**
 *
 * @author Peter
 */
public class AHShowRoomInformationAction extends AbstractAction {
    
    private RoomInformationDialog roomInformationDlg;
    private Window owner;
    private SQLServer db;
    
    public AHShowRoomInformationAction(Window owner, SQLServer db) {
        super("Termek...",null);
        putValue(SHORT_DESCRIPTION,"Termek információinak megtekintése.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
        this.owner = owner;
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ( roomInformationDlg == null )
            roomInformationDlg = new RoomInformationDialog(this.owner,db);
        else
            roomInformationDlg.UpdateDialog();
        
        roomInformationDlg.setVisible(true);
    }    
    
}
