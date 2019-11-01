package com.namnh.nfchelper.model;

import org.jetbrains.annotations.NotNull;

public class NDEFExternalType extends BaseNdefRecord {

    public String extContent;

    @NotNull
    public String toString() {
        return super.toString() + "Content: " + extContent;
    }


    public static NDEFExternalType createRecord(byte[] payload) {
        NDEFExternalType result = new NDEFExternalType();
        StringBuilder pCont = new StringBuilder();
        for (byte b : payload) {
            pCont.append((char) b);
        }

        result.extContent = pCont.toString();
        return result;
    }
}