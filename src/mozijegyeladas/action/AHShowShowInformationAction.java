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
import mozijegyeladas.gui.ShowInformationDialog;

/**
 *
 * @author Peter
 */
public class AHShowShowInformationAction extends AbstractAction {
    
    private ShowInformationDialog showInformationDlg;
    private Window owner;
    private SQLServer db;
    
    public AHShowShowInformationAction(Window owner, SQLServer db) {
        super("Előadások törlése...",null);
        putValue(SHORT_DESCRIPTION,"Előadások információinak megtekintése és előadások törlése.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
        this.owner = owner;
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ( showInformationDlg == null )
            showInformationDlg = new ShowInformationDialog(this.owner,db);
        else
            showInformationDlg.UpdateDialog();
        
        showInformationDlg.setVisible(true);
    }    
    
}
