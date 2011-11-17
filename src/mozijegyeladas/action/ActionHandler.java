/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.action;

/**
 *
 * @author Peter
 */
public final class ActionHandler {
    
    public static final AHExitAction Exit = new AHExitAction();
    public static final AHShowRoomInformationAction ShowRoomInformation = new AHShowRoomInformationAction();
    public static final AHShowMovieInformationAction ShowMovieInformation = new AHShowMovieInformationAction();
    
    // Private constructor, igy veletlenul sem nem lehet peldanyositani
    private ActionHandler() {
    }
    
}
