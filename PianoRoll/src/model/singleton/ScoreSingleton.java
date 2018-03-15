/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.singleton;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.ImpulseOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.softsynth.shared.time.TimeStamp;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.View;
import jm.JMC;
import static jm.constants.Durations.M;
import static jm.constants.Durations.SB;
import jm.util.*;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;
import model.NoteBlock;
import model.utils.ConfigManager;
import model.instrument.GoogleWaveOscillator;
import model.Oscillator;
import model.utils.KeyLocate;
import model.utils.Timer;
import model.utils.Utils;
import pianoroll.controller.PianoRollContent;
/**
 *
 * @author MacBook
 */
public class ScoreSingleton implements JMC {

    Synthesizer synth;
    ArrayList listOscillators;
    LineOut lineOut;
    private JLabel timeLabel;    
    private static ScoreSingleton instance;
    //private HashMap<Float,ArrayList<Boolean>> score;
    private Score score;

    private ScoreSingleton() {

    }


    //static block initialization for exception handling
    static {
        try {
            instance = new ScoreSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static ScoreSingleton getInstance() {
        return instance;
    }

    public void reset() {
        System.out.println("---- Reset singleton ----");

        float[] listFrequences = ConfigManager.getListFrequences();
        int countMisure = ConfigManager.getInstance().getConfigCountMisureButtons();
        int minMisure = ConfigManager.getInstance().getConfigMinDurate();

        score = new Score("Exam");
        score.clean();
        score.setTempo(Double.valueOf(ConfigManager.getInstance().getConfigBPM()));
        for (int i = 0; i < listFrequences.length; i++) {
            Part part = new Part(Integer.toString(i));
            for (int ph = 0; ph < countMisure; ph++) {
                Phrase phrase = new Phrase(Integer.toString(ph));
                for (int j = 0; j < minMisure; j++) {
                    Note n = new Note(REST, ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE);
                    phrase.add(n);
                }
                part.add(phrase);
            }
            score.add(part);
        }
        initSynth();
    }

    public void setTimeLabel(JLabel jLabel){
        timeLabel = jLabel;
    }
    public JLabel getTimeLabel(){
        return timeLabel;
    }
    public void setNoteInPartAndPhraseAthIndex(NoteBlock noteBlock, boolean set) {
        Part oldPart = score.getPart(noteBlock.getPart());
        Phrase oldPhrase = oldPart.getPhrase(noteBlock.getPhrase());
        float frequence = ConfigManager.getListFrequences()[Integer.valueOf(oldPart.getTitle())];

        Note n = set ? new Note(Note.freqToMidiPitch(frequence), ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE) : new Note(REST, ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE);
        n.setDuration(n.getRhythmValue());
        oldPhrase.setNote(n, noteBlock.getIndex());
    }
    
   
    private UnitOscillator getOscillator(){
        switch(ConfigManager.getInstance().getConfigTypeOScillator()){
            case SINE:{
                return new SineOscillator();
            }
            case WAVE:{
                return new GoogleWaveOscillator();
            }
            case SAW:{
                return new SawtoothOscillator();
            }
            case TRIANGLE:{
                return new TriangleOscillator();
            }
            case SQUARE:{
                return new SquareOscillator();
            }
            default:{
                return new SineOscillator();
            }
            
            
        }
    }

    private void initSynth() {
        System.out.println("Init Synth");
        // Create a context for the synthesizer.
        synth = JSyn.createSynthesizer();

        // Add an output mixer.
        synth.add(lineOut = new LineOut());

        // Add a tone generator.
        // Connect the oscillator to the left and right audio output.
        listOscillators = new ArrayList<>();
        for (int i = 0; i < score.getPartArray().length; i++) {            
            //TODO: Swith and create class osc type
            UnitOscillator osc = getOscillator();
            //L'ampiezza è suddivisa tra i canali (??)
            double countOscillators = 100 / ConfigManager.getListFrequences().length;
            osc.amplitude.set(countOscillators);
            synth.add(osc);
            osc.getOutput().connect(0, lineOut.input, 0);
            osc.getOutput().connect(0, lineOut.input, 1);
            listOscillators.add(osc);
        }
        // Start synthesizer using default stereo output at 44100 Hz.
        synth.start();
    }

    public void play() {    
        try {
            System.out.println("---------------- PLAY ----------------");
            lineOut.stop();

            // Get synthesizer time in seconds.
            double timeNow = synth.getCurrentTime();

            // Advance to a near future time so we have a clean start.
            TimeStamp timeStamp = new TimeStamp(timeNow);
            TimeStamp timeStampParts = new TimeStamp(timeStamp.getTime());

            Score s = new Score();
            s.setTempo(score.getTempo());

            for (int indp = 0; indp < score.getPartArray().length; indp++) {
                Part currPart = score.getPartArray()[indp];

                timeStamp = new TimeStamp(timeStampParts.getTime());
                //System.out.println("PART " + indp + " at : " + timeStamp.getTime());

                for (int indph = 0; indph < currPart.getPhraseArray().length; indph++) {
                    Phrase currPhrase = currPart.getPhraseArray()[indph];
                    for (int i = 0; i < currPhrase.getNoteArray().length; i++) {
                        Note currNote = currPhrase.getNoteArray()[i];

                        double freq = currNote.getFrequency();
                        double duration = currNote.getDuration();
                        UnitOscillator currOscillator = (UnitOscillator) listOscillators.get(indp);
                        //TODO: Revisone Ampiezza, se le note sono allo steso istante, diminuire amp.
                        currOscillator.noteOn(freq, 1.0 / ConfigManager.getListFrequences().length, timeStamp);
                        currOscillator.noteOff(timeStamp.makeRelative(duration));
                        if (currNote.getPitch() != REST) {
                            //System.out.println("\tNote " + i + " from : " + timeStamp.getTime() + " to: " + timeStamp.makeRelative(duration).getTime());
                        }
                        timeStamp = timeStamp.makeRelative(duration);
                    }
                }
            }
            // We only need to start the LineOut. It will pull data from the oscillator
            lineOut.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        } 
        int endTransactionTime = 0; //or 500 ms
        long duratePulsation = (long) (60.0/score.getTempo() * 1000); //ms
        long endTime = (long) (score.getEndTime() * 1000) + endTransactionTime;
        System.out.println("BPM : " + score.getTempo() + ""
                + "\nDurata pulsazione (s) : " + (duratePulsation/1000)
                + "\nDurata brano (s) : " + (endTime/1000));

        
        Timer timer = new Timer(duratePulsation, endTime) {            
            @Override
            public void start() {
                System.out.println("start");
                manageClock(getElapsedTimeTicking());
                super.start(); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            protected void onFinish() {
                System.out.println("onFinish ");
                manageClock(getElapsedTimeTicking());
                stop(); 
            }

            @Override
            protected void onTick() {
                
            }

            @Override
            protected void onTicking() {
                manageClock(getElapsedTimeTicking());
            }
        };
        
        try {
            timer.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
    }


    public void stop() {
        // Stop everything.
        System.out.println("---------------- STOP ----------------");
        double fadeOut = 500;
        lineOut.stop(new TimeStamp(fadeOut));
    }

    public void pause() {
        lineOut.stop(); //TODO
    }
   
    public void readScore() {
        System.out.println(score.toString());
    }
    
    private void manageClock(double seconds){     
       getTimeLabel().setBackground(Color.red);
       getTimeLabel().setText(Double.toString(seconds));
    }

    

}
