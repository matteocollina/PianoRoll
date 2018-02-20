/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author MacBook
 */
public class Utils {
    private static final String ICO_SETTINGS = "ic_settings_2x.png";
    private static final String ICO_STOP = "ic_stop_2x.png";
    private static final String ICO_PAUSE = "ic_pause_2x.png";
    private static final String ICO_PLAY = "ic_play_2x.png";
    
    /**
     * 
     * @param name name of images (include extension)
     * @return Image
     */
    private Image getImageWithName(String name){
        try {
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
    
    /* ICONS */
    public static ImageIcon getSettingsIcon(int width, int height,float resize){
        return new Utils().getIconWithNameAndSize(ICO_SETTINGS,width,height,resize);
    }
    public static ImageIcon getStopIcon(int width, int height,float resize){
        return new Utils().getIconWithNameAndSize(ICO_STOP,width,height,resize);
    }
    public static ImageIcon getPauseIcon(int width, int height,float resize){
        return new Utils().getIconWithNameAndSize(ICO_PAUSE,width,height,resize);
    }
    public static ImageIcon getPlayIcon(int width, int height,float resize){
        return new Utils().getIconWithNameAndSize(ICO_PLAY,width,height,resize);
    }
    
    
    
}
