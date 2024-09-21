package org.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import org.example.frames.Loader;
import org.example.frames.OptionsDialog;
import org.example.frames.Panic;
import org.example.net.BeeShieldAPI;
import org.example.net.HttpUtil;
import org.example.reui.BackgroundPanel;
import org.example.reui.TransparentButton;
import org.example.reui.TransparentLabel;
import org.example.reui.TransparentPanel;
import org.example.reui.TransparentTextField;
import org.example.reui.TransparentTextFieldPassword;

public class Main {
    private static JFrame frame;
    private static JTextField textField_login;
    private static TransparentButton button_logout;
    private static TransparentButton button_login;
    private static JLabel label_pwd;
    private static JLabel label_nick_or_welcome;
    private static JButton button_settings;
    private static JButton button_startmc;
    private static TransparentTextFieldPassword textField_pwd;
    public static final int version = 20;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        final Window w = new Window((Frame)null) {
            public void update(Graphics g) {
                this.paint(g);
            }
        };
        w.setAlwaysOnTop(true);
        JLabel gifLabel = new JLabel();
        w.add(gifLabel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        Rectangle gifSize = gifLabel.getGraphicsConfiguration().getBounds();
        w.setBounds(gifSize.x, gifSize.y + gifSize.height / 6, gifSize.width, gifSize.height);
        w.setLayout(new FlowLayout(1));
        w.setBackground(new Color(0, true));
        w.setVisible(true);
        TimerTask task = new TimerTask() {
            public void run() {
                w.setEnabled(false);
                w.setVisible(false);

                try {
                    Config.load();
                } catch (IOException var2) {
                    throw new RuntimeException(var2);
                }

                EventQueue.invokeLater(() -> {
                    try {
                        Main window = new Main();
                        Main.frame.setVisible(true);
                        Main.frame.pack();
                    } catch (Exception var1) {
                        var1.printStackTrace();
                    }

                });
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        timer.schedule(task, delay);
    }

    public Main() {
        this.initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setLocationRelativeTo((Component)null);
        frame.getContentPane().setPreferredSize(new Dimension(600, 340));
        frame.getContentPane().setMaximumSize(new Dimension(600, 340));
        frame.getContentPane().setMinimumSize(new Dimension(600, 340));
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().setLayout((LayoutManager)null);

        try {
            HttpURLConnection versionConn = HttpUtil.cookConnection("http://138.2.143.151/bslstatic/launcherver");
            String[] data = HttpUtil.readData(versionConn).split("/w/");
            if (Integer.parseInt(data[0]) > 20) {
                JOptionPane.showMessageDialog(frame, "Вышло новое обновление лаунчера! Скачать можно на сайте beeshield.ru.\nНововведения:\n" + data[1], "Gecko's BeeShield Launcher", 1);
                if (!(new File(Config.getWorkDir() + File.separator + ".dev")).exists()) {
                    System.exit(0);
                }
            }
        } catch (IOException var7) {
            JOptionPane.showMessageDialog(frame, "Не удалось проверить обновления лаунчера! Причина:\n" + Panic.convertException(var7), "Gecko's BeeShield Launcher", 0);
        }

        JPanel panel_2 = new BackgroundPanel(new ImageIcon((URL)Objects.requireNonNull(this.getClass().getResource("/assets/background_spawn.png"))));
        panel_2.setBounds(0, 0, 600, 340);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout((LayoutManager)null);
        panel_2.repaint();
        JLabel lblGeckosBeeshieldLauncher = new JLabel("BeeShield Launcher");
        lblGeckosBeeshieldLauncher.setForeground(new Color(196, 196, 196));
        lblGeckosBeeshieldLauncher.setBounds(12, 12, 576, 20);
        panel_2.add(lblGeckosBeeshieldLauncher);
        lblGeckosBeeshieldLauncher.setFont(new Font("Dialog", 0, 20));
        JPanel panel = new TransparentPanel();
        panel.setBounds(12, 44, 244, 284);
        panel_2.add(panel);
        panel.setBorder(new EtchedBorder(1, (Color)null, (Color)null));
        panel.setLayout((LayoutManager)null);
        label_nick_or_welcome = new JLabel("Ник");
        label_nick_or_welcome.setForeground(new Color(196, 196, 196));
        label_nick_or_welcome.setBounds(12, 12, 220, 15);
        panel.add(label_nick_or_welcome);
        textField_login = new TransparentTextField();
        textField_login.setBounds(12, 39, 220, 19);
        panel.add(textField_login);
        textField_login.setColumns(10);
        textField_login.setText(Config.cfg.getProperty("username", ""));
        button_startmc = new TransparentButton("Войти в игру");
        button_startmc.setBounds(12, 247, 220, 25);
        panel.add(button_startmc);
        button_startmc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Config.cfg.setProperty("username", Main.textField_login.getText());
                    Config.save();
                    if (!BeeShieldAPI.verifyToken(Config.getUsername(), Config.getToken())) {
                        JOptionPane.showMessageDialog(Main.frame, "Сессия устарела! Войдите ещё раз.", "Gecko's BeeShield Launcher", 0);
                        return;
                    }

                    File java = Config.getJava();
                    if (!java.exists()) {
                        JOptionPane.showMessageDialog(Main.frame, "Мы не нашли Java! Попробуйте указать корректный путь.", "Gecko's BeeShield Launcher", 0);
                        return;
                    }

                    Loader lf = new Loader(new Runnable() {
                        public void run() {
                            try {
                                System.out.println("Libraries are updated/up-to-date, launching");

                                try {
                                    System.out.println("Repacked successfully, launching");
                                } catch (KeyAlreadyExistsException var10) {
                                    JOptionPane.showMessageDialog(Main.frame, var10.getMessage(), "Gecko's BeeShield Launcher", 0);
                                    return;
                                }

                                String natives = Config.getNatives().getAbsolutePath();
                                StringBuilder classpath = new StringBuilder();
                                File[] var3 = Config.getLibraries().listFiles();
                                int var4 = var3.length;

                                int i;
                                for(i = 0; i < var4; ++i) {
                                    File lib = var3[i];
                                    if (lib.getName().equals("minecraftjr.jar")) {
                                        classpath.append(lib.getAbsolutePath());
                                    } else {
                                        classpath.append(lib.getAbsolutePath());
                                    }

                                    classpath.append(File.pathSeparator);
                                }

                                ProcessBuilder processBuilder = (new ProcessBuilder(new String[0])).directory(Config.getWorkDir());
                                processBuilder.command(Config.getJava().getAbsolutePath(), "-Dpw.geckonerd.bs.launcher.token=" + Config.getToken(), "-Dorg.lwjgl.librarypath=" + natives, "-Dnet.java.games.input.librarypath=" + natives, "-Dfile.encoding=UTF-8", "-Xmx" + Config.getMaxMemory() + "M", "-cp", classpath.toString(), "net.minecraft.client.Minecraft", Config.getUsername(), "--workdir=" + Config.getWorkDir().getAbsolutePath());
                                String command = "";

                                for(i = 0; i < processBuilder.command().size(); ++i) {
                                    command = command + (String)processBuilder.command().get(i) + " ";
                                }

                                System.out.println(command);
                                Process start = processBuilder.inheritIO().start();
                                (new Timer()).schedule(new TimerTask() {
                                    public void run() {
                                        Main.frame.setVisible(false);
                                    }
                                }, 1000L);
                                start.waitFor();
                                Frame[] var15 = Frame.getFrames();
                                int var7 = var15.length;

                                for(int var8 = 0; var8 < var7; ++var8) {
                                    Frame awtFrame = var15[var8];
                                    awtFrame.dispose();
                                }

                                System.out.println("finished");
                                System.exit(0);
                            } catch (Exception var11) {
                                Main.panic(Main.frame, "Не получилось запустить игру!", var11);
                            }

                        }
                    });
                    lf.setVisible(true);
                } catch (Exception var4) {
                    Main.panic(Main.frame, "Не получилось запустить игру!", var4);
                }

            }
        });
        button_settings = new TransparentButton("Настройки");
        button_settings.setBounds(12, 210, 220, 25);
        panel.add(button_settings);
        label_pwd = new JLabel("Пароль");
        label_pwd.setForeground(new Color(196, 196, 196));
        label_pwd.setBounds(12, 70, 54, 15);
        panel.add(label_pwd);
        textField_pwd = new TransparentTextFieldPassword();
        textField_pwd.setText("");
        textField_pwd.setColumns(10);
        textField_pwd.setBounds(12, 97, 220, 19);
        panel.add(textField_pwd);
        button_login = new TransparentButton("Войти в аккаунт");
        button_login.setText("Войти в аккаунт");
        button_login.setBounds(12, 128, 220, 25);
        panel.add(button_login);
        button_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String session = BeeShieldAPI.login(Main.textField_login.getText(), Main.textField_pwd.getText());
                if (session == null) {
                    JOptionPane.showMessageDialog(Main.frame, "Неправильный логин или пароль! (или ошибка?)", "Gecko's BeeShield Launcher", 0);
                } else {
                    Config.cfg.setProperty("token", session);
                    Config.cfg.setProperty("username", Main.textField_login.getText());

                    try {
                        Config.save();
                    } catch (FileNotFoundException var4) {
                        var4.printStackTrace();
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }

                    Main.logged_in_reui();
                }

            }
        });
        button_logout = new TransparentButton("Выйти из аккаунта");
        button_logout.setText("Выйти из аккаунта");
        button_logout.setBounds(12, 39, 220, 25);
        panel.add(button_logout);
        button_logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.logout(true);
            }
        });
        JPanel panel_1 = new TransparentPanel();
        panel_1.setBounds(268, 44, 320, 284);
        panel_2.add(panel_1);
        panel_1.setBorder(new EtchedBorder(1, (Color)null, (Color)null));
        panel_1.setLayout((LayoutManager)null);
        JLabel label_1 = new JLabel("Новости сервера");
        label_1.setForeground(new Color(196, 196, 196));
        label_1.setBounds(12, 12, 302, 15);
        panel_1.add(label_1);
        JLabel textPane = new TransparentLabel();
        textPane.setText("Получение данных...");
        textPane.setBounds(12, 39, 296, 233);
        panel_1.add(textPane);
        button_settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionsDialog f = new OptionsDialog(Main.frame);
                f.setVisible(true);
                f.pack();
            }
        });
        CompletableFuture.supplyAsync(() -> {
            return HttpUtil.getNews();
        }).thenAccept((s) -> {
            textPane.setText(s);
        });
        logout(false);
        login_startup();
    }

    public static void logout(boolean removeToken) {
        button_logout.setVisible(false);
        button_startmc.setVisible(false);
        button_settings.setVisible(false);
        if (removeToken) {
            Config.cfg.remove("token");
            Config.cfg.remove("username");

            try {
                Config.save();
            } catch (FileNotFoundException var2) {
                var2.printStackTrace();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

        textField_login.setVisible(true);
        textField_pwd.setVisible(true);
        label_nick_or_welcome.setText("Ник");
        label_pwd.setVisible(true);
        button_login.setVisible(true);
    }

    public static void logged_in_reui() {
        button_logout.setVisible(true);
        button_startmc.setVisible(true);
        button_settings.setVisible(true);
        textField_login.setVisible(false);
        textField_pwd.setVisible(false);
        label_nick_or_welcome.setText(Config.getUsername() + ", привет!");
        label_pwd.setVisible(false);
        button_login.setVisible(false);
    }

    public static void login_startup() {
        if (Config.getToken() != null) {
            if (BeeShieldAPI.verifyToken(Config.getUsername(), Config.getToken())) {
                logged_in_reui();
                return;
            }

            JOptionPane.showMessageDialog(frame, "Сессия устарела! Войдите ещё раз.", "Gecko's BeeShield Launcher", 0);
            logout(true);
        }

    }

    public static void panic(Component thing, String reason, Exception e) {
        new Panic(thing, reason, e);
        if (thing == null);
    }
}