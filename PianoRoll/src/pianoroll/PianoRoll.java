/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll;
import pianoroll.controller.PianoRollContent;

/**
 *
 * @author MacBook
 */
public class PianoRoll {

    public static void main(String[] args) {
        initComponents();
    }

    private static void initComponents() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PianoRollContent pianoRollContent = new PianoRollContent();
            }
        });
    }

}

