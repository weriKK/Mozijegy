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
import mozijegyeladas.gui.NewShowDialog;

/**
 *
 * @author Peter
 */
class AHOpenShowInformationAction extends AbstractAction {
    
    private NewShowDialog newShowDlg;
    private Window owner;
    private SQLServer db;    
    
    public AHOpenShowInformationAction(Window owner, SQLServer db) {
        super("Új Előadás...",null);
        putValue(SHORT_DESCRIPTION,"Új Előadások meghirdetése.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
        this.owner = owner;
        this.db = db;        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ( newShowDlg == null )
            newShowDlg = new NewShowDialog(this.owner, this.db);
//        else
//            newShowDlg.UpdateDialog();
        
        newShowDlg.setVisible(true);
    }
    
}
