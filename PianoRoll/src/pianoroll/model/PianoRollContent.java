/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.model;

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
    private TopBar topBar = new TopBar();
    private NoteBar noteBar = new NoteBar();
    private Body body = new Body();
    
    public PianoRollContent(){
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());
        
        add(topBar,BorderLayout.NORTH);
        add(noteBar,BorderLayout.WEST);
        add(body,BorderLayout.CENTER);
        
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setVisible(true);
    }
    public TopBar getTopBar(){
        return this.topBar;
    }
    public NoteBar getNoteBar(){
        return this.noteBar;
    }
    public Body getBody(){
        return this.body;
    }
}
