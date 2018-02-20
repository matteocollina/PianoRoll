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

    public IntTextField(String text) {
        super(text);
        addKeyListener(this);
    }
    public IntTextField(String text, int columns) {
        super(text,columns);
        addKeyListener(this);
    }
    
    @Override
    public String getText(){
        return super.getText().trim().replaceAll("[^\\d.]", "");
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
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
    }
}
