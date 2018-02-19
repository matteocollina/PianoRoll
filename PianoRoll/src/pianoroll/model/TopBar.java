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
public class TopBar extends JPanel{
    private final int HEIGHT = 30; 
    
    public TopBar() {
        super();
        setBackground(Color.RED);
        setPreferredSize(new Dimension(0, HEIGHT));
    }
}

