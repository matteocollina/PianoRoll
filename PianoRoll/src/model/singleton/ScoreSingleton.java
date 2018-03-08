/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.singleton;

import java.awt.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import model.NoteBlock;
import model.utils.ConfigManager;

/**
 *
 * @author MacBook
 */
public class ScoreSingleton implements JMC{
    private static ScoreSingleton instance;
    //private HashMap<Float,ArrayList<Boolean>> score;
    private Score score;
    
    private ScoreSingleton(){
        
    }
    
    //static block initialization for exception handling
    static{
        try{
            instance = new ScoreSingleton();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
    
    public static ScoreSingleton getInstance(){
        return instance;
    }
    
    public void reset(){
        System.out.println("Reset singleton");
        float[] listFrequences = ConfigManager.getListFrequences();
        int countMisure = ConfigManager.getInstance().getConfigCountMisureButtons();
        int minMisure = ConfigManager.getInstance().getConfigMinDurate();
        
        score = new Score("Exam");
        score.setTempo(Double.valueOf(ConfigManager.getInstance().getConfigBPM()));
        score.clean();
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
    }
    public void setNoteInPartAndPhraseAthIndex(NoteBlock noteBlock,boolean set){
        Part oldPart = score.getPart(noteBlock.getPart());
        Phrase oldPhrase = oldPart.getPhrase(noteBlock.getPhrase());
        float frequence = ConfigManager.getListFrequences()[Integer.valueOf(oldPart.getTitle())];
           
        Note n = set ? new Note(Note.freqToMidiPitch(frequence), ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE) : new Note(REST, ConfigManager.DEFAULT_CONFIG_RYTHM_MIN_DURATE);
        n.setDuration(n.getRhythmValue());
        oldPhrase.setNote(n, noteBlock.getIndex());

        System.out.println("Part: " + oldPart.getTitle() + "\n" + "Phrase: " + oldPhrase.getTitle());
        System.out.println(score.toString());
    }
    
    /*1.6.4 of jMusic allow*/
    public void play(){
        Play.midi(score, false);
    }
    public void stop(){
        Play.stopAudio();
    }
    public void pause(){
        Play.pauseAudio();
    }
    
    public void readScore(){
        System.out.println(score.toString());
    }
    
}

