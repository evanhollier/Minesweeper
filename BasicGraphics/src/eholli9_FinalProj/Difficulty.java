package eholli9_FinalProj;

public class Difficulty 
{
    int NUM_ROWS;
    int NUM_COLUMNS;
    int NUM_MINES;
    String scoreModifier;
    
    // This object serves as game settings that can be accessed and modified in other classes. 
    // Beginner by default 
    public Difficulty() 
    {
        NUM_COLUMNS = 12;
        NUM_ROWS = 12;
        NUM_MINES = 18;
        scoreModifier = "Beginner";
    }
}
