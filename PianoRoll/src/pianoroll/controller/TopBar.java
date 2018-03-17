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
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.IntTextField;
import model.Oscillator;
import model.SettingButton;
import model.SettingGroupPanel;
import model.singleton.ScoreSingleton;
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
    private final SettingButton btnRandom = new SettingButton(Utils.getRandomIcon(HEIGHT,HEIGHT, (float) 0.5));
    private SettingButton[] listButtons = {btnStop,btnPlay};
    private SettingButton[] listOtherButtons = {};
    private final Oscillator.OTYPE[] listTimbri = {Oscillator.OTYPE.SINE,Oscillator.OTYPE.WAVE,Oscillator.OTYPE.SAW,Oscillator.OTYPE.SQUARE,Oscillator.OTYPE.TRIANGLE};
    private JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    private int MAX_CHAR = 4;
    private int MAX_CHAR_BTN_NOTES = 2;
    private int MAX_LIMIT_MISURE = 50;
    private int WIDTH_GROUP = 50;
    private int HEIGHT_GROUP = 50;
    private int MAX_CHAR_FREQ = 7;
    private int WIDTH_FREQ = 80;
    IntTextField minFreqTextField;
    IntTextField maxFreqTextField;
    IntTextField bpmTextField;
    IntTextField countNoteButtonsTextField;
    IntTextField countMisuresTextField;
    PianoRollContent pianoRollContent;
    Timer timer = new Timer();
    JComboBox comboListOScillator;
    JLabel timeLabel = new JLabel("");
    
    public TopBar() {
        super();
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(0, HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.TRAILING));
               
        minFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMinFreq(),Utils.FIELDS.LIMIT_CHAR.FREQ,Utils.FIELDS.DEFAULT_VALUE.MIN_FREQ);        
        SettingGroupPanel minFreqGroupPanel = new SettingGroupPanel(WIDTH_FREQ, HEIGHT_GROUP, minFreqTextField, Utils.getAppString(KeyLocate.FREQ_MIN));
        this.add(minFreqGroupPanel);
        
        maxFreqTextField = new IntTextField(ConfigManager.getInstance().getConfigMaxFreq(),Utils.FIELDS.LIMIT_CHAR.FREQ,Utils.FIELDS.DEFAULT_VALUE.MAX_FREQ);
        SettingGroupPanel maxFreqGroupPanel = new SettingGroupPanel(WIDTH_FREQ, HEIGHT_GROUP, maxFreqTextField, Utils.getAppString(KeyLocate.FREQ_MAX));
        this.add(maxFreqGroupPanel);
               
        timeLabel.setBackground(Color.WHITE);
        this.add(timeLabel);
        
        for (int i = 0; i < listButtons.length; i++) {
            this.add(listButtons[i]);
        }  
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                ScoreSingleton.getInstance().play();
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop Audio");
                ScoreSingleton.getInstance().stop(true);
            }
        });
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScoreSingleton.getInstance().pause();
            }
        });
        
        bpmTextField = new IntTextField(ConfigManager.getInstance().getConfigBPM(),Utils.FIELDS.LIMIT_CHAR.BPM,Utils.FIELDS.DEFAULT_VALUE.BPM);
        SettingGroupPanel bpmGroupPanel = new SettingGroupPanel(WIDTH_GROUP, HEIGHT_GROUP, bpmTextField, Utils.getAppString(KeyLocate.BPM));
        bpmTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e); //To change body of generated methods, choose Tools | Templates.
                System.out.println(e.toString());
            }
        });
        this.add(bpmGroupPanel);
        
        comboListOScillator = new JComboBox(listTimbri);
        comboListOScillator.setSelectedItem(ConfigManager.getInstance().getConfigTypeOScillator());
        this.add(comboListOScillator);
        
        
        
        for (int i = 0; i < listOtherButtons.length; i++) {
            this.add(listOtherButtons[i]);
        }
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnRandom.isSelected()) {
                    ScoreSingleton.getInstance().disableRepeat();
                    btnRandom.setSelected(false);
                }else{
                    ScoreSingleton.getInstance().enableRepeat();
                    btnRandom.setSelected(true);
                }
            }
        });

        
        
        
        countNoteButtonsTextField = new IntTextField(Integer.toString(ConfigManager.getInstance().getConfigCountNoteButtons()),Utils.FIELDS.LIMIT_CHAR.NOTE,Utils.FIELDS.DEFAULT_VALUE.NOTE);                
        SettingGroupPanel settingGroupPanel = new SettingGroupPanel(WIDTH_GROUP, HEIGHT_GROUP, countNoteButtonsTextField, Utils.getAppString(KeyLocate.TASTI));
        this.add(settingGroupPanel);
        
        countMisuresTextField = new IntTextField(Integer.toString(ConfigManager.getInstance().getConfigCountMisureButtons()),Utils.FIELDS.LIMIT_CHAR.MISURE,Utils.FIELDS.DEFAULT_VALUE.MISURE);                
        SettingGroupPanel misureGroupPanel = new SettingGroupPanel(WIDTH_GROUP, HEIGHT_GROUP, countMisuresTextField, Utils.getAppString(KeyLocate.MISURE));
        this.add(misureGroupPanel);
        
        SettingButton btnSave = new SettingButton(Utils.getSaveIcon(HEIGHT,HEIGHT, (float) 0.5));        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfig();
            }
        });
        this.add(btnSave); 
        ScoreSingleton.getInstance().setTopbar(this);
    }
    
    private void saveConfig(){          
        ConfigManager.getInstance().setConfigMinFreq(minFreqTextField.getText());
        ConfigManager.getInstance().setConfigMaxFreq(maxFreqTextField.getText());
        ConfigManager.getInstance().setConfigBPM(bpmTextField.getText());
        ConfigManager.getInstance().setConfigCountNoteButtons(countNoteButtonsTextField.getText());
        ConfigManager.getInstance().setConfigCountMisure(countMisuresTextField.getText());
        ConfigManager.getInstance().setConfigTypeOscillatore((Oscillator.OTYPE) comboListOScillator.getSelectedItem());
        //JOptionPane.showMessageDialog(topFrame, Utils.getAppString(KeyLocate.CONFIGURAZIONE_SALVATA));
        getPianoRollContent().reloadGraphic();
    }
    
    private PianoRollContent getPianoRollContent(){
        return (PianoRollContent) javax.swing.SwingUtilities.getWindowAncestor(this);
    }
    public JLabel getTimeLabel(){
        return this.timeLabel;
    }
    public SettingButton getPlayButton(){
        return this.btnPlay;
    }
        
}

