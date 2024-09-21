package org.example.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.example.frames.Panic;

public class HttpUtil {
    public static final String BSL = "http://138.2.143.151/bslstatic/";
    public static final String BSAPI = "http://185.9.145.188:25666/";

    public static String getNews() {
        try {
            HttpURLConnection con = cookConnection("http://138.2.143.151/bslstatic/news");
            InputStream is = con.getInputStream();
            Throwable var2 = null;

            Object var7;
            try {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                Throwable var4 = null;

                try {
                    BufferedReader br = new BufferedReader(isr);
                    Throwable var6 = null;

                    try {
                        var7 = (String)br.lines().collect(Collectors.joining(""));
                    } catch (Throwable var54) {
                        var7 = var54;
                        var6 = var54;
                        throw var54;
                    } finally {
                        if (br != null) {
                            if (var6 != null) {
                                try {
                                    br.close();
                                } catch (Throwable var53) {
                                    var6.addSuppressed(var53);
                                }
                            } else {
                                br.close();
                            }
                        }

                    }
                } catch (Throwable var56) {
                    var4 = var56;
                    throw var56;
                } finally {
                    if (isr != null) {
                        if (var4 != null) {
                            try {
                                isr.close();
                            } catch (Throwable var52) {
                                var4.addSuppressed(var52);
                            }
                        } else {
                            isr.close();
                        }
                    }

                }
            } catch (Throwable var58) {
                var2 = var58;
                throw var58;
            } finally {
                if (is != null) {
                    if (var2 != null) {
                        try {
                            is.close();
                        } catch (Throwable var51) {
                            var2.addSuppressed(var51);
                        }
                    } else {
                        is.close();
                    }
                }

            }

            return (String)var7;
        } catch (Exception var60) {
            return "Не получилось получить новости :(\nПричина:\n" + Panic.convertException(var60);
        }
    }

    public static HttpURLConnection cookConnection(String urlS) throws IOException {
        URL url = new URL(urlS);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
        conn.addRequestProperty("Host", "geckonerd.pw");
        return conn;
    }

    public static String readData(HttpURLConnection conn) throws UnsupportedEncodingException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Throwable var3 = null;

        try {
            String responseLine = null;

            while((responseLine = br.readLine()) != null) {
                sb.append(responseLine);
            }
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (br != null) {
                if (var3 != null) {
                    try {
                        br.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    br.close();
                }
            }

        }

        return sb.toString();
    }
}
