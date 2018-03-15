/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author MacBook
 */
public class Utils {
    private static final String ICO_SETTINGS = "ic_settings_2x.png";
    private static final String ICO_STOP = "ic_stop_black_2x.png";
    private static final String ICO_PAUSE = "ic_pause_black_2x.png";
    private static final String ICO_PLAY = "ic_play_black_2x.png";
    private static Hashtable resourceBundles = new Hashtable();
    private static final String FILENAME_STRINGS = "resources.lang.app_string";
    
    public static class FIELDS {
        public static class DEFAULT_VALUE {
            public static int BPM = 10;
            public static int MIN_FREQ = 100;
            public static int MAX_FREQ = 100;
            public static int NOTE = 1;
            public static int MISURE = 1;
        }
        public static class LIMIT_CHAR {
            public static int BPM = 4;
            public static int FREQ = 7;
            public static int NOTE = 2;
            public static int MISURE = 3;
        }
    }
    
    /**
     * 
     * @param name name of images (include extension)
     * @return Image
     */
    private Image getImageWithName(String name){
        try {/*
            BufferedImage in = ImageIO.read(getClass().getResource("/resources/images/" + name));
            return new BufferedImage(
                in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);*/
            return ImageIO.read(getClass().getResource("/resources/images/" + name));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    private  ImageIcon getIconWithName(String name){
        return new ImageIcon(getImageWithName(name));
    }
    private  ImageIcon getIconWithNameAndSize(String name,int width, int height, float resize){
        return new ImageIcon(getImageWithName(name).getScaledInstance((int) (width * resize), (int) (height * resize),  java.awt.Image.SCALE_SMOOTH ));
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Icons ">
    public static ImageIcon getSettingsIcon(int width, int height, float resize) {
        return new Utils().getIconWithNameAndSize(ICO_SETTINGS, width, height, resize);
    }

    public static ImageIcon getStopIcon(int width, int height, float resize) {
        return new Utils().getIconWithNameAndSize(ICO_STOP, width, height, resize);
    }

    public static ImageIcon getPauseIcon(int width, int height, float resize) {
        return new Utils().getIconWithNameAndSize(ICO_PAUSE, width, height, resize);
    }

    public static ImageIcon getPlayIcon(int width, int height, float resize) {
        return new Utils().getIconWithNameAndSize(ICO_PLAY, width, height, resize);
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc=" Translate ">
    public static String getAppString(String key) {
        return Utils.getResourceString(key, FILENAME_STRINGS);
    }
    public static String getResourceString(String key, String baseName) {
        if (key == null || key.equals("")) {
            return key;
        }
        Locale locale = Locale.getDefault();
        ResourceBundle resource
                = (ResourceBundle) resourceBundles.get(baseName + "_" + locale.toString());
        if (resource == null) {
            try {
                resource = ResourceBundle.getBundle(baseName, locale);
                if (resource != null) {
                    resourceBundles.put(baseName + "_" + locale.toString(), resource);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (resource != null) {
            try {
                String value = resource.getString(key);
                if (value != null) {
                    return value;
                }
            } catch (java.util.MissingResourceException mre) {
                System.out.println(mre.toString());
            }
        }
        return key;
    }
    //</editor-fold>
    
    
}