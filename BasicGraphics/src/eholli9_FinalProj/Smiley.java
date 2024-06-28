package eholli9_FinalProj;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Smiley extends SpriteComponent
{    
    static Sprite im = new Sprite();
    static boolean done;
    
    void init() {
        im.setPicture(new Picture("/SPRITES/smiley1.png"));
        addSprite(im);
        start(0, 10);
        
        done = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Minesweeper.run();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!done) {
                    im.setPicture(new Picture("/SPRITES/smiley2.png"));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(!done) {
                    im.setPicture(new Picture("/SPRITES/smiley1.png"));
                }
            }
        });
    }
    
    static void lose() {
        done = true;
        im.setPicture(new Picture("/SPRITES/smiley3.png"));
    }
    static void win() {
        done = true;
        im.setPicture(new Picture("/SPRITES/smiley4.png"));
    }
}
