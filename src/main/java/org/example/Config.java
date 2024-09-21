package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static Properties cfg = new Properties();
    static final String path = getWorkDir() + "/geckos_bsl.properties";

    public static void load() throws FileNotFoundException, IOException {
        File f = new File(path);
        File parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (!f.exists()) {
            f.createNewFile();
        }

        cfg.load(new FileInputStream(f));
        System.out.println("Java path - " + cfg.getProperty("javabin"));
    }

    public static void save() throws FileNotFoundException, IOException {
        cfg.store(new FileOutputStream(new File(path)), (String)null);
    }

    public static File getJava() {
        return new File(cfg.getProperty("javabin", getJavaExe()));
    }

    private static File ensure(File f) {
        f.mkdirs();
        return f;
    }

    public static File getNatives() {
        return ensure(new File(getWorkDir(), "natives_bsl"));
    }

    public static File getLibraries() {
        return ensure(new File(getWorkDir(), "libraries_bsl"));
    }

    public static File getJarMods() {
        return ensure(new File(getWorkDir(), "jarmods_bsl"));
    }

    public static File getDisabledJarMods() {
        return ensure(new File(new File(getWorkDir(), "jarmods_bsl"), "disabled"));
    }

    public static File getPlayground() {
        return ensure(new File(getWorkDir(), "temp_bsl"));
    }

    public static File getWorkDir() {
        switch(EnumOS.getOS()) {
            case WINDOWS:
                return new File(System.getenv("APPDATA"), ".minecraft");
            case MACOS:
                return new File(System.getProperty("user.home"), "Library/Application Support/.minecraft");
            case OTHER:
                return new File(System.getProperty("user.home"), ".minecraft");
            default:
                return null;
        }
    }

    public static String getUsername() {
        return cfg.getProperty("username");
    }

    public static String getToken() {
        return cfg.getProperty("token");
    }

    public static String getMaxMemory() {
        return cfg.getProperty("memory", "512");
    }

    private static String getJavaExe() {
        String JAVA_HOME = System.getProperty("java.home");
        File BIN = new File(JAVA_HOME, "bin");
        File exe = new File(BIN, "java");
        if (!exe.exists()) {
            exe = new File(BIN, "java.exe");
        }

        if (exe.exists()) {
            return exe.getAbsolutePath();
        } else {
            try {
                String NAKED_JAVA = "java";
                (new ProcessBuilder(new String[]{"java"})).start();
                return "java";
            } catch (IOException var4) {
                return null;
            }
        }
    }
}
