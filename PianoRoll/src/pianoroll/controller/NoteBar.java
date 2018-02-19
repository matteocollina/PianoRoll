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
import model.NoteButton;

/**
 *
 * @author MacBook
 */
public class NoteBar extends JPanel implements ActionListener{ 
    private final int WIDTH = 150; 
    private Synthesizer synth = JSyn.createSynthesizer();
    private SineOscillator osc = new SineOscillator();
    private LineOut lineOut = new LineOut();
    
    private void initSynth(){
        synth.add(osc);
        synth.add(lineOut);
        osc.output.connect(0, lineOut.input, 0);
        osc.output.connect(0, lineOut.input, 1);
        synth.start();
        
    }
    public NoteBar() {
        super();
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(WIDTH, 0));
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        initSynth();
    }
    
    public void setNoteButtons(){
        this.removeAll();
        int countall = 10; //TODO: Create var
        
        for (int i = 0; i < countall; i++) {
            NoteButton noteBtn = new NoteButton(i);
            noteBtn.setPreferredSize(new Dimension(WIDTH, this.getHeight()/countall));
            noteBtn.addActionListener(this);
            this.add(noteBtn);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NoteButton noteBtn = (NoteButton) e.getSource();
        //TODO.
        lineOut.start();
        TimeStamp timeStamp = new TimeStamp( synth.getCurrentTime() + 0.5);
        double freq = 220.0 * noteBtn.getFrequence();
        double timeBetweenNotes = 1.0;
        double noteDuration = 0.3;
        osc.noteOn(freq, 0.5, timeStamp);
        osc.noteOff(timeStamp.makeRelative(noteDuration));
    }
    
}
