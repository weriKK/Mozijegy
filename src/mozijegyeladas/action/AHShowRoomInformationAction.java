/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author Peter
 */
public class AHShowRoomInformationAction extends AbstractAction {
    public AHShowRoomInformationAction() {
        super("Termek...",null);
        putValue(SHORT_DESCRIPTION,"Termek információink megtekintése.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }    
    
}
