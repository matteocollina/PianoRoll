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
import model.utils.ConfigManager;

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
        int countAudioLines = ConfigManager.getInstance().getConfigCountNoteButtons();
        
        for (int i = 0; i < countAudioLines; i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/countAudioLines));
            panel.setBackground(i%2==0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            
            for (int j = 0; j < countAudioLines; j++) {
                JCheckBox jCheckBox = new JCheckBox();
                panel.add(jCheckBox);
            }
            
            this.add(panel);
        }
    }
}
