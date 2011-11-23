/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Peter
 */
public abstract class OKCancelDialog extends JDialog {
    
    public static final int OK = 1;
    public static final int CANCEL = 0;
    
    protected JPanel buttonPanel;
    protected JButton okButton;
    protected JButton cancelButton;
    
    protected int lastButtonPressed;
    
    protected OKCancelDialog(Window owner, String title, Dialog.ModalityType modality ) {
        super(owner, title, modality);
        
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        lastButtonPressed = CANCEL;
        
        okButton = new JButton(okAction);
        okButton.setPreferredSize(new Dimension(90,25));
        
        cancelButton = new JButton(cancelAction);
        cancelButton.setPreferredSize(new Dimension(90,25));
        getRootPane().setDefaultButton(cancelButton);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
    }
    
    protected abstract boolean processOK();
    protected abstract void processCancel();
    
    public int getButtonCode() { return lastButtonPressed; }
    
    public void enableOK() { okButton.setEnabled(true); }
    public void disableOK() { okButton.setEnabled(false); }
    public void setOKButtonText(String value) { okButton.setText(value); }
    
    private AbstractAction okAction = new AbstractAction("OK") {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if ( processOK() ) {
                OKCancelDialog.this.setVisible(false);
                lastButtonPressed = OK;
                
            }
        }
    };
    
    private AbstractAction cancelAction = new AbstractAction("Mégsem") {

        @Override
        public void actionPerformed(ActionEvent e) {
            processCancel();
            OKCancelDialog.this.setVisible(false);
            lastButtonPressed = CANCEL;
        }
    };
    
}
