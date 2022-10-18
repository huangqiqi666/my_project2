package com.hikvision.pbg.sitecodeprj.utils.humanId;

public class IdentifySecurityTools {
    private static volatile int version = 1;

    public IdentifySecurityTools() {
    }

    public static String decode(String value) {
        return version == 1 ? IdentifySecurityUtil.decode(value) : value;
    }

    public static String encode(String value) {
        return version == 1 ? IdentifySecurityUtil.encode(value) : value;
    }

    public static void setVersion(int version) {
        IdentifySecurityTools.version = version;
    }
}