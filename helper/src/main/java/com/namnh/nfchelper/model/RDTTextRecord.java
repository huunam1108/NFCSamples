package com.namnh.nfchelper.model;

import android.util.Log;

import androidx.annotation.NonNull;

public class RDTTextRecord extends BaseNdefRecord {

    public String language;
    public String encoding;

    public RDTTextRecord() {
        this.TNF = 0x01;
    }

    @NonNull
    public String toString() {
        Log.d("Nfc", "Here to string");

        return super.toString() + " Encoding:" + encoding +
                " Content: " + payload;
    }

    public static RDTTextRecord createRecord(byte[] payload) {
        Log.d("Nfc", "Text record");
        String txtContent = "";
        // Handle text

        RDTTextRecord record = new RDTTextRecord();

        byte status = payload[0];
        int enc = status & 0x80; // Bit mask 7th bit 1
        String encString = null;
        if (enc == 0)
            encString = "UTF-8";
        else
            encString = "UTF-16";

        record.encoding = encString;
        int ianaLength = status & 0x3F; // Bit mask bit 5..0
        Log.d("Nfc", "IANA Len [" + ianaLength + "]");

        try {
            String content = new String(payload, ianaLength + 1, payload.length - 1 - ianaLength, encString);
            record.payload = content;
            // txtContent = "Enc:" + encString + " Content:" + content;

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return record;
    }
}