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
    private  ImageIcon getIconWithNameAndSize(String name,int width, int height){
        return new ImageIcon(getImageWithName(name).getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH ));
    }
    public static ImageIcon getSettingsIcon(int width, int height){
        return new Utils().getIconWithNameAndSize(ICO_SETTINGS,width,height);
    }
    
}
