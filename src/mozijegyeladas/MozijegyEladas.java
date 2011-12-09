package mozijegyeladas;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Peter
 */
public class MozijegyEladas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

            // A GUI letrehozasa. SwingUtilities segitsegevel
            // biztos, hogy az Event-Dispatching Thread-en
            // for meghivodni.
        try {            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(MozijegyEladas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
        
            public void run() {
                new mozijegyeladas.gui.MainFrame();                
            }
            
        });
        
    }
} 