package org.example.frames;

import java.io.File;

class TaskHolder {
    public String libpath;
    public String ver;
    public File folder;

    public TaskHolder(String lp, String v, File f) {
        this.libpath = lp;
        this.ver = v;
        this.folder = f;
    }
}
