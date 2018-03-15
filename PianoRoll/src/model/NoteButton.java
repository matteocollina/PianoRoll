/*
 * Il NoteButton è il bottone che rappresenta un tasto del piano
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
        super(String.format("%d",(long)frequence));
        this.frequence = frequence;
    }
    public float getFrequence(){
        return this.frequence;
    }
}
