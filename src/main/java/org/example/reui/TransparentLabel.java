package org.example.reui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class TransparentLabel extends JLabel {
    public TransparentLabel() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 100));
        this.setForeground(new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getBlue(), Color.LIGHT_GRAY.getGreen(), 240));
        this.setVerticalAlignment(1);
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(53, 53, 53, 243)));
    }

    public TransparentLabel(String label) {
        this();
        this.setText(label);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        super.paintComponent(g);
    }

    public void setText(String text) {
        super.setText("<html>" + text + "</html>");
    }
}
