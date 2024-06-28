package eholli9_FinalProj;

import basicgraphics.SpriteComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class Timer extends SpriteComponent
{
    static int time;
    boolean stopped;
    
    void init() {
        start(0, 1000);
        stopped = true;
    }
    
    void reset() {
        time = 0;
    }
    
    void start() {
        stopped = false;
    }
    
    void stop() {
        stopped = true;
    }
    
    
    @Override
    public void postMove() {
        if( (time < 1000) && (!stopped) ) {
            time++;
        }
    }
    
    @Override
    public void paintBackground(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, d.width, d.height);
        
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 9));
        g.drawString(String.format("%03d", time) , 1,12);
    }
}
