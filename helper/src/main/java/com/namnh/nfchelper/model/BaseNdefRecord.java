package com.namnh.nfchelper.model;

public class BaseNdefRecord {
    /**
     * Message Begin, If set as 1, it means the first Record in the NDEF Message
     */
    protected int MB;
    /**
     * Message End, If set as 1, it means the last Record in the NDEF Message
     */
    protected int ME;
    /**
     * Short Record, If set as 1, it means that payload length included in this Record is between 0-255
     */
    protected int SR;
    /**
     * Type Name Format, This coding format consists of 3 bits; therefore, the type setting value is 0~7 <br>
     * <ul>
     * <li>0x00: Empty Types.</li>
     * <li>0x01: NFC Forum Well Know Types; Payload Type is in accordance with RTD specifications.</li>
     * <li>0x02: Media Types; Payload type is in accordance with RFC 2046.</li>
     * <li>0x03: Absolute URI, Uniform Resource Identifier (URI) Types; Payload Type is in accordance with RFC 3986</li>
     * <li>0x04: NFC Forum External Types; Payload Type is in accordance with RTD specifications.</li>
     * <li>0x05: Unknown Types. </li>
     * <li>0x06: Unchanged, Types is the same as the previous Record; applied on Chuck Records.</li>
     * <li>0x07: Reserved.</li>
     * </ul>
     */
    protected int TNF;
    /**
     * Payload data, User data
     */
    public String payload;

    public String toString() {
        return "MB:" + MB +
                " ME:" + ME +
                " SR:" + SR +
                " TNF:" + TNF;
    }

    static int[] getHeader(byte[] payload) {
        byte header = payload[0];
        int[] result = new int[3];

        result[0] = (header & 0x80) >> 7;
        result[1] = (header & 0x40) >> 6;
        result[2] = (header & 0x10) >> 4;

        return result;
    }
}
