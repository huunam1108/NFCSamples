package com.namnh.nfchelper.model;

public class RDTUrl extends BaseNdefRecord {
    public String url;
    public int prefix;

    public String toString() {
        return "Prefix:" + prefix + "   " + NFCProtocol.getProtocol(prefix) + url;
    }

}