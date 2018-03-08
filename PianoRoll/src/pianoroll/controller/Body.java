/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import model.NoteBlock;
import model.utils.ConfigManager;

/**
 *
 * @author MacBook
 */
public class Body extends JPanel {
    public Body() {
        super();
        setBackground(Color.ORANGE);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

    public void setLines() {
        this.removeAll();
        int countAudioLines = ConfigManager.getInstance().getConfigCountNoteButtons();
        int countMisure = ConfigManager.getInstance().getConfigCountMisureButtons();
        int minDurate = ConfigManager.getInstance().getConfigMinDurate();
        
        int widthLine = getWidthLine(countMisure, minDurate);
        
        JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        content.setPreferredSize(new Dimension(widthLine, this.getHeight()));
        content.setBackground(Color.BLACK);
        
        JScrollPane scroll = new JScrollPane(content,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.setAutoscrolls(true);
        scroll.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

        
        for (int i = 0; i < countAudioLines; i++) {
            JPanel partPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 4));
            partPanel.setPreferredSize(new Dimension(widthLine, getHeightLine()));
            partPanel.setBackground(i % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
            
            for (int p = 0; p < countMisure; p++) {
                for (int m = 0; m < minDurate; m++) {
                    NoteBlock misure = new NoteBlock(m,p,i,getHeightLine());
                    partPanel.add(misure);                 
                }
            } 
            content.add(partPanel);
        }
        this.add(scroll);
    }
    private int getWidthLine(int misure, int minDurate) {
        int widthCalculatedLine = ((minDurate * NoteBlock.WIDTH_MIN_DURATE) * misure) + (int)(minDurate*misure*NoteBlock.WIDTH_MIN_DURATE*0.1);
        return  widthCalculatedLine < this.getWidth() ? this.getWidth() : widthCalculatedLine;
    }
    private int getHeightLine() {
        return this.getHeight() / ConfigManager.getInstance().getConfigCountNoteButtons();
    }
}
