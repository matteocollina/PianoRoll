/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.model;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author MacBook
 */
public class NoteBar extends JPanel{ 
    private final int WIDTH = 150; 
    
    public NoteBar() {
        super();
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(WIDTH, 0));
    }
}
