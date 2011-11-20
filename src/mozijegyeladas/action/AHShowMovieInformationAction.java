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
import mozijegyeladas.gui.MovieInformationDialog;

/**
 *
 * @author Peter
 */
public class AHShowMovieInformationAction extends AbstractAction {
    
    private MovieInformationDialog movieInformationDlg;
    private Window owner;
    private SQLServer db;
    
    public AHShowMovieInformationAction(Window owner, SQLServer db) {
        super("Filmek...",null);
        putValue(SHORT_DESCRIPTION,"Filmek információinak megtekintése.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_F);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
        this.owner = owner;
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ( movieInformationDlg == null )
            movieInformationDlg = new MovieInformationDialog(this.owner, this.db);
        
        movieInformationDlg.setVisible(true);
    }    
    
}
