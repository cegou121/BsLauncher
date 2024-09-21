package org.example.reui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class TransparentButton extends JButton {
    public static final Color COLOR_DISABLED = new Color(50, 50, 50, 180);

    public TransparentButton(String label) {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(156, 156, 156, 149), 2));
        this.setCursor(new Cursor(12));
        this.setBackground(new Color(100, 100, 100, 180));
        this.setForeground(new Color(232, 232, 232, 140));
        this.setText(label);
        this.setHorizontalAlignment(0);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.initializeEvents();
    }

    private void initializeEvents() {
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                TransparentButton.this.setForeground(new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getBlue(), Color.LIGHT_GRAY.getGreen(), 240));
            }

            public void mouseExited(MouseEvent e) {
                TransparentButton.this.setForeground(new Color(232, 232, 232, 140));
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.isEnabled()) {
            g.setColor(this.getBackground());
        } else {
            g.setColor(COLOR_DISABLED);
        }

        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        super.paintComponent(g);
    }
}
