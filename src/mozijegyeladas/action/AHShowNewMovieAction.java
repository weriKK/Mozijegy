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
import mozijegyeladas.gui.NewMovieDialog;

/**
 *
 * @author Peter
 */
public class AHShowNewMovieAction extends AbstractAction {
    
    private NewMovieDialog newMovieDlg;
    private Window owner;
    private SQLServer db;
    
    public AHShowNewMovieAction(Window owner, SQLServer db) {
        super("Új film...",null);
        putValue(SHORT_DESCRIPTION,"Új film felvétele az adatbázisba.");
        putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        //putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
        this.owner = owner;
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ( newMovieDlg == null )
            newMovieDlg = new NewMovieDialog(this.owner,db);
        
        newMovieDlg.setVisible(true);
    }    
    
}
