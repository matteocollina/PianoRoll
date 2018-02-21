/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author MacBook
 */
public class PianoRollContent extends JFrame{    
    private static final String TITLE = "Piano Roll Microtonale";
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 400;
    private final TopBar topBar = new TopBar();
    private NoteBar noteBar = new NoteBar();
    private Body body = new Body();
    
    public PianoRollContent(){
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        
        add(topBar,BorderLayout.NORTH);
        add(noteBar,BorderLayout.WEST);
        add(body,BorderLayout.CENTER);
        
        setVisible(true);
        reloadGraphic();
    }
    
    
    /**
     * - Set notes
     * - Set piano roll's lines
     */
    public void reloadGraphic(){
        noteBar.setNoteButtons();
        body.setLines();
        revalidate();
        repaint();
    }
    protected void sayhi(){
        System.out.println("Hiiiiii");
    }
}
