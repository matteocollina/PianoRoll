/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.utils.KeyLocate;
import model.utils.Utils;

/**
 *
 * @author MacBook
 */
public class Oscillator {
    public enum OTYPE {
        SINE,WAVE,SAW,TRIANGLE,SQUARE
    }
    public static String toString(OTYPE value){
        switch (value){
            case SINE:{
                return Utils.getAppString(KeyLocate.SINE);
            }
            case WAVE:{
                return Utils.getAppString(KeyLocate.WAVE);
            }
            case SAW:{
                return Utils.getAppString(KeyLocate.SAW);
            }
            case TRIANGLE:{
                return Utils.getAppString(KeyLocate.TRIANGLE);
            }
            case SQUARE:{
                return Utils.getAppString(KeyLocate.SQUARE);
            }
        }
        return "";
    }
    public static OTYPE fromString(String value){
        switch (value.toUpperCase()){
            case "SINE":{
                return OTYPE.SINE;
            }
            case "WAVE":{
                return OTYPE.WAVE;
            }
            case "SAW":{
                return OTYPE.SAW;
            }
            case "TRIANGLE":{
                return OTYPE.TRIANGLE;
            }
            case "SQUARE":{
                return OTYPE.SQUARE;
            }
            default:{
                return OTYPE.SINE;
            }
        }
    }
}
