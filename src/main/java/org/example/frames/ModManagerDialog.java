package org.example.frames;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.example.Config;
import org.example.reui.TransparentButton;

public class ModManagerDialog extends JDialog {
    public ModManagerDialog(JFrame parent) {
        super(parent, true);
        this.getContentPane().setBackground(new Color(31, 31, 31));
        this.setResizable(false);
        this.getContentPane().setSize(new Dimension(424, 435));
        this.getContentPane().setPreferredSize(new Dimension(424, 435));
        this.getContentPane().setMaximumSize(new Dimension(424, 435));
        this.getContentPane().setMinimumSize(new Dimension(424, 435));
        this.pack();
        this.setBackground(new Color(31, 31, 31));
        this.repaint();
        this.getContentPane().setLayout((LayoutManager)null);
        final JButton moveLeft = new TransparentButton("<<");
        moveLeft.setEnabled(false);
        moveLeft.setBounds(294, 323, 117, 25);
        this.getContentPane().add(moveLeft);
        final JButton del = new TransparentButton("Удалить");
        del.setEnabled(false);
        del.setBounds(141, 323, 141, 25);
        this.getContentPane().add(del);
        final DefaultListModel<String> listEnabledModel = new DefaultListModel();
        final JList listEnabled = new JList(listEnabledModel);
        listEnabled.setForeground(new Color(196, 196, 196));
        listEnabled.setBackground(new Color(63, 63, 63));
        listEnabled.setSelectionMode(0);
        listEnabled.setBounds(12, 72, 198, 239);
        this.getContentPane().add(listEnabled);
        final DefaultListModel<String> listDisabledModel = new DefaultListModel();
        JButton btnD = new TransparentButton("Скачать новые моды");
        btnD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://geckonerd.pw/bsl/browse.html"));
                } catch (URISyntaxException | IOException var3) {
                    var3.printStackTrace();
                    JOptionPane.showMessageDialog(ModManagerDialog.this.getContentPane(), "Не удалось открыть веб-сайт.", "Gecko's BeeShield Launcher", 0);
                }

            }
        });
        btnD.setBounds(12, 360, 399, 25);
        this.getContentPane().add(btnD);
        JButton btnD_1 = new TransparentButton("Открыть папку с модами");
        btnD_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(Config.getJarMods());
                } catch (IOException var3) {
                    var3.printStackTrace();
                    JOptionPane.showMessageDialog(ModManagerDialog.this.getContentPane(), "Не удалось открыть папку.", "Gecko's BeeShield Launcher", 0);
                }

            }
        });
        btnD_1.setBounds(12, 397, 399, 25);
        this.getContentPane().add(btnD_1);
        JSeparator separator = new JSeparator();
        separator.setBounds(12, 39, 399, 21);
        this.getContentPane().add(separator);
        JLabel label_1 = new JLabel("Включенные моды");
        label_1.setForeground(new Color(196, 196, 196));
        label_1.setBounds(12, 45, 152, 15);
        this.getContentPane().add(label_1);
        JLabel label_2 = new JLabel("Выключенные моды");
        label_2.setForeground(new Color(196, 196, 196));
        label_2.setBounds(266, 45, 146, 15);
        this.getContentPane().add(label_2);
        final JButton moveRight = new TransparentButton(">>");
        moveRight.setBounds(12, 323, 117, 25);
        this.getContentPane().add(moveRight);
        moveRight.setEnabled(false);
        moveRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listEnabled.isSelectionEmpty()) {
                    String filename = (String)listEnabledModel.get(listEnabled.getSelectedIndex());
                    (new File(Config.getJarMods(), filename)).renameTo(new File(Config.getDisabledJarMods(), filename));
                    listDisabledModel.addElement(filename);
                    listEnabledModel.remove(listEnabled.getSelectedIndex());
                    if (listEnabledModel.size() == 0) {
                        moveRight.setEnabled(false);
                    }

                }
            }
        });
        final JList listDisabled = new JList(listDisabledModel);
        listDisabled.setForeground(new Color(196, 196, 196));
        listDisabled.setBackground(new Color(63, 63, 63));
        listDisabled.setBounds(214, 72, 198, 239);
        this.getContentPane().add(listDisabled);
        listDisabled.setSelectionMode(0);
        listDisabled.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listEnabled.clearSelection();
                listDisabled.requestFocusInWindow();
                moveRight.setEnabled(false);
                moveLeft.setEnabled(true);
                del.setEnabled(true);
            }
        });
        JLabel label = new JLabel("Менеджер модов");
        label.setForeground(new Color(196, 196, 196));
        label.setBounds(12, 12, 399, 15);
        this.getContentPane().add(label);
        listEnabled.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listDisabled.clearSelection();
                listEnabled.requestFocusInWindow();
                moveRight.setEnabled(true);
                moveLeft.setEnabled(false);
                del.setEnabled(true);
            }
        });
        moveLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listDisabled.isSelectionEmpty()) {
                    String filename = (String)listDisabledModel.get(listDisabled.getSelectedIndex());
                    (new File(Config.getDisabledJarMods(), filename)).renameTo(new File(Config.getJarMods(), filename));
                    listEnabledModel.addElement(filename);
                    listDisabledModel.remove(listDisabled.getSelectedIndex());
                    if (listDisabledModel.size() == 0) {
                        moveLeft.setEnabled(false);
                    }

                }
            }
        });
        del.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = null;
                boolean fromEnabled = false;
                if (!listDisabled.isSelectionEmpty()) {
                    filename = (String)listDisabledModel.get(listDisabled.getSelectedIndex());
                }

                if (!listEnabled.isSelectionEmpty()) {
                    filename = (String)listEnabledModel.get(listEnabled.getSelectedIndex());
                    fromEnabled = true;
                }

                if (filename != null) {
                    int result = JOptionPane.showConfirmDialog(ModManagerDialog.this.getContentPane(), "Вы собираетесь удалить мод '" + filename + "'. Продолжить?", "Удалить мод", 0);
                    if (result == 0) {
                        if (fromEnabled) {
                            (new File(Config.getJarMods(), filename)).delete();
                            listEnabledModel.remove(listEnabled.getSelectedIndex());
                        } else {
                            (new File(Config.getDisabledJarMods(), filename)).delete();
                            listDisabledModel.remove(listDisabled.getSelectedIndex());
                        }
                    }

                }
            }
        });
        File[] mods = Config.getJarMods().listFiles();
        File[] disabled = Config.getDisabledJarMods().listFiles();
        File[] var17 = mods;
        int var18 = mods.length;

        int var19;
        File mod;
        for(var19 = 0; var19 < var18; ++var19) {
            mod = var17[var19];
            if (!mod.isDirectory()) {
                listEnabledModel.addElement(mod.getName());
            }
        }

        var17 = disabled;
        var18 = disabled.length;

        for(var19 = 0; var19 < var18; ++var19) {
            mod = var17[var19];
            if (!mod.isDirectory()) {
                listDisabledModel.addElement(mod.getName());
            }
        }

    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            File[] var2 = files;
            int var3 = files.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                File f = var2[var4];
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }

        folder.delete();
    }

    public static void repackMinecraft() throws IOException {
        System.out.println("Repacking");
        File repacked = new File(Config.getPlayground(), "repacked_mc.jar");
        File oldfile = new File(Config.getLibraries(), "minecraft.jar");
        if (repacked.exists()) {
            repacked.delete();
        }

        deleteFolder(new File(Config.getPlayground(), "unzipped"));
        System.out.println("Checking for any conflicts");
        File[] mods = Config.getJarMods().listFiles();
        mods = (File[])Arrays.stream(mods).filter((x) -> {
            return x.getName().endsWith(".jar") || x.getName().endsWith(".zip");
        }).toArray((x$0) -> {
            return new File[x$0];
        });
        HashMap<String, String> knownPaths = new HashMap();
        File[] var4 = mods;
        int var5 = mods.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            File mod = var4[var6];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(mod));

            for(ZipEntry zipEntry = zis.getNextEntry(); zipEntry != null; zipEntry = zis.getNextEntry()) {
                if (!zipEntry.isDirectory()) {
                    if (knownPaths.containsKey(zipEntry.getName()) && !zipEntry.getName().equalsIgnoreCase("readme.txt")) {
                        throw new KeyAlreadyExistsException("Конфликт модов! Мод " + mod.getName() + " конфликтует с модом " + (String)knownPaths.get(zipEntry.getName()) + " за файл " + zipEntry.getName() + "! Проблему можно решить убрав один из модов.");
                    }

                    knownPaths.put(zipEntry.getName(), mod.getName());
                }
            }

            zis.closeEntry();
            zis.close();
        }

        System.out.println("Unzipping mods (" + mods.length + " mods)");
        File unzipped = new File(Config.getPlayground(), "unzipped");
        unzipped.mkdir();
        File[] var11 = mods;
        var6 = mods.length;

        for(int var12 = 0; var12 < var6; ++var12) {
            File mod = var11[var12];
            unzipMod(mod, unzipped);
        }

        System.out.println("Zipping stuff back into Minecraft");
        Files.copy(oldfile.toPath(), repacked.toPath());
        addFilesToExistingZip(repacked, unzipped.listFiles(), unzipped);
    }

    private static void unzipMod(File file, File extractFolder) throws ZipException, IOException {
        int BUFFER = 16384;
        ZipFile zip = new ZipFile(file);
        Enumeration zipFileEntries = zip.entries();

        while(true) {
            ZipEntry entry;
            File destFile;
            do {
                do {
                    if (!zipFileEntries.hasMoreElements()) {
                        return;
                    }

                    entry = (ZipEntry)zipFileEntries.nextElement();
                    String currentEntry = entry.getName();
                    destFile = new File(extractFolder, currentEntry);
                } while(destFile.exists());

                File destinationParent = destFile.getParentFile();
                destinationParent.mkdirs();
            } while(entry.isDirectory());

            BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
            byte[] data = new byte[BUFFER];
            FileOutputStream fos = new FileOutputStream(destFile);
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

            int currentByte;
            while((currentByte = is.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, currentByte);
            }

            dest.flush();
            dest.close();
            is.close();
        }
    }

    private static File[] recursize(File[] files) {
        ArrayList<File> fls = new ArrayList();
        File[] var2 = files;
        int var3 = files.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            File f = var2[var4];
            if (!f.isDirectory()) {
                fls.add(f);
            } else {
                fls.add(f);
                File[] var6 = recursize(f.listFiles());
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    File f2 = var6[var8];
                    fls.add(f2);
                }
            }
        }

        return (File[])fls.toArray(new File[fls.size()]);
    }

    public static void addFilesToExistingZip(File zipFile, File[] files, File filesRootDir) throws IOException {
        files = recursize(files);
        File tempFile = File.createTempFile(zipFile.getName(), (String)null);
        tempFile.delete();
        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException("could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
        } else {
            byte[] buf = new byte[1024];
            ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            ZipEntry entry = zin.getNextEntry();

            while(true) {
                while(entry != null) {
                    String name = entry.getName();
                    boolean notInFiles = true;
                    if (name.startsWith("META-INF/")) {
                        entry = zin.getNextEntry();
                    } else {
                        File[] var11 = files;
                        int var12 = files.length;

                        for(int var13 = 0; var13 < var12; ++var13) {
                            File f = var11[var13];
                            if (constructPath(f, filesRootDir).equals(name)) {
                                notInFiles = false;
                                break;
                            }
                        }

                        if (notInFiles) {
                            out.putNextEntry(new ZipEntry(name));

                            int len;
                            while((len = zin.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                        }

                        entry = zin.getNextEntry();
                    }
                }

                zin.close();
                HashSet<String> duplicateEntries = new HashSet();
                _addFilesToStream(files, out, duplicateEntries, "");
                out.close();
                tempFile.delete();
                return;
            }
        }
    }

    private static String constructPath(File f, File root) {
        String path = root.toPath().relativize(f.toPath()).toString();
        if (f.isDirectory() && !path.endsWith("/")) {
            path = path + "/";
        }

        return path;
    }

    private static void _addFilesToStream(File[] files, ZipOutputStream out, HashSet<String> duplicateEntries, String prefix) throws IOException {
        byte[] buf = new byte[16384];

        for(int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                if (!duplicateEntries.contains(prefix + files[i].getName() + "/")) {
                    out.putNextEntry(new ZipEntry(prefix + files[i].getName() + "/"));
                    out.closeEntry();
                    duplicateEntries.add(prefix + files[i].getName() + "/");
                }

                _addFilesToStream(files[i].listFiles(), out, duplicateEntries, prefix + files[i].getName() + "/");
            } else if (!duplicateEntries.contains(prefix + files[i].getName())) {
                out.putNextEntry(new ZipEntry(prefix + files[i].getName()));
                duplicateEntries.add(prefix + files[i].getName());
                FileInputStream in = new FileInputStream(files[i]);

                int len;
                while((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                out.closeEntry();
                in.close();
            }
        }

    }
}
