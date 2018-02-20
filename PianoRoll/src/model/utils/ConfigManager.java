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


public class ConfigManager {
    private static ConfigManager singleton = new ConfigManager( );

    private Properties _properties;
    private final String _filename = "pianoroll.properties";
    
    private String KEY_CONFIG_COUNT_NOTE_BTNS = "key_count_note_btns";
    private int DEFAULT_CONFIG_COUNT_NOTE_BTNS = 10;
    private String KEY_CONFIG_MIN_FREQ = "key_min_freq";
    private String DEFAULT_CONFIG_MIN_FREQ = "27.5";
    private String KEY_CONFIG_MAX_FREQ = "key_max_freq";
    private String DEFAULT_CONFIG_MAX_FREQ = "4186.01";
    private String KEY_CONFIG_BPM = "key_bpm";
    private String DEFAULT_CONFIG_BPM = "100";
 
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

   /* Other methods protected by singleton-ness */
   protected static void demoMethod( ) {
      System.out.println("demoMethod for singleton");
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
    public String getConfigBPM(){
        String value = this.read(KEY_CONFIG_BPM);
        return (value==null || value.compareTo("")==0) ? DEFAULT_CONFIG_BPM : value;
    }
}