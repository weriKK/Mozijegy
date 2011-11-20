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
public class MovieInformationDialog extends JDialog {
    
    public MovieInformationDialog(Window owner, SQLServer db) {
        super(owner,"Film információk",Dialog.ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);
    }
}
