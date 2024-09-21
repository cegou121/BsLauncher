package org.example.reui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class TransparentPanel extends JPanel {
    public TransparentPanel() {
        this.setBackground(new Color(15, 15, 15, 110));
        this.setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        super.paintComponent(g);
    }
}
