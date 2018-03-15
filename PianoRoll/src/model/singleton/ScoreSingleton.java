/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.singleton;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.softsynth.shared.time.TimeStamp;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import model.NoteBlock;
import model.utils.ConfigManager;
import model.instrument.GoogleWaveOscillator;
import model.utils.TimerAudio;
import pianoroll.controller.TopBar;

/**
 *
 * @author MacBook
 */
public class ScoreSingleton implements JMC {

    Synthesizer synth;
    ArrayList listOscillators;
    LineOut lineOut;
    private JLabel timeLabel;
    private TopBar topBar;
    private DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
    private boolean inOnPause = false;
    private HashMap<TimeStamp,HashMap<Double,Double>> noteMap = new HashMap<>(); //timestamp: <freq,duration>

    private static ScoreSingleton instance;
    private Score score;
    private TimerAudio timerSong;

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
        manageClock(0);
    }

    public void setTopbar(TopBar topBar) {
        this.topBar = topBar;
    }

    private TopBar getTopbar() {
        return this.topBar;
    }

    public void setNoteInPartAndPhraseAthIndex(NoteBlock noteBlock, boolean set) {
        Part oldPart = score.getPart(noteBlock.getPart());
        Phrase oldPhrase = oldPart.getPhrase(noteBlock.getPhrase());
        float frequence = ConfigManager.getListFrequences()[Integer.valueOf(oldPart.getTitle())];

        Note n = set ? new Note(Note.freqToMidiPitch(frequence), ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE) : new Note(REST, ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE);
        n.setDuration(n.getRhythmValue());
        oldPhrase.setNote(n, noteBlock.getIndex());
    }

    private UnitOscillator getOscillator() {
        switch (ConfigManager.getInstance().getConfigTypeOScillator()) {
            case SINE: {
                return new SineOscillator();
            }
            case WAVE: {
                return new GoogleWaveOscillator();
            }
            case SAW: {
                return new SawtoothOscillator();
            }
            case TRIANGLE: {
                return new TriangleOscillator();
            }
            case SQUARE: {
                return new SquareOscillator();
            }
            default: {
                return new SineOscillator();
            }

        }
    }
    
    private double getDurationNote(){
        return 60.0 / score.getTempo();
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
    }

    public void play() {
        //Block permission to play while playing.
        if (canPlaying()) {
            //if resume from pause
            if (!inOnPause) {
                try {
                    System.out.println("---------------- PLAY ----------------");
                    blockPlaying();

                    // Start synthesizer using default stereo output at 44100 Hz.
                    synth.start();
                    
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
                                
                                /*
                                - es. 120BPM => Pulsazione dura 60/120=0.5s
                                - 0.5s / 4 = 0.125s     //note da 1/4
                                (oppure 0.5s / 8 = 0.0625s     //note da 1/8
                                */
                                double duration = getDurationNote() / ConfigManager.getInstance().getConfigMinDurate();                                 
                                
                                UnitOscillator currOscillator = (UnitOscillator) listOscillators.get(indp);
                                

                                currOscillator.noteOn(freq, 1.0 / ConfigManager.getListFrequences().length, timeStamp);
                                currOscillator.noteOff(timeStamp.makeRelative(duration));
                                
                                //TODO: Revisone Ampiezza, se le note sono allo steso istante, diminuire amp.
                                noteMap.put(timeStamp, new HashMap<Double, Double>() {{
                                    put(freq,duration);
                                }});
                                
                                if (currNote.getPitch() != REST) {
                                    System.out.println("duration : " + duration);
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
                

                int endTransactionTime = 500; //or 500 ms
                long duratePulsation = (long) (getDurationNote() * 1000); //ms
                
                
                long endTime = (long)(((getDurationNote()) 
                                            * ConfigManager.getInstance().getConfigCountMisureButtons()) * 1000) 
                                            + endTransactionTime;

                System.out.println("BPM : " + score.getTempo() + ""
                        + "\nDurata pulsazione (ms) : " + (duratePulsation )
                        + "\nDurata brano (ms) : " + (endTime ));

                timerSong = new TimerAudio(duratePulsation, endTime) {
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
                        stop(false);
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
                    timerSong.start();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }else{
                //Resume from pause
                timerSong.resume();
            }
        } else {
            System.out.println("-- song is playing --");
        }
    }

    public void stop(boolean resetTimer) {
        System.out.println("---------------- STOP ----------------");
        synth.stop();
        inOnPause = false;
        allowPlaying();
        lineOut.stop();
        timerSong.cancel();
        if(resetTimer){
            manageClock(0);
        }
    }

    public void pause() {
        allowPlaying();
        timerSong.pause();
        inOnPause = true;
        try {
        } catch (Exception e) {
            System.out.println(">> ERROR while press pause : " + e.toString());
        }
    }

    public void readScore() {
        System.out.println(score.toString());
    }

    private void manageClock(long seconds) {
        getTopbar().getTimeLabel().setText(timeFormatter.format(new Date(seconds)));
    }

    private boolean canPlaying() {
        return getTopbar().getPlayButton().isEnabled();
    }

    private void blockPlaying() {
        getTopbar().getPlayButton().setEnabled(false);
    }

    private void allowPlaying() {
        getTopbar().getPlayButton().setEnabled(true);
    }

}
