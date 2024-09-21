package org.example.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BeeShieldAPI {
    public static boolean verifyToken(String username, String token) {
        try {
            HttpURLConnection con = HttpUtil.cookConnection("http://185.9.145.188:25666/tokencheck?usr=" + username + "&token=" + token);
            InputStream is = con.getInputStream();
            Throwable var4 = null;
            try {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                Throwable var6 = null;

                try {
                    BufferedReader br = new BufferedReader(isr);
                    Throwable var8 = null;

                    try {
                        boolean isOk = ((String)br.lines().collect(Collectors.joining(""))).equals("ok");
                    } catch (Throwable var56) {
                        var8 = var56;
                        throw var56;
                    } finally {
                        if (br != null) {
                            if (var8 != null) {
                                try {
                                    br.close();
                                } catch (Throwable var55) {
                                    var8.addSuppressed(var55);
                                }
                            } else {
                                br.close();
                            }
                        }

                    }
                } catch (Throwable var58) {
                    var6 = var58;
                    throw var58;
                } finally {
                    if (isr != null) {
                        if (var6 != null) {
                            try {
                                isr.close();
                            } catch (Throwable var54) {
                                var6.addSuppressed(var54);
                            }
                        } else {
                            isr.close();
                        }
                    }

                }
            } catch (Throwable var60) {
                var4 = var60;
                throw var60;
            } finally {
                if (is != null) {
                    if (var4 != null) {
                        try {
                            is.close();
                        } catch (Throwable var53) {
                            var4.addSuppressed(var53);
                        }
                    } else {
                        is.close();
                    }
                }

            }
        } catch (Exception var62) {
            var62.printStackTrace();
            return false;
        }
        return false;
    }

    public static String login(String username, String password) {
        try {
            HttpURLConnection con = HttpUtil.cookConnection("http://185.9.145.188:25666/login?usr=" + username + "&pwd=" + URLEncoder.encode(password, StandardCharsets.UTF_8.name()));
            InputStream is = con.getInputStream();
            Throwable var4 = null;

            String var10;
            try {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                Throwable var6 = null;

                try {
                    BufferedReader br = new BufferedReader(isr);
                    Throwable var8 = null;

                    try {
                        String resp = (String)br.lines().collect(Collectors.joining(""));
                        if (resp.startsWith("err")) {
                            var10 = null;
                            return var10;
                        }

                        var10 = resp.trim();
                    } catch (Throwable var63) {
                        var8 = var63;
                        throw var63;
                    } finally {
                        if (br != null) {
                            if (var8 != null) {
                                try {
                                    br.close();
                                } catch (Throwable var62) {
                                    var8.addSuppressed(var62);
                                }
                            } else {
                                br.close();
                            }
                        }

                    }
                } catch (Throwable var65) {
                    var6 = var65;
                    throw var65;
                } finally {
                    if (isr != null) {
                        if (var6 != null) {
                            try {
                                isr.close();
                            } catch (Throwable var61) {
                                var6.addSuppressed(var61);
                            }
                        } else {
                            isr.close();
                        }
                    }

                }
            } catch (Throwable var67) {
                var4 = var67;
                throw var67;
            } finally {
                if (is != null) {
                    if (var4 != null) {
                        try {
                            is.close();
                        } catch (Throwable var60) {
                            var4.addSuppressed(var60);
                        }
                    } else {
                        is.close();
                    }
                }

            }

            return var10;
        } catch (Exception var69) {
            var69.printStackTrace();
            return null;
        }
    }
}
