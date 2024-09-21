package org.example;

public enum EnumOS {
    WINDOWS,
    MACOS,
    OTHER;

    public static EnumOS getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac")) {
            return MACOS;
        } else {
            return os.startsWith("win") ? WINDOWS : OTHER;
        }
    }

    public String toString() {
        if (this.equals(WINDOWS)) {
            return "win";
        } else {
            return this.equals(OTHER) ? "linux" : "macos";
        }
    }
}
