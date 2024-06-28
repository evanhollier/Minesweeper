package eholli9_FinalProj;

import basicgraphics.BasicFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuBar       
{
    static BasicFrame pop;
    
    void init(BasicFrame bf) {
        bf.addMenuAction("Game", String.format("New %20s", "F2"), new Runnable() {
            @Override
            public void run() {
                Minesweeper.run();
            }
        });

        bf.addMenuAction("Game", "Beginner", new Runnable() {
            @Override
            public void run() {
                Minesweeper.DIFF.NUM_ROWS = 12;
                Minesweeper.DIFF.NUM_COLUMNS = 12;
                Minesweeper.DIFF.NUM_MINES = 18;
                Minesweeper.DIFF.scoreModifier = "Beginner";
                Minesweeper.run();
            }
        });
        
        bf.addMenuAction("Game", "Intermediate", new Runnable() {
            @Override
            public void run() {
                Minesweeper.DIFF.NUM_ROWS = 16;
                Minesweeper.DIFF.NUM_COLUMNS = 16;
                Minesweeper.DIFF.NUM_MINES = 40;
                Minesweeper.DIFF.scoreModifier = "Intermediate";
                Minesweeper.run();
            }
        });
        
        bf.addMenuAction("Game", "Expert", new Runnable() {
            @Override
            public void run() {
                Minesweeper.DIFF.NUM_ROWS = 16;
                Minesweeper.DIFF.NUM_COLUMNS = 30;
                Minesweeper.DIFF.NUM_MINES = 99;
                Minesweeper.DIFF.scoreModifier = "Expert";
                Minesweeper.run();
            }
        });
        
        
        bf.addMenuAction("Game", "Custom", new Runnable() {
            @Override
            public void run() {
                if (pop != null) {
                    pop.dispose(); // only one instance allowed
                }
                // popup
                pop = new BasicFrame("Custom Field");
                String[][] layout = {
                    {"height"  ,"input1", "submit"},
                    {"width"  ,"input2", "submit"},
                    {"mines"  ,"input3", "cancel"}
                };
                
                // text-only items
                pop.add(layout,"height",new JLabel("Height:")); 
                pop.add(layout,"width",new JLabel("Width:"));
                pop.add(layout,"mines",new JLabel("Mines:"));
                
                // fields auto-populated with current values
                final JTextField input1 = new JTextField(""+Minesweeper.DIFF.NUM_ROWS); 
                final JTextField input2 = new JTextField(""+Minesweeper.DIFF.NUM_COLUMNS);
                final JTextField input3 = new JTextField(""+Minesweeper.DIFF.NUM_MINES);
                pop.add(layout,"input1",input1);
                pop.add(layout,"input2",input2);
                pop.add(layout,"input3",input3);
                
                JButton submit = new JButton("OK");
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {  
                        try { // in case non-int input
                            int r = Integer.parseInt(input1.getText());
                            int c = Integer.parseInt(input2.getText());
                            int m = Integer.parseInt(input3.getText());
                            
                            // Height must be between 12-24, inclusive.
                            if(r >= 12 && r <= 24) {
                                Minesweeper.DIFF.NUM_ROWS = r;
                            } else if(r < 12) {
                                Minesweeper.DIFF.NUM_ROWS = 12;
                            } else {
                                Minesweeper.DIFF.NUM_ROWS = 24;
                            }
                            
                            // Width must be between 12-30, inclusive.
                            if(c >= 12 && c <= 30) {
                                Minesweeper.DIFF.NUM_COLUMNS = c;
                            } else if(c < 12) {
                                Minesweeper.DIFF.NUM_COLUMNS = 12;
                            } else {
                                Minesweeper.DIFF.NUM_COLUMNS = 30;
                            }
                            
                            // Mines must be between 18 to (Rows-1)x(Columns-1), inclusive.
                            if((m >= 18) && (m <= ((Minesweeper.DIFF.NUM_ROWS-1)*(Minesweeper.DIFF.NUM_COLUMNS-1)))) {
                                Minesweeper.DIFF.NUM_MINES = m;
                            } else if(m < 18) {
                                Minesweeper.DIFF.NUM_MINES = 18;
                            } else {
                                Minesweeper.DIFF.NUM_MINES = (Minesweeper.DIFF.NUM_ROWS-1)*(Minesweeper.DIFF.NUM_COLUMNS-1);
                            }
                            
                            Minesweeper.DIFF.scoreModifier = "Custom";
                            
                            pop.dispose();
                            Minesweeper.run();
                        } catch(NumberFormatException er) {
                            System.err.println("NumberFormatException: "
                                    + er.getMessage()
                                    + ", could not convert string into a numeric format."
                            );
                        }
                    }
                });
                pop.add(layout,"submit",submit);
                
                JButton cancel = new JButton("Cancel");
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pop.dispose();
                    }
                });
                pop.add(layout,"cancel",cancel);
                
                pop.jf.setPreferredSize(new Dimension(200,150));
                pop.jf.setResizable(false);
                pop.show();
            }
        });
        
        bf.addMenuAction("Game", "Best Times", new Runnable() {
            @Override
            public void run() {
                try {
                    Highscores.popScores();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        bf.addMenuAction("Game", "Exit", new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });
    }
}
