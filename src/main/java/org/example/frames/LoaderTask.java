package org.example.frames;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.concurrent.TimeUnit;
import org.example.net.HttpUtil;

public class LoaderTask {
    File f;
    File verFile;
    File targetDir;
    String url;
    String ver;

    public LoaderTask(File targetPath, String serverURL, String version, File targetDir) {
        this.f = targetPath;
        this.verFile = new File(this.f.getAbsolutePath() + ".version");
        this.url = serverURL;
        this.ver = version;
        this.targetDir = targetDir;
    }

    private static String encryptSHA(String password) {
        String sha1 = "";

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return sha1;
    }

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = Files.newInputStream(Paths.get(filename));
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");

        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while(numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for(int i = 0; i < b.length; ++i) {
            result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
        }

        return result;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public boolean needsUpdate() throws Exception {
        if (!this.f.exists()) {
            return true;
        } else if (!this.verFile.exists()) {
            return true;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(this.verFile));
            String line = reader.readLine();
            int verLocal = Integer.parseInt(line.split("&")[0]);
            reader.close();
            if (verLocal < Integer.parseInt(this.ver.split("&")[0])) {
                return true;
            } else {
                if (this.ver.split("&").length > 1) {
                    String hash = this.ver.split("&")[1];
                    System.out.println(getMD5Checksum(this.f.getAbsolutePath()).toLowerCase() + " " + this.ver.split("&")[1]);
                    if (!getMD5Checksum(this.f.getAbsolutePath()).toLowerCase().equals(hash)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public void download(Loader frame) throws Exception {
        if (!this.targetDir.exists()) {
            this.targetDir.mkdirs();
        }

        if (this.f.exists()) {
            this.f.delete();
        }

        this.f.getParentFile().mkdirs();
        URLConnection urlConnection = HttpUtil.cookConnection(this.url);
        urlConnection.setConnectTimeout((int)TimeUnit.SECONDS.toMillis(3L));
        int contentLengthLong = urlConnection.getContentLength();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Throwable var5 = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.f);
            Throwable var7 = null;

            try {
                byte[] data = new byte[4096];
                int totalWrited = 0;

                int readed;
                while((readed = in.read(data)) != -1) {
                    fileOutputStream.write(data, 0, readed);
                    totalWrited += readed;
                    frame.setTaskMeter2(totalWrited, contentLengthLong);
                }
            } catch (Throwable var32) {
                var7 = var32;
                throw var32;
            } finally {
                if (fileOutputStream != null) {
                    if (var7 != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable var31) {
                            var7.addSuppressed(var31);
                        }
                    } else {
                        fileOutputStream.close();
                    }
                }

            }
        } catch (Throwable var34) {
            var5 = var34;
            throw var34;
        } finally {
            if (in != null) {
                if (var5 != null) {
                    try {
                        in.close();
                    } catch (Throwable var30) {
                        var5.addSuppressed(var30);
                    }
                } else {
                    in.close();
                }
            }

        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(this.verFile));
        writer.write(String.valueOf(this.ver));
        writer.close();
    }
}
