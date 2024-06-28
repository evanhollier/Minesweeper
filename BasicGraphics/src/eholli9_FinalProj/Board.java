package eholli9_FinalProj;

import basicgraphics.BasicFrame;
import basicgraphics.images.Picture;
import java.util.Random;

public class Board 
{
    static boolean timerStarted;
    
    void init() {
        timerStarted = false;
        genLayout();
        initRealBoard();
        popMines();
        popNumbers();
    }
    void draw(String[][] layout, BasicFrame bf) {
        String location;
        for (int r = 0; r < Minesweeper.DIFF.NUM_ROWS; r++) {
            for (int c = 0; c < Minesweeper.DIFF.NUM_COLUMNS; c++) {
                Tile t = realBoard[r][c];
                t.init();            
                
                location = String.format("%dx%d", r, c);
                bf.add(layout, location, t);
            }
        }
    }
    
    
    String[][] layout = new String[Minesweeper.DIFF.NUM_ROWS][Minesweeper.DIFF.NUM_COLUMNS];
    
    // Used for adding to the basic frame with a String[][] layout.
    void genLayout() 
    {
        for(int r=0; r<Minesweeper.DIFF.NUM_ROWS; r++) {
            for(int c=0; c<Minesweeper.DIFF.NUM_COLUMNS; c++) {
                layout[r][c] = String.format("%dx%d",r,c);
            }
        }
    }
    
    
    
    // Used to track each square.
    Tile[][] realBoard = new Tile[Minesweeper.DIFF.NUM_ROWS][Minesweeper.DIFF.NUM_COLUMNS];
    
    // realBoard Tiles are all null; call constructor for every index.
    void initRealBoard() {
        for (int r = 0; r < realBoard.length; r++) {
            for (int c = 0; c < realBoard[0].length; c++) {
                realBoard[r][c] = new Tile(this);
            }
        }
    }
    
    // Populate realBoard with mines.
    void popMines()
    {
        int[][] mines = genMinefield();
        for (int[] mine : mines) {
            realBoard[mine[0]][mine[1]].hasMine = true;
        }
    }
    
    // Generates random coordinate for one mine in format [r, c].
    int[] genMine() 
    {
        Random rand = new Random();
        int[] coord = new int[2];
        coord[0] = rand.nextInt(Minesweeper.DIFF.NUM_ROWS); // num between 0 and r-1
        coord[1] = rand.nextInt(Minesweeper.DIFF.NUM_COLUMNS); // num between 0 and c-1
        return coord;
    }
    
    // Generates coordinates for every mine. Guarantees each coord is unique.
    int[][] genMinefield()
    {
        int[][] coords = new int[Minesweeper.DIFF.NUM_MINES][2];
        int minesPlaced = 0;
        
        coords[minesPlaced][0] = genMine()[0];
        coords[minesPlaced][1] = genMine()[1];
        // [0] is unique.
        minesPlaced++; // 0 -> 1
        
        while (minesPlaced < Minesweeper.DIFF.NUM_MINES) { 
            // Generate a mine. It is assumed to be non-unique.
            coords[minesPlaced][0] = genMine()[0];
            coords[minesPlaced][1] = genMine()[1];
            // If duplicate, re-generate until unique.
            while(isUnique(coords, minesPlaced) == false) {
                coords[minesPlaced][0] = genMine()[0];
                coords[minesPlaced][1] = genMine()[1];
            }
            // The mine is now unique.
            minesPlaced++;
        }
        return coords;
    }
    // Helper method for genMinefield.
    boolean isUnique(int[][] coords, int x)
    {
        // Scan left from index x.
        for (int i = x-1; i >= 0; i--) {
            if (coords[x][0] == coords[i][0] && coords[x][1] == coords[i][1]) {
                return false; // Found duplicate.
            }
        }
        return true; // Did not find duplicate.
    }
    
    // Populate realBoard with number clues.
    void popNumbers()
    {
        int mines;
        for(int r=0; r<realBoard.length; r++) {
            for(int c=0; c<realBoard[0].length; c++) {
                // Make sure current square is not a mine.
                if(realBoard[r][c].hasMine == true) {
                    continue;
                }
                mines = 0;
                // Check all 8 directions, if they're in-bounds.
                if(r-1 >= 0 && c-1 >= 0) { // up left
                    if(realBoard[r-1][c-1].hasMine == true) {
                        mines++;
                    }
                }
                if(r-1 >= 0) { // up
                    if(realBoard[r-1][c].hasMine == true) {
                        mines++;
                    }
                }
                if(r-1 >= 0 && c+1 < realBoard[0].length) { // up right
                    if(realBoard[r-1][c+1].hasMine == true) {
                        mines++;
                    }
                }
                if(c-1 >= 0) { // left
                    if(realBoard[r][c-1].hasMine == true) {
                        mines++;
                    }
                }
                if(c+1 < realBoard[0].length) { // right
                    if(realBoard[r][c+1].hasMine == true) {
                        mines++;
                    }
                }
                if(r+1 < realBoard.length && c-1 >= 0) { // down left
                    if(realBoard[r+1][c-1].hasMine == true) {
                        mines++;
                    }
                }
                if(r+1 < realBoard.length) { // down
                    if(realBoard[r+1][c].hasMine == true) {
                        mines++;
                    }
                }
                if(r+1 < realBoard.length && c+1 < realBoard[0].length) { // down right
                    if(realBoard[r+1][c+1].hasMine == true) {
                        mines++;
                    }
                }
                
                realBoard[r][c].neighborMines = mines;
            }
        }
    }
    
    void revealChunk() {
        for (int r = 0; r < realBoard.length; r++) {
            for (int c = 0; c < realBoard[0].length; c++) {                
                if((realBoard[r][c].neighborMines == 0) 
                        && ((realBoard[r][c].isRevealed) && (!realBoard[r][c].hasFlag))) {
                    // Reveal all 8 directions, if they're in-bounds.
                    if(r-1 >= 0 && c-1 >= 0) { // up left
                        realBoard[r-1][c-1].reveal();
                    }
                    if(r-1 >= 0) { // up
                        realBoard[r-1][c].reveal();
                    }
                    if(r-1 >= 0 && c+1 < realBoard[0].length) { // up right
                        realBoard[r-1][c+1].reveal();
                    }
                    if(c-1 >= 0) { // left
                        realBoard[r][c-1].reveal();
                    }
                    if(c+1 < realBoard[0].length) { // right
                        realBoard[r][c+1].reveal();
                    }
                    if(r+1 < realBoard.length && c-1 >= 0) { // down left
                        realBoard[r+1][c-1].reveal();
                    }
                    if(r+1 < realBoard.length) { // down
                        realBoard[r+1][c].reveal();
                    }
                    if(r+1 < realBoard.length && c+1 < realBoard[0].length) { // down right
                        realBoard[r+1][c+1].reveal();
                    }
                }
            }
        }
    }

    void revealMines() {
        for (int r = 0; r < realBoard.length; r++) {
            for (int c = 0; c < realBoard[0].length; c++) {
                realBoard[r][c].isRevealed = true; // no longer revealable
                
                if((!realBoard[r][c].hasFlag) && (realBoard[r][c].hasMine)) {
                    realBoard[r][c].im.setPicture(new Picture("/SPRITES/mine.jpg"));
                }
                else if((realBoard[r][c].hasFlag) && (!realBoard[r][c].hasMine)) {
                    realBoard[r][c].im.setPicture(new Picture("/SPRITES/wrong.png"));
                }
                
            }
        }
    }
    
    static int squaresLeft;
    
    void flagAll() {
        for (int r = 0; r < realBoard.length; r++) {
            for (int c = 0; c < realBoard[0].length; c++) {
                if((!realBoard[r][c].isRevealed) && (!realBoard[r][c].hasFlag)) {
                    realBoard[r][c].toggleFlag();
                    realBoard[r][c].isRevealed = true; // so unflagged mines are unrevealable
                } 
                else if((!realBoard[r][c].isRevealed) && (realBoard[r][c].hasFlag)) {
                    realBoard[r][c].isRevealed = true; // so flagged mines are unrevealable
                }
            }
        }
    }
}
