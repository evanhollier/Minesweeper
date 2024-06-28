package eholli9_FinalProj;

import basicgraphics.SpriteComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class MineCounter extends SpriteComponent
{
    static int count;
    
    void init() {
        count = Minesweeper.DIFF.NUM_MINES;
        start(0, 10);
    }
    
    @Override
    public void paintBackground(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, d.width, d.height);
        
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 9));
        g.drawString(String.format("%03d", count) , 1,12);
    }
    
}
