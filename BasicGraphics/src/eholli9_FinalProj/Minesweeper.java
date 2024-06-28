package eholli9_FinalProj;

import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Minesweeper
{   
    static Difficulty DIFF = new Difficulty();
    static BasicFrame bf;
    static Timer TIMER = new Timer();
    static Highscores SCORE = new Highscores();
    
    static void run()
    {   
        if(bf != null) {
            bf.dispose(); // only one instance allowed
        }
        bf = new BasicFrame("Minesweeper");        
        
        // tiles width + margin + (bar4 + bar5), tiles height + margin + menuBar + (bar1 + bar2 + bar3 + bar6)
        Dimension BOARD_SIZE = new Dimension(DIFF.NUM_COLUMNS*16 + 19 + 32, DIFF.NUM_ROWS*16 + 41 + 26 + 64); 
        final Color GRAY1 = new Color(192, 192, 192);
        final Color GRAY2 = new Color(128, 128, 128);
        
        Board gb = new Board();
        gb.init();
        
        String[][] GAME_LAYOUT = new String[gb.layout.length + 4][gb.layout[0].length + 2];
        
        Board.squaresLeft = (Minesweeper.DIFF.NUM_ROWS*Minesweeper.DIFF.NUM_COLUMNS) - Minesweeper.DIFF.NUM_MINES;
        
        // Menu bar
        MenuBar mb = new MenuBar();
        mb.init(bf);
        
        // Key input
        bf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    run();
                }
            }
        });
        
        
        // Outer shell
        for(int c = 0; c < GAME_LAYOUT[0].length; c++) {
            GAME_LAYOUT[0][c] = "bar1";
        }
        SpriteComponent bar1 = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              g.setColor(GRAY1);
              g.fillRect(7, 3, 9+(DIFF.NUM_COLUMNS*16)+9, 16);
              g.setColor(GRAY2);
              g.fillRect(13, 9, (DIFF.NUM_COLUMNS*16)+5, 2);
              g.fillRect(13, 11, 2, 5);
              g.setColor(Color.WHITE);
              g.fillRect((DIFF.NUM_COLUMNS*16)+17, 10, 2, 6);
              g.setColor(GRAY1);
              g.fillRect((DIFF.NUM_COLUMNS*16)+17, 10, 1, 1);
              
              //bottom
          }
        };
        bf.add(GAME_LAYOUT, "bar1", bar1);
        
        
        //bar2
        GAME_LAYOUT[1][0] = "bar2L";
        SpriteComponent bar2L = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              g.setColor(GRAY1);
              g.fillRect(7, 0, 9, 16);
              g.setColor(GRAY2);
              g.fillRect(13, 0, 2, 16);
          }
        };
        bf.add(GAME_LAYOUT, "bar2L", bar2L);
        
        GAME_LAYOUT[1][1] = "bar2M";
        MineCounter bar2M = new MineCounter();
        bar2M.init();
        bf.add(GAME_LAYOUT, "bar2M", bar2M);
        
        for(int n=0; n < ( (DIFF.NUM_COLUMNS-1)/ 2 ) -1; n++) {
            GAME_LAYOUT[1][n+2] = "bar2pL";
        }
        SpriteComponent bar2pL = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              Dimension d = getSize();
              g.setColor(GRAY1);
              g.fillRect(0, 0, d.width, d.height);
          }
        };
        bf.add(GAME_LAYOUT, "bar2pL", bar2pL);
        
        GAME_LAYOUT[1][( ((DIFF.NUM_COLUMNS-1)/2)-1 ) +2] = "bar2S";
        Smiley bar2S = new Smiley();
        bar2S.init();
        bf.add(GAME_LAYOUT, "bar2S", bar2S);
        
        for(int n=0; n < ( (DIFF.NUM_COLUMNS/2)-1 ); n++) {
            GAME_LAYOUT[1][n+ ( ((DIFF.NUM_COLUMNS-1)/2)-1 ) +3] = "bar2pR";
        }
        SpriteComponent bar2pR = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              Dimension d = getSize();
              g.setColor(GRAY1);
              g.fillRect(0, 0, d.width, d.height);
          }
        };
        bf.add(GAME_LAYOUT, "bar2pR", bar2pR);
        
        GAME_LAYOUT[1][GAME_LAYOUT[0].length -2] = "bar2T";
        TIMER.reset();
        TIMER.stop();
        bf.add(GAME_LAYOUT, "bar2T", TIMER);
        
        GAME_LAYOUT[1][GAME_LAYOUT[0].length -1] = "bar2R";
        SpriteComponent bar2R = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              g.setColor(GRAY1);
              g.fillRect(0, 0, 9, 16);
              g.setColor(Color.WHITE);
              g.fillRect(1, 0, 2, 16);
          }
        };
        bf.add(GAME_LAYOUT, "bar2R", bar2R);
        
        // bar3
        for(int c = 0; c < GAME_LAYOUT[0].length; c++) {
            GAME_LAYOUT[2][c] = "bar3";
        }
        SpriteComponent bar3 = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              g.setColor(GRAY1);
              g.fillRect(7, 0, 9+(DIFF.NUM_COLUMNS*16)+9, 16);
              g.setColor(GRAY2);
              g.fillRect(13, 13, 3+(DIFF.NUM_COLUMNS*16)+2, 3);
              g.setColor(Color.WHITE);
              g.fillRect(16+(DIFF.NUM_COLUMNS*16)+1, 14, 2, 2);
              g.setColor(GRAY1);
              g.fillRect(16+(DIFF.NUM_COLUMNS*16), 15, 1, 1);
              g.fillRect(16+(DIFF.NUM_COLUMNS*16)+1, 14, 1, 1);
              
              g.setColor(GRAY2);
              g.fillRect(13, 0, 2, 6);
              g.setColor(Color.WHITE);
              g.fillRect(14, 5, (DIFF.NUM_COLUMNS*16)+5, 2);
              g.fillRect((DIFF.NUM_COLUMNS*16)+17, 0, 2, 5);
              g.setColor(GRAY1);
              g.fillRect(14, 5, 1, 1);
          }
        };
        bf.add(GAME_LAYOUT, "bar3", bar3);
        
        // bar4
        for(int r = 3; r < GAME_LAYOUT.length -1; r++) {
            GAME_LAYOUT[r][0] = "bar4";
        }
        SpriteComponent bar4 = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              Dimension d = getSize();
              g.setColor(GRAY1);
              g.fillRect(7, 0, 6, d.height);
              g.setColor(GRAY2);
              g.fillRect(13, 0, 3, d.height);
          }
        };
        bf.add(GAME_LAYOUT, "bar4", bar4);
        
        // bar5
        for(int r = 3; r < GAME_LAYOUT.length -1; r++) {
            GAME_LAYOUT[r][GAME_LAYOUT[0].length -1] = "bar5";
        }
        SpriteComponent bar5 = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              Dimension d = getSize();
              g.setColor(Color.WHITE);
              g.fillRect(0, 0, 3, d.height);
              g.setColor(GRAY1);
              g.fillRect(3, 0, 6, d.height);
          }
        };
        bf.add(GAME_LAYOUT, "bar5", bar5);
        
        // bar6
        for(int c = 0; c < GAME_LAYOUT[0].length; c++) {
            GAME_LAYOUT[GAME_LAYOUT.length -1][c] = "bar6";
        }
        SpriteComponent bar6 = new SpriteComponent()
        {
          @Override
          public void paintBackground(Graphics g) {
              g.setColor(GRAY1);
              g.fillRect(7, 0, 9+(DIFF.NUM_COLUMNS*16)+9, 9);
              g.setColor(Color.WHITE);
              g.fillRect(14, 0, 2+(DIFF.NUM_COLUMNS*16)+3, 3);
              g.setColor(GRAY2);
              g.fillRect(13, 0, 2, 2);
              g.setColor(GRAY1);
              g.fillRect(14, 1, 1, 1);
              g.fillRect(15, 0, 1, 1);
          }
        };
        bf.add(GAME_LAYOUT, "bar6", bar6);
        
        
        // Fill in rest of GAME_LAYOUT with gameboard.layout
        for (int r = 3; r < GAME_LAYOUT.length -1; r++) {
            for (int c = 1; c < GAME_LAYOUT[0].length -1; c++) {
                GAME_LAYOUT[r][c] = gb.layout[r-3][c-1];
            }
        }
        
        
        gb.draw(GAME_LAYOUT, bf);
        
        bf.jf.setResizable(false); // For some reason, boards smaller than 12x12 are disjointed because of this.
        bf.jf.setPreferredSize(BOARD_SIZE);
        bf.show();
    }
    
    public static void main(String[] args) throws IOException 
    {
        TIMER.init();
        SCORE.init();
        Minesweeper.run();
    }
}