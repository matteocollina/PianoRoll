/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pianoroll.controller;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.utils.Utils;

/**
 *
 * @author MacBook
 */
public class TopBar extends JPanel implements ActionListener{

    private final int HEIGHT = 30;
    private final JButton btnSettings;

    public TopBar() {
        super();
        setBackground(Color.RED);
        setPreferredSize(new Dimension(0, HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.TRAILING));
        
        
        btnSettings = new JButton(Utils.getSettingsIcon((int) (HEIGHT * 0.5),(int) (HEIGHT * 0.5)));
        btnSettings.setOpaque(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.setBorderPainted(false);
        btnSettings.addActionListener(this);
        this.add(btnSettings);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        
                    }
                });
                
                /*
                System.out.println("actionPerformed");
                JFrame frame = new JFrame("PROVA");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        System.out.println("windowClosing");
                    }
                });
                frame.setVisible(true);
                */
    }
    
    
   
}
