/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Window;
import javax.swing.JDialog;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public class RoomInformationDialog extends JDialog {
    
    public RoomInformationDialog(Window owner, SQLServer db) {
        super(owner,"Terem információk",Dialog.ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);
        
        
        
        // default close operation, if we hide it, we need
        // an update method we can call in the action handler to update it
        // or just dispose of it on close, not the most elegant solution :)
        
        
    }
}
