package org.example.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import org.example.Config;
import org.example.Main;
import org.example.reui.TransparentButton;
import org.example.reui.TransparentTextField;

public class OptionsDialog extends JDialog {
    private static final long serialVersionUID = -4290448232276256312L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    public String testers = " Благодарим данных людей за участие в тестировании: .\ud835\ude4b\ud835\ude67\ud835\ude5e\ud835\ude68\ud835\ude62., Свекла(((, Felek, ken_inch, ⓅⓁⒺⓍ, striker, WhiTeik24, Yoshinori, Zendal. Желаем вам приятной игры на сервере!";
    private Color[] rainbowColors;

    private Color getRainbowColor(int index) {
        return this.rainbowColors[index % this.rainbowColors.length];
    }

    public OptionsDialog(JFrame parent) {
        super(parent, true);
        this.rainbowColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        this.getContentPane().setBackground(new Color(31, 31, 31));
        this.setResizable(false);
        this.setBackground(new Color(15, 15, 15));
        this.getContentPane().setSize(new Dimension(424, 435));
        this.getContentPane().setPreferredSize(new Dimension(450, 300));
        this.getContentPane().setMaximumSize(new Dimension(450, 300));
        this.getContentPane().setMinimumSize(new Dimension(450, 300));
        this.pack();
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(this.contentPanel, "Center");
        this.contentPanel.setLayout((LayoutManager)null);
        this.contentPanel.setBackground(new Color(31, 31, 31));
        JLabel label = new JLabel("Путь к папке bin вашей JRE");
        label.setForeground(new Color(196, 196, 196));
        label.setBounds(12, 12, 426, 15);
        this.contentPanel.add(label);
        this.textField = new TransparentTextField();
        this.textField.setBounds(12, 39, 426, 19);
        this.contentPanel.add(this.textField);
        this.textField.setColumns(10);
        label = new JLabel("Количество выделенной памяти игре");
        label.setForeground(new Color(196, 196, 196));
        label.setBounds(12, 70, 426, 15);
        this.contentPanel.add(label);
        JLabel label_1 = new JLabel("мегабайт");
        label_1.setForeground(new Color(196, 196, 196));
        label_1.setBounds(99, 99, 305, 15);
        this.contentPanel.add(label_1);

        try {
            JLabel picLabel = new JLabel(new ImageIcon((URL)Objects.requireNonNull(this.getClass().getResource("/assets/Bee4kaG.gif"))));
            picLabel.setBounds(280, 80, 150, 150);
            this.contentPanel.add(picLabel);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        final JSpinner spinner = new JSpinner();
        spinner.setForeground(new Color(196, 196, 196));
        spinner.setBackground(new Color(31, 31, 31));
        spinner.setBounds(12, 97, 79, 20);
        this.contentPanel.add(spinner);
        JPanel textPane = new JPanel();
        textPane.setBackground(new Color(31, 31, 31));
        textPane.setLayout(new FlowLayout(2));
        this.getContentPane().add(textPane, "South");
        JButton cancelButton = new TransparentButton("Применить");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int memory = (Integer)spinner.getValue();
                if (memory > 1536) {
                    memory = 1536;
                }

                if (memory < 96) {
                    memory = 96;
                }

                File java = new File(OptionsDialog.this.textField.getText());
                if (!java.exists()) {
                    JOptionPane.showMessageDialog(OptionsDialog.this.getContentPane(), "Мы не нашли Java! Попробуйте указать корректный путь.", "CursedRB", 0);
                } else {
                    Config.cfg.put("memory", String.valueOf(memory));
                    Config.cfg.put("javabin", OptionsDialog.this.textField.getText());

                    try {
                        Config.save();
                    } catch (Exception var5) {
                        Main.panic(OptionsDialog.this, "Не удалось сохранить параметры!", var5);
                    }

                    OptionsDialog.this.setVisible(false);
                    OptionsDialog.this.dispose();
                }
            }
        });
        textPane.add(cancelButton);
        this.getRootPane().setDefaultButton(cancelButton);
        cancelButton = new TransparentButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsDialog.this.setVisible(false);
                OptionsDialog.this.dispose();
            }
        });
        textPane.add(cancelButton);
        textPane = new JPanel();
        textPane.setBackground(new Color(31, 31, 31));
        textPane.setLayout(new GridLayout());
        this.getContentPane().add(textPane, "North");
        final JLabel runningText = new JLabel(this.testers);
        runningText.setFont(new Font("Serif", 1, 14));
        Timer timer = new Timer(25, new ActionListener() {
            final float[] colorChange = new float[]{0.1F, 0.0F};

            public void actionPerformed(ActionEvent e) {
                float[] var10000;
                if (this.colorChange[1] == 1.0F) {
                    var10000 = this.colorChange;
                    var10000[0] += 0.005F;
                } else {
                    var10000 = this.colorChange;
                    var10000[0] -= 0.005F;
                }

                if (this.colorChange[0] >= 1.05F) {
                    this.colorChange[0] = 0.0F;
                } else if (this.colorChange[0] <= 0.05F) {
                    this.colorChange[0] = 1.0F;
                }

                Color color2 = Color.getHSBColor(this.colorChange[0], 0.8F, 0.8F);
                runningText.setForeground(color2);
            }
        });
        timer.start();
        Timer timer2 = new Timer(200, (e) -> {
            this.testers = this.testers.substring(1) + this.testers.charAt(0);
            SwingUtilities.invokeLater(() -> {
                runningText.setText(this.testers);
            });
        });
        timer2.start();
        textPane.add(runningText);
        spinner.setValue(Integer.parseInt(Config.cfg.getProperty("memory", "768")));
        this.textField.setText(Config.getJava().getAbsolutePath());
        JLabel lblNewLabel = new JLabel("Версия лаунчера: 20");
        lblNewLabel.setForeground(new Color(196, 196, 196));
        lblNewLabel.setBounds(12, 211, 426, 15);
        this.contentPanel.add(lblNewLabel);
    }
}
