/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mozijegyeladas.gui;

import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Peter
 */
public class SeatIcon implements Icon {

    private int width;
    private int height;
    
    private Color bgColor;;
    
    public SeatIcon(Color iconBackgroudnColor) {
        super();
        
        this.width = 16;
        this.height = 20;        
        
        this.bgColor = iconBackgroudnColor;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(bgColor);
        g2d.fillRect(x +1, y + 1, width-2, height-2);

        g2d.setColor(Color.BLACK);
        //g2d.drawRect(x +1 ,y + 1,width -2 ,height -2);
        g2d.drawRect(x, y, width-1, height -1);
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}