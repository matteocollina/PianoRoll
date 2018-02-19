/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author MacBook
 */
public class Body extends JPanel{  
    
    public Body() {
        super();
        setBackground(Color.ORANGE);
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    }
    
    public void setLines(){
        this.removeAll();
        int countall = 10; //TODO: Create var
        
        for (int i = 0; i < countall; i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(this.getWidth(), (int)((this.getHeight()/countall)*1)));
            panel.setBackground(i%2==0 ? Color.BLUE : Color.PINK);
            
            for (int j = 0; j < countall; j++) {
                JCheckBox jCheckBox = new JCheckBox();
                panel.add(jCheckBox);
            }
            
            this.add(panel);
        }
    }
}
