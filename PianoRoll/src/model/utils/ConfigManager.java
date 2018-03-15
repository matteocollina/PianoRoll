/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

/**
 *
 * @author MacBook
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import jm.JMC;
import model.Oscillator;


public class ConfigManager {
    private static ConfigManager singleton = new ConfigManager( );

    private Properties _properties;
    private final String _filename = "pianoroll.properties";
    
    private int DEFAULT_BEAT = 4; //  4/4
    
    private int DEFAULT_CONFIG_MIN_DURATE = 4; //   1/4
    public static final double DEFAULT_CONFIG_RYTHM_MIN_DURATE = JMC.SEMI_QUAVER;
    
    private String KEY_CONFIG_COUNT_NOTE_BTNS = "key_count_note_btns";
    private String KEY_CONFIG_COUNT_MISURE_BTNS = "key_count_misure_btns";
    private int DEFAULT_CONFIG_COUNT_NOTE_BTNS = 10;
    private int DEFAULT_CONFIG_COUNT_MISURE_BTNS = 2;
    private String KEY_CONFIG_MIN_FREQ = "key_min_freq";
    private String DEFAULT_CONFIG_MIN_FREQ = "27.5";
    private String KEY_CONFIG_MAX_FREQ = "key_max_freq";
    private String DEFAULT_CONFIG_MAX_FREQ = "4186.01";
    private String KEY_CONFIG_BPM = "key_bpm";
    private String DEFAULT_CONFIG_BPM = "100";
    private String KEY_CONFIG_OSCILLATOR = "key_oscillator";
    private Oscillator.OTYPE DEFAULT_CONFIG_OSCILLATOR = Oscillator.OTYPE.SINE;
 
    public ConfigManager(){
        this._properties = new Properties();
        File file=new File(this._filename);
 
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) { System.err.println(ex.toString()); }
        }
 
        try {
            this._properties.load(new FileInputStream(this._filename));
        } catch (IOException e) { System.err.println(e.toString()); }
    }
    
   public static ConfigManager getInstance( ) {
      return singleton;
   }
 
    /**
     * read property
     * @param key
     * @return String
     */
    public String read(String key){
        return this._properties.getProperty(key);
    }
 
    /**
     * write property and save
     * @param key
     * @param value 
     */
    public void write(String key, String value){
        this._properties.setProperty(key, value);
        try {
            this._properties.store(new FileOutputStream(this._filename), null);
        } catch (IOException e) { System.err.println(e.toString()); }
    }
    
    /* GET */
    public int getBeat(){
        return DEFAULT_BEAT;
    }
    public int getConfigMinDurate(){
        return DEFAULT_CONFIG_MIN_DURATE;
    }
    public int getConfigCountMisureButtons(){
        String value = this.read(KEY_CONFIG_COUNT_MISURE_BTNS);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_COUNT_MISURE_BTNS : Integer.parseInt(value);
    }
    public int getConfigCountNoteButtons(){
        String value = this.read(KEY_CONFIG_COUNT_NOTE_BTNS);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_COUNT_NOTE_BTNS : Integer.parseInt(value);
    }
    public String getConfigMinFreq(){
        String value = this.read(KEY_CONFIG_MIN_FREQ);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_MIN_FREQ : value;
    }
    public String getConfigMaxFreq(){
        String value = this.read(KEY_CONFIG_MAX_FREQ);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_MAX_FREQ : value;
    }
    public float getConfigStepFreq(){
        return Float.valueOf(getConfigMaxFreq()) - Float.valueOf(getConfigMinFreq());
    }
    public String getConfigBPM(){
        String value = this.read(KEY_CONFIG_BPM);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_BPM : value;
    }
    public Oscillator.OTYPE getConfigTypeOScillator(){
        String value = this.read(KEY_CONFIG_OSCILLATOR);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_OSCILLATOR : Oscillator.fromString(value);
    }
    
    /* SET */
    public  void setConfigCountMisure(String value){
        this.write(KEY_CONFIG_COUNT_MISURE_BTNS,value);
    }
    public  void setConfigCountNoteButtons(String value){
        this.write(KEY_CONFIG_COUNT_NOTE_BTNS,value);
    }
    public void setConfigMinFreq(String value){
        this.write(KEY_CONFIG_MIN_FREQ,value);
    }
    public void setConfigMaxFreq(String value){
        this.write(KEY_CONFIG_MAX_FREQ,value);
    }
    public void setConfigBPM(String value){
        this.write(KEY_CONFIG_BPM,value);
    }
    public void setConfigTypeOscillatore(Oscillator.OTYPE value){
        this.write(KEY_CONFIG_OSCILLATOR,Oscillator.toString(value));
    }
    
    
    
    public static float[] getListFrequences(){
        int countNotesButtons = getInstance().getConfigCountNoteButtons();
        float[] list = new float[countNotesButtons];
        float minFreq = Float.valueOf(getInstance().getConfigMinFreq());
        float maxFreq = Float.valueOf(getInstance().getConfigMaxFreq());        
        float step = countNotesButtons > 2 ? getInstance().getConfigStepFreq() / ((countNotesButtons-2)+1) : 0;
        
        for (int i = 0; i < countNotesButtons; i++) {
            if(i==0){
               list[i] = minFreq;
            }else if(i==countNotesButtons-1){
                list[i] = maxFreq;
            }else{
                list[i] = minFreq + (i*step);
            }
        }
        return list;
    }
}