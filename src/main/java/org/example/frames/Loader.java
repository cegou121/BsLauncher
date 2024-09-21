package org.example.frames;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import org.example.Config;
import org.example.EnumOS;
import org.example.net.HttpUtil;

public class Loader extends JFrame {
    private JPanel contentPane;
    private JLabel label;
    private JLabel lblNull;
    private JProgressBar progressBar;
    private List<LoaderTask> tasks;
    private JProgressBar progressBar_1;

    public Loader(final Runnable r) {
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout((LayoutManager)null);
        this.label = new JLabel("Загрузка игры... ");
        this.label.setBounds(12, 28, 426, 15);
        this.contentPane.add(this.label);
        this.progressBar = new JProgressBar();
        this.progressBar.setBounds(12, 55, 426, 14);
        this.contentPane.add(this.progressBar);
        this.lblNull = new JLabel("Задание: (нет)");
        this.lblNull.setBounds(12, 81, 426, 15);
        this.contentPane.add(this.lblNull);
        this.progressBar_1 = new JProgressBar();
        this.progressBar_1.setBounds(12, 108, 426, 14);
        this.contentPane.add(this.progressBar_1);
        (new Thread(new Runnable() {
            public void run() {
                try {
                    Loader.this.tasks = Loader.this.getTasks();
                    int ctask = 0;
                    Iterator var2 = Loader.this.tasks.iterator();

                    while(var2.hasNext()) {
                        LoaderTask task = (LoaderTask)var2.next();
                        ++ctask;
                        Loader.this.setTaskMeter(ctask, Loader.this.tasks.size());
                        Loader.this.setTask("Сверяю " + task.f.getName() + "...");
                        System.out.println("looking at " + task.f.getName());
                        if (task.needsUpdate()) {
                            System.out.println("update required!");
                            Loader.this.setTask("Скачиваю " + task.f.getName() + "...");
                            task.download(Loader.this);
                        }
                    }

                    Loader.this.setTaskMeter(1, 1);
                    Loader.this.label.setText("Готово!");
                    Loader.this.setTask("(нет)");
                } catch (Exception var5) {
                    JOptionPane.showMessageDialog(Loader.this.contentPane, "Не получилось обновить файлы! Игра будет запущена без обновления, что может привести к багам и ошибкам.\nЕсли известно, что существует критический баг, игру лучше не запускать.\nПричина:\n" + Panic.convertException(var5), "CursedRB", 0);
                }

                try {
                    Timer timer = new Timer(2500, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Loader.this.setVisible(false);
                            r.run();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                } catch (Exception var4) {
                    JOptionPane.showMessageDialog(Loader.this.contentPane, "Произошла ошибка при запуске игры! Причина:\n" + Panic.convertException(var4), "CursedRB", 0);
                    System.exit(0);
                }

            }
        })).start();
    }

    public void setTaskMeter(int cur, int max) {
        this.progressBar.setMinimum(0);
        this.progressBar.setMaximum(max);
        this.progressBar.setValue(cur);
        this.label.setText("Загрузка игры... " + cur + "/" + max + " заданий выполнено");
    }

    public void setTaskMeter2(int cur, int max) {
        this.progressBar_1.setMinimum(0);
        this.progressBar_1.setMaximum(max);
        this.progressBar_1.setValue(cur);
    }

    public void setTask(String task) {
        this.lblNull.setText("Задание: " + task);
    }

    private List<LoaderTask> getTasks() throws Exception {
        List<LoaderTask> tasks = new ArrayList();
        Iterator var2 = this.getLibraries().iterator();

        TaskHolder t;
        while(var2.hasNext()) {
            t = (TaskHolder)var2.next();
            System.out.println("Server sent lib " + t.libpath + " ver " + t.ver);
            tasks.add(new LoaderTask(new File(Config.getLibraries(), t.libpath), "http://138.2.143.151/bslstatic/libraries/" + t.libpath, t.ver, t.folder));
        }

        var2 = this.getNatives().iterator();

        while(var2.hasNext()) {
            t = (TaskHolder)var2.next();
            System.out.println("Server sent native " + t.libpath + " ver " + t.ver);
            tasks.add(new LoaderTask(new File(Config.getNatives(), t.libpath), "http://138.2.143.151/bslstatic/natives/" + EnumOS.getOS().toString() + "/" + t.libpath, t.ver, t.folder));
        }

        return tasks;
    }

    private List<TaskHolder> getLibraries() throws Exception {
        List<TaskHolder> t = new ArrayList();
        HttpURLConnection con = HttpUtil.cookConnection("http://138.2.143.151/bslstatic/libraries/get_test");
        String jdata = HttpUtil.readData(con);
        String[] var4 = jdata.split(";", 0);
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String libdata = var4[var6];
            if (!jdata.contains("/")) {
                break;
            }

            String[] libdata2 = libdata.split("/", 0);
            t.add(new TaskHolder(libdata2[0], libdata2[1], Config.getLibraries()));
        }

        return t;
    }

    private List<TaskHolder> getNatives() throws Exception {
        List<TaskHolder> t = new ArrayList();
        HttpURLConnection con = HttpUtil.cookConnection("http://138.2.143.151/bslstatic/natives/" + EnumOS.getOS().toString() + "/get");
        String jdata = HttpUtil.readData(con);
        String[] var4 = jdata.split(";", 0);
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String libdata = var4[var6];
            if (!jdata.contains("/")) {
                break;
            }

            String[] libdata2 = libdata.split("/", 0);
            t.add(new TaskHolder(libdata2[0], libdata2[1], Config.getNatives()));
        }

        return t;
    }
}
