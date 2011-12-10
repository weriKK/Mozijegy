/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Peter
 */
public class SeatLabel extends JLabel {
    
    public SeatLabel(String labelText)
    {
        super(labelText,SwingConstants.CENTER);
        
        this.setFont(new Font("Tahoma", 1, 12));
        this.setMaximumSize(new Dimension(16, 20));
        this.setMinimumSize(new Dimension(16, 20));
        this.setPreferredSize(new Dimension(16, 20));        
        
//        this.setHorizontalAlignment(SwingConstants.CENTER);        
//        this.setText(labelText);        
//        
//        if ( drawBorder )
//            this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
        
    }
    
}
