/*
 * Panel per racchiudere un componente (button,texteditor) e una label sottostante
 */
package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author MacBook
 */
public class SettingGroupPanel extends JPanel{
    
    public SettingGroupPanel(int width, int height, Component comp, String text) {
        super(new BorderLayout());
        setPreferredSize(new Dimension(width,height));
               
        comp.setFont(new Font(null, 0, 13));
        if (comp.getClass() == IntTextField.class) {
            IntTextField textField = (IntTextField) comp;
            textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            textField.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JLabel label = new JLabel(text);
        label.setFont(new Font(null, 0, 10));
        label.setOpaque(false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(comp,BorderLayout.CENTER);
        add(label,BorderLayout.PAGE_END);        
    }
}
