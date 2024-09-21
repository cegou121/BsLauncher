package org.example.reui;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private ImageIcon backgroundImage;

    public BackgroundPanel(ImageIcon backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setBackgroundImage(ImageIcon backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage.getImage(), 0, 0, this);
    }
}
