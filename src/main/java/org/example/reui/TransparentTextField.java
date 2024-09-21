package org.example.reui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TransparentTextField extends JTextField {
    public TransparentTextField() {
        this.setOpaque(false);
        this.setBackground(new Color(67, 67, 67, 80));
        this.setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(new Color(105, 105, 105), 2));
        this.initializeEvents();
    }

    private void initializeEvents() {
        this.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyEvent) {
                if (!Character.toString(keyEvent.getKeyChar()).matches("[_a-z0-9A-Z]+")) {
                    keyEvent.consume();
                }

            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        super.paintComponent(g);
    }
}

