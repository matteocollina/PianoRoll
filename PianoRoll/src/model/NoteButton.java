/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.JButton;

/**
 *
 * @author MacBook
 */
public class NoteButton extends JButton{
    private float frequence;
    
    public NoteButton(float frequence){
        super(Float.toString(frequence));
        this.frequence = frequence;
    }
    public float getFrequence(){
        return this.frequence;
    }
}
