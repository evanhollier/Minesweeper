package eholli9_FinalProj;

import basicgraphics.BasicFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Highscores 
{   
    final static String DIRECTORY = "src\\eholli9_FinalProj\\SCORES\\";
    static File FILE = new File(DIRECTORY + "scores.txt");
    static BufferedWriter WRITER;
    static BufferedReader READER;
    
    
    static BasicFrame pop;
    // Pop up with fastest time in each category
    static void popScores() throws IOException {
        if (pop != null) {
            pop.dispose(); // only one instance allowed
        }
        // popup
        pop = new BasicFrame("Fastest Times");
        String[][] layout = {
            {"cat1", "score1", "name1"},
            {"cat2", "score2", "name2"},
            {"cat3", "score3", "name3"},
            {"reset", "ok", "ok"}
        };
        // static text items
        pop.add(layout, "cat1", new JLabel("Beginner:     ", SwingConstants.RIGHT));
        pop.add(layout, "cat2", new JLabel("Intermediate:     ", SwingConstants.RIGHT));
        pop.add(layout, "cat3", new JLabel("Expert:     ", SwingConstants.RIGHT));
        
        String[] lines = readScore();
        // score items
        pop.add(layout, "score1", new JLabel(lines[0]));
        pop.add(layout, "score2", new JLabel(lines[2]));
        pop.add(layout, "score3", new JLabel(lines[4]));
        
        // name items
        pop.add(layout, "name1", new JLabel(lines[1]));
        pop.add(layout, "name2", new JLabel(lines[3]));
        pop.add(layout, "name3", new JLabel(lines[5]));
        
        // buttons
        JButton reset = new JButton("Reset scores");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    WRITER = new BufferedWriter(new FileWriter(FILE));
                    for (int i = 0; i < 3; i++) { // default scores
                        WRITER.append(String.valueOf(999));
                        WRITER.newLine();
                        WRITER.append("Unknown");
                        WRITER.newLine();
                    }
                    WRITER.close();
                    popScores(); // refresh
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        pop.add(layout,"reset",reset);
                
        JButton done = new JButton("OK");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pop.dispose();
            }
        });
        pop.add(layout,"ok",done);
        
        pop.jf.setPreferredSize(new Dimension(350,150));
        pop.jf.setResizable(false);
        pop.show();
    }
    
    
    // Writer
    void init() throws IOException {
        if(!FILE.exists()) {
            // Create the file, since it doesn't exist.
            WRITER = new BufferedWriter(new FileWriter(FILE));
            FILE.createNewFile(); // create the file

            for(int i=0; i<3; i++) { // default scores
                WRITER.append(String.valueOf(999));
                WRITER.newLine();
                WRITER.append("Unknown");
                WRITER.newLine();
            }
        } else { 
            // File found, leave it as it is.
            String[] lines = readScore();
            WRITER = new BufferedWriter(new FileWriter(FILE));
            for(int i=0; i<6; i++) { // default scores
                WRITER.append(lines[i]);
                WRITER.newLine();
            }
        }
        WRITER.close();
    }
    
    
    /**
     * Attempts to add a score, if it's higher than the current high score.
     * @param mod Minesweeper.DIFF.scoreModifier 
     * @param score Timer.time when won.
     * @throws IOException 
     */
    static void addScore(String mod, int score) throws IOException {
        if(mod.equals("Custom")) {
            return; // do nothing for Custom
        }
        
        String[] lines = readScore();
        WRITER = new BufferedWriter(new FileWriter(FILE));
        
        switch(mod) {
            case "Beginner":
                // Beginner
                WRITER.append(String.valueOf(score));
                WRITER.newLine();
                WRITER.append(userName);
                WRITER.newLine();
                
                for(int i=2; i<6; i++) {
                    WRITER.append(lines[i]);
                    WRITER.newLine();
                }
                
                WRITER.close();
                break;
            case "Intermediate":
                // Intermediate
                WRITER.append(lines[0]);
                WRITER.newLine();
                WRITER.append(lines[1]);
                WRITER.newLine();
                
                WRITER.append(String.valueOf(score));
                WRITER.newLine();
                WRITER.append(userName);
                WRITER.newLine();
                
                WRITER.append(lines[4]);
                WRITER.newLine();
                WRITER.append(lines[5]);
                
                WRITER.close();
                break;
            case "Expert":
                // Expert
                for(int i=0; i<4; i++) {
                    WRITER.append(lines[i]);
                    WRITER.newLine();
                }   
                
                WRITER.append(String.valueOf(score));
                WRITER.newLine();
                WRITER.append(userName);
                
                WRITER.close();
                break;
        }
    }
    
    static BasicFrame pop2;
    static String userName;
    static void getName(String mod) { // Helper method for addScore. Must be called before.
        if(mod.equals("Custom")) {
            return; // do nothing for Custom
        }
        userName = (String) JOptionPane.showInputDialog(
                pop2.getFrame(),
                String.format("You have the fastest time for %s level.", mod),
                "New High Score",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "Unknown"
        );
        if(userName == null) { // Cancel option
            userName = "Unknown";
        } else if(userName.length() >= 16) {
            userName = userName.substring(0, 16); // trim to 16 chars
        }
    }
    
    // Reader
    static String[] readScore() throws IOException {
        READER = new BufferedReader(new FileReader(FILE));
        String[] scores = new String[6];
        
        for (int i=0; i<scores.length; i++) {
            scores[i] = READER.readLine();
        }
        READER.close();
        return scores;
    }
}
