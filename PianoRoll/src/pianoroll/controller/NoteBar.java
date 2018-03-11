/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.softsynth.shared.time.TimeStamp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import jm.music.data.Note;
import jm.util.Play;
import model.NoteButton;
import model.utils.ConfigManager;

/**
 *
 * @author MacBook
 */
public class NoteBar extends JPanel implements ActionListener{ 
    private final int WIDTH = 150; 
    
    public NoteBar() {
        super();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, 0));
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    }
    
    public void setNoteButtons(){
        this.removeAll();
        float[] listFrequences = ConfigManager.getListFrequences();
        for (int i = 0; i < listFrequences.length; i++) {
            NoteButton noteBtn = new NoteButton(listFrequences[i]);
            noteBtn.setPreferredSize(new Dimension(WIDTH, this.getHeight()/listFrequences.length));
            noteBtn.addActionListener(this);
            this.add(noteBtn);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NoteButton noteBtn = (NoteButton) e.getSource();
        Play.midi(new Note(Note.freqToMidiPitch(noteBtn.getFrequence()), ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE));
    }
    
}
