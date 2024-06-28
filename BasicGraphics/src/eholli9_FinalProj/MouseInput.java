package eholli9_FinalProj;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput extends MouseAdapter
{
    MouseListener ml;
    Tile t;    
    
    MouseInput(Tile tile) {
        t = tile;
    }
    
    MouseAdapter ma = new MouseAdapter() {};    
    
    @Override
    public void mouseClicked(MouseEvent e) {  
        if(e.getButton() == 1) { // Left click
            if(!Board.timerStarted) { // First click
                if(t.neighborMines == -1) { // First clicked a mine, make a new board.
                    Minesweeper.run();
                } 
                else { // Didn't first click a mine. Start Timer.
                    t.reveal(); 
                    Board.timerStarted = true;
                    Minesweeper.TIMER.start();
                }
            } 
            else {
                t.reveal();
            }
        }
        else if(e.getButton() == 3) { // Right click
            t.toggleFlag();
        }
    }
}