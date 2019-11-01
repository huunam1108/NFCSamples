package com.namnh.nfchelper.model;

import android.util.SparseArray;

public class NFCProtocol {

    private static SparseArray<String> uriMap = new SparseArray<>();

    static {
        uriMap.put(0x01, "http://www.");
        uriMap.put(0x02, "https://www.");
        uriMap.put(0x03, "http://");
        uriMap.put(0x04, "https://www.");
        uriMap.put(0x05, "tel:");
        uriMap.put(0x06, "mailto:");
        uriMap.put(0x07, "ftp://anonymous:anonymous@");
        uriMap.put(0x08, "ftp://ftp.");
        uriMap.put(0x09, "ftps://");
    }

    public static String getProtocol(int code) {
        return uriMap.get(code);
    }
}
