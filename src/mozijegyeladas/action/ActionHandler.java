/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.action;

import java.awt.Window;
import mozijegyeladas.db.SQLServer;

/**
 *
 * @author Peter
 */
public final class ActionHandler {
    
    public AHExitAction Exit;
    public AHShowRoomInformationAction ShowRoomInformation;
    public AHShowMovieInformationAction ShowMovieInformation;
    public AHShowNewMovieAction ShowNewMovie;
    
    public AHShowNewShowAction NewShowInformation;
    
    public AHShowShowInformationAction ShowShowInformation;    
    
    // Private constructor, igy veletlenul sem nem lehet peldanyositani
    public ActionHandler(Window owner, SQLServer db) {
        Exit = new AHExitAction();
        
        ShowRoomInformation = new AHShowRoomInformationAction(owner, db);
        ShowMovieInformation = new AHShowMovieInformationAction(owner, db);        
        
        ShowNewMovie = new AHShowNewMovieAction(owner, db);
        
        NewShowInformation = new AHShowNewShowAction(owner, db);
        
        ShowShowInformation = new AHShowShowInformationAction(owner, db);
    }
    
}
