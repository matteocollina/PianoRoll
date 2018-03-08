/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.singleton.ScoreSingleton;

/**
 *
 * @author MacBook
 */
public class NoteBlock extends JPanel {
    private Color COLOR_SELECTED = Color.RED;
    private Color COLOR_UNSELECTED = Color.WHITE;
    private int OFFSET_H = 8;
    public static int WIDTH_MIN_DURATE = 30;
    
    private boolean selected;
    private int of_phrase;
    private int of_part;
    private int index;

    public NoteBlock(int index,int of_phrase,int of_part,int heightLine) {
        this.selected = false;
        this.index = index;
        this.of_phrase = of_phrase;
        this.of_part = of_part;
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        setPreferredSize(new Dimension(WIDTH_MIN_DURATE, (int) (heightLine - OFFSET_H)));
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NoteBlock tmpMisure = (NoteBlock) e.getSource();
                if (isSelected()){
                    tmpMisure.setUnselected();
                }else{
                    tmpMisure.setSelected();
                }
                tmpMisure.revalidate();
                tmpMisure.repaint();
            }
        });
    }
    public int getPart(){
        return this.of_part;
    }
    public int getPhrase(){
        return this.of_phrase;
    }
    public int getIndex(){
        return this.index;
    }
    
    private void setSelected(){
        this.selected = true;
        this.setBackground(COLOR_SELECTED);
        ScoreSingleton.getInstance().setNoteInPartAndPhraseAthIndex(this,true);
    }
    private void setUnselected(){
        this.selected = false;
        this.setBackground(COLOR_UNSELECTED);
        ScoreSingleton.getInstance().setNoteInPartAndPhraseAthIndex(this,false);
    }
    private boolean isSelected(){
        return this.getBackground() == COLOR_SELECTED;
    }

}
