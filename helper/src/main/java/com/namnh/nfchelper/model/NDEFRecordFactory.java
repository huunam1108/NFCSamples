package com.namnh.nfchelper.model;

import android.nfc.NdefRecord;
import android.util.Log;

import java.util.Arrays;

public class NDEFRecordFactory {

    public static BaseNdefRecord createRecord(NdefRecord record) {
        short tnf = record.getTnf();
        byte[] cont = record.getPayload();

        Log.d("Nfc", "Dump record ["+dumpPayload(record.getPayload())+"]");
        if (tnf == NdefRecord.TNF_WELL_KNOWN) {
            Log.d("Nfc", "Well Known");
            // Check if it is TEXT
            if (Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                return RDTTextRecord.createRecord(record.getPayload());
            }
            else if (Arrays.equals(record.getType(), NdefRecord.RTD_URI)) {
                Log.d("Nfc", "RTD_URI");
                //Log.d("Nfc", "Content [" + new String(data.content)+ "]");
            }
            else if (Arrays.equals(record.getType(), NdefRecord.RTD_SMART_POSTER)) {
                Log.d("Nfc", "Smart poster");


                //Log.d("Nfc", "Smart poster ["+new String(data.content)+"]");
                return RDTSpRecord.createRecord(record.getPayload());
            }
            // Maybe handle more
        }
        else if (tnf == NdefRecord.TNF_EXTERNAL_TYPE) {
            return NDEFExternalType.createRecord(record.getPayload());
        }

        return null;
    }

    private static String dumpPayload(byte[] payload) {
        StringBuilder pCont = new StringBuilder();
        for (byte b : payload) {
            pCont.append(" ").append(Integer.toHexString(b));
        }

        return pCont.toString();
    }

    private static String dumpPayload2String(byte[] payload) {
        StringBuilder pCont = new StringBuilder();
        for (byte b : payload) {
            pCont.append((char) b);
        }

        return pCont.toString();
    }
}
