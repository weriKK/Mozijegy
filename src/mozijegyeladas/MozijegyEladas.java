package mozijegyeladas;

import javax.swing.SwingUtilities;

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
        SwingUtilities.invokeLater(new Runnable() {
        
            public void run() {
                new mozijegyeladas.gui.MainFrame();                
            }
            
        });
        
    }
} 