/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Peter
 */
public class Seat extends SeatLabel {
    
    private SeatIcon icon;
    
    private int row;
    private int col;
    
    private int status;
    
    public static final Color COLOR_FREE = new Color(200,255,200);
    public static final Color COLOR_BOOKED = new Color(255,200,200);
    public static final Color COLOR_SOLD = Color.RED;    
    
    private boolean isSelected;
    
    private Border selectedBorder;
    private Border unselectedBorder;
    
    
    public Seat( int row, int col, int status, MouseAdapter actionHandler)
    {
        super(null);
        
        this.isSelected = false;
        this.addMouseListener(actionHandler);
        
        this.status = status;
        this.row = row;
        this.col = col;
        
        this.unselectedBorder = BorderFactory.createLineBorder(Color.BLACK,1);        
        this.selectedBorder = BorderFactory.createLineBorder(Color.BLUE,2);
        
        this.setBorder(this.unselectedBorder);
        SetIcon();
        
    }
    
    public int getRowPosition() {
        return this.row;
    }
    
    public int getColPosition() {
        return this.col;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus( int status) {
        this.status = status;
        SetIcon();
    }
    
    public void setSelected(boolean isSelected) {
        
        if ( this.isSelected != isSelected) {
            this.isSelected = isSelected;

            if ( this.isSelected ) {
//                this.icon.setBorderColor(Color.BLUE);
                this.setBorder(this.selectedBorder);
            } else {
//                this.icon.setBorderColor(Color.BLACK);
                this.setBorder(this.unselectedBorder);
            }
            
            //this.validate();
        }

    }
    
    public boolean isSelected() {
        return this.isSelected;
    }

    private void SetIcon() {
        
        if ( this.icon == null ) {
            this.icon = new SeatIcon(COLOR_FREE);
            this.setIcon(this.icon);
        }
        
        switch ( this.status ) {
            case 0:
                this.icon.setBgColor(COLOR_FREE);
                break;
            case 1:
                this.icon.setBgColor(COLOR_BOOKED);
                break;                
            case 2:
                this.icon.setBgColor(COLOR_SOLD);
                break;                
            default:
                this.icon.setBgColor(COLOR_FREE);
        }
        
        this.updateUI();
    }
    
}
