package org.example.frames;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Panic extends JFrame {
    private static final long serialVersionUID = 6951249155992072654L;
    private JPanel contentPane;

    public Panic(Component thing, String reason, Exception e) {
        if (thing != null) {
            thing.setVisible(false);
        }

        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 640, 480);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout((LayoutManager)null);
        JLabel label = new JLabel("Упс! Лаунчер крашнулся!");
        label.setBounds(12, 25, 616, 15);
        this.contentPane.add(label);
        final JTextPane textPane = new JTextPane();
        textPane.setText("*** BSL crashed! ***\n" + reason + "\n" + convertException(e) + "\n*** no stats here");
        textPane.setBounds(12, 46, 616, 393);
        this.contentPane.add(textPane);
        JButton button = new JButton("Закрыть лаунчер");
        button.setBounds(12, 443, 159, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.contentPane.add(button);
        JButton button_1 = new JButton("Скопировать");
        button_1.setBounds(183, 443, 132, 25);
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(textPane.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, (ClipboardOwner)null);
            }
        });
        this.contentPane.add(button_1);
        this.setVisible(true);
    }

    public static String convertException(Exception e) {
        if (e == null) {
            return "(no stack trace)";
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
    }
}
