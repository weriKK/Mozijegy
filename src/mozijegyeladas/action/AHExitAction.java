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
public class AHExitAction extends AbstractAction {
    
    public AHExitAction() {
        super("Kilépés",null);
        putValue(SHORT_DESCRIPTION,"Kilépés a programból.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_K);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
}
