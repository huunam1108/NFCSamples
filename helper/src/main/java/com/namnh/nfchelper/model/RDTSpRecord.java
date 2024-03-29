package com.namnh.nfchelper.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RDTSpRecord extends RDTTextRecord {

    public String language;
    public String encoding;
    public String type;

    public List<BaseNdefRecord> records = new ArrayList<>();

    public RDTSpRecord() {
        this.TNF = 0x01;
    }

    public String toString() {
        return super.toString();
    }

    public static RDTSpRecord createRecord(byte[] payload) {

        RDTSpRecord record = new RDTSpRecord();

        while (payload.length > 0) {
            Log.d("NFC", "--------------------");

            int[] result = getHeader(payload);

            int numLenByte = 1;


            if (result[2] == 0)
                numLenByte = 4;

            String byteLen = "";

            for (int p = 2; p <= 2 + numLenByte - 1; p++)
                byteLen = byteLen + payload[p];

            Log.d("Nfc", "Byte [" + byteLen + "]");

            int pos = 2 + numLenByte;


            int type = payload[pos];
            Log.d("NFC", "Type:" + (char) type);
            pos++;

            if (type == 'U') {
                RDTUrl url = new RDTUrl();
                result = getHeader(payload);
                url.MB = result[0];
                url.ME = result[1];
                url.SR = result[2];
                url.prefix = payload[pos];
                Log.d("NFC", "Action:" + "0x" + Integer.toHexString(url.prefix));
                url.url = new String(payload, pos + 1, Integer.parseInt(byteLen) - 1);
                Log.d("NFC", "Content:" + url.url);
                record.records.add(url);
            } else if (type == 'T') {
                RDTTextRecord text = new RDTTextRecord();
                result = getHeader(payload);
                text.MB = result[0];
                text.ME = result[1];
                text.SR = result[2];
                int len = payload[pos];
                Log.d("Nfc", "Lan len [" + len + "]");
                text.language = "";
                for (int i = pos + 1; i <= pos + len; i++)
                    text.language = text.language + (char) payload[i];

                Log.d("Nfc", "Lang [" + text.language + "]");

                text.payload = new String(payload, pos + len + 1, Integer.parseInt(byteLen) - len - 1);
                Log.d("NFC", "Content:" + text.payload);

                record.records.add(text);
            }

            StringBuilder content = new StringBuilder();

            for (int p = pos + 1; p < Integer.parseInt(byteLen) - 1; p++)
                content.append(" ").append(payload[p]);


            payload = Arrays.copyOfRange(payload, pos + Integer.parseInt(byteLen), payload.length);

        }

        return record;

    }
}