/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.IntTextField;
import model.SettingButton;
import model.utils.ConfigManager;
import model.utils.Utils;

/**
 *
 * @author MacBook
 */
public class TopBar extends JPanel{

    private final int HEIGHT = 30;
    private final SettingButton btnStop = new SettingButton(Utils.getStopIcon(HEIGHT,HEIGHT, (float) 0.5));
    private final SettingButton btnPause = new SettingButton(Utils.getPauseIcon(HEIGHT,HEIGHT, (float) 0.5));
    private final SettingButton btnPlay = new SettingButton(Utils.getPlayIcon(HEIGHT,HEIGHT, (float) 0.5));
    private SettingButton[] listButtons = {btnStop,btnPause,btnPlay};
    private String[] listTimbri = {"Sine"};
    private JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

    
    
    public TopBar() {
        super();
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(0, HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.TRAILING));
        
        IntTextField minFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMinFreq(),4);        
        IntTextField maxFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMaxFreq(),4);
        this.add(minFreqTextField);
        this.add(maxFreqTextField);
        
        JLabel timeLabel = new JLabel("00:00");
        this.add(timeLabel);
        
        for (int i = 0; i < listButtons.length; i++) {
            this.add(listButtons[i]);
        }  
        
        IntTextField bpmTextField = new IntTextField("120",4);
        this.add(bpmTextField);
        
        JComboBox comboListTimbri = new JComboBox(listTimbri);
        this.add(comboListTimbri);
        
        JButton btnSave = new JButton("Salva");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(topFrame, "Configuration saved");
            }
        });
        this.add(btnSave);
    }
        
}

