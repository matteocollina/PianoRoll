/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import model.SettingGroupPanel;
import model.utils.ConfigManager;
import model.utils.KeyLocate;
import model.utils.Utils;

/**
 *
 * @author MacBook
 */
public class TopBar extends JPanel{

    private final int HEIGHT = 80; //30
    private final SettingButton btnStop = new SettingButton(Utils.getStopIcon(HEIGHT,HEIGHT, (float) 0.5));
    private final SettingButton btnPause = new SettingButton(Utils.getPauseIcon(HEIGHT,HEIGHT, (float) 0.5));
    private final SettingButton btnPlay = new SettingButton(Utils.getPlayIcon(HEIGHT,HEIGHT, (float) 0.5));
    private SettingButton[] listButtons = {btnStop,btnPause,btnPlay};
    private String[] listTimbri = {"Sine"};
    private JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    private int MAX_CHAR = 4;
    private int MAX_CHAR_BTN_NOTES = 2;
    private int WIDTH_GROUP = 50;
    private int HEIGHT_GROUP = 50;
    private int MAX_CHAR_FREQ = 7;
    private int WIDTH_FREQ = 80;
    IntTextField minFreqTextField;
    IntTextField maxFreqTextField;
    IntTextField bpmTextField;
    IntTextField countNoteButtonsTextField;
    PianoRollContent pianoRollContent;
    
    
    public TopBar() {
        super();
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(0, HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.TRAILING));
               
        minFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMinFreq(),MAX_CHAR_FREQ);        
        SettingGroupPanel minFreqGroupPanel = new SettingGroupPanel(WIDTH_FREQ, HEIGHT_GROUP, minFreqTextField, Utils.getAppString(KeyLocate.FREQ_MIN));
        this.add(minFreqGroupPanel);
        
        maxFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMaxFreq(),MAX_CHAR_FREQ);
        SettingGroupPanel maxFreqGroupPanel = new SettingGroupPanel(WIDTH_FREQ, HEIGHT_GROUP, maxFreqTextField, Utils.getAppString(KeyLocate.FREQ_MAX));
        this.add(maxFreqGroupPanel);
        
        
        
        JLabel timeLabel = new JLabel("00:00");
        this.add(timeLabel);
        
        for (int i = 0; i < listButtons.length; i++) {
            this.add(listButtons[i]);
        }  
        
        bpmTextField = new IntTextField(ConfigManager.getInstance().getConfigBPM(),4);
        SettingGroupPanel bpmGroupPanel = new SettingGroupPanel(WIDTH_GROUP, HEIGHT_GROUP, bpmTextField, Utils.getAppString(KeyLocate.BPM));
        this.add(bpmGroupPanel);
        
        JComboBox comboListTimbri = new JComboBox(listTimbri);
        this.add(comboListTimbri);
        
        countNoteButtonsTextField = new IntTextField(Integer.toString(ConfigManager.getInstance().getConfigCountNoteButtons()),MAX_CHAR_BTN_NOTES);                
        SettingGroupPanel settingGroupPanel = new SettingGroupPanel(WIDTH_GROUP, HEIGHT_GROUP, countNoteButtonsTextField, Utils.getAppString(KeyLocate.TASTI));
        this.add(settingGroupPanel);
        
        JButton btnSave = new JButton(Utils.getAppString(KeyLocate.SALVA));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfig();
            }
        });
        this.add(btnSave);        
    }
    
    private void saveConfig(){
        ConfigManager.getInstance().setConfigMinFreq(minFreqTextField.getText());
        ConfigManager.getInstance().setConfigMaxFreq(maxFreqTextField.getText());
        ConfigManager.getInstance().setConfigBPM(bpmTextField.getText());
        ConfigManager.getInstance().setConfigCountNoteButtons(countNoteButtonsTextField.getText());
        JOptionPane.showMessageDialog(topFrame, Utils.getAppString(KeyLocate.CONFIGURAZIONE_SALVATA));

        getPianoRollContent().sayhi();
        getPianoRollContent().reloadGraphic();
    }
    
    private PianoRollContent getPianoRollContent(){
        return (PianoRollContent) javax.swing.SwingUtilities.getWindowAncestor(this);
    }
        
}

