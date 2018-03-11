/*
 * TextField che accetta solo numeri
 */
package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.management.monitor.StringMonitor;
import javax.swing.JTextField;

/**
 *
 * @author MacBook
 */
public class IntTextField extends JTextField implements KeyListener{
    private int limit;
    private int defvalue;
    
    public IntTextField(String text,int limit,int defvalue) {
        super(text, limit);
        this.limit = limit;
        this.defvalue = defvalue;
        addKeyListener(this);
    }
    public IntTextField(String text, int columns,int limit,int defvalue) {
        super(text,columns);
        this.limit = limit;
        this.defvalue = defvalue;
        addKeyListener(this);
    }
    
    @Override
    public String getText(){
        return super.getText().trim().replaceAll("[^\\d.]", "");
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if (this.getText().length() >= this.limit ) // limit to 3 characters
                e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            this.setText(this.getText());
        } catch (Exception exception) {
            System.out.println(exception);
        }
        //If field is empty, force to fill with default value
        if (this.getText().length()==0) {
            this.setText(Integer.toString(this.defvalue));
        }
    }
}
