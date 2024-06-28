package eholli9_FinalProj;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.io.IOException;

public class Tile extends SpriteComponent
{    
    Board board;
    Sprite im;
    boolean isRevealed;
    boolean hasMine;
    boolean hasFlag;
    int neighborMines; // 0-8
    
    public Tile(Board board) {
        this.board = board;
        im = new Sprite();
        isRevealed = false;
        hasMine = false;
        hasFlag = false;
        neighborMines = -1;
    }

    void init() {
        im.setPicture(new Picture("/SPRITES/hidden.png"));
        addSprite(im);
        addMouseListener(new MouseInput(this));
        start(0, 10);
    }
    
    void reveal() {
        if(!isRevealed && !hasFlag) { // do nothing if revealed already or if flagged
            if(hasMine) { // lose
                im.setPicture(new Picture("/SPRITES/mine2.jpg"));
                hasMine = false; // to stay wrong.jpg
                Minesweeper.TIMER.stop();
                Smiley.lose();
                board.revealMines();
            }
            else {
                isRevealed = true;
                Board.squaresLeft--;
                switch(neighborMines) {
                    case 0:
                        im.setPicture(new Picture("/SPRITES/0.jpg"));
                        board.revealChunk();
                        break;
                    case 1:
                        im.setPicture(new Picture("/SPRITES/1.png"));
                        break;
                    case 2:
                        im.setPicture(new Picture("/SPRITES/2.png"));
                        break;
                    case 3:
                        im.setPicture(new Picture("/SPRITES/3.png"));
                        break;
                    case 4:
                        im.setPicture(new Picture("/SPRITES/4.png"));
                        break;
                    case 5:
                        im.setPicture(new Picture("/SPRITES/5.png"));
                        break;
                    case 6:
                        im.setPicture(new Picture("/SPRITES/6.png"));
                        break;
                    case 7:
                        im.setPicture(new Picture("/SPRITES/7.png"));
                        break;
                    case 8:
                        im.setPicture(new Picture("/SPRITES/8.png"));
                        break;
                }
                if(Board.squaresLeft == 0) { // won
                    Minesweeper.TIMER.stop();
                    board.flagAll();
                    
                    if(!Smiley.done) {
                        System.out.printf("Score: %d %n", Timer.time);
                    }
                    
                    try {
                        Highscores.getName(Minesweeper.DIFF.scoreModifier); // 1
                        Highscores.addScore(Minesweeper.DIFF.scoreModifier, Timer.time);
                    } catch (IOException ex) {}
                    
                    Smiley.win();
                }
            }
        }
    }
    
    void toggleFlag() {
        if(!isRevealed) {
            if(!hasFlag) {
                im.setPicture(new Picture("/SPRITES/flag.png"));
                hasFlag = true;
                MineCounter.count--;
            } else {
                im.setPicture(new Picture("/SPRITES/hidden.png"));
                hasFlag = false;
                MineCounter.count++;
            }
        }
    }   
}
