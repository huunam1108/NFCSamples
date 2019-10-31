package com.namnh.nfcsamples.features

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import com.namnh.nfcsamples.features.nfc.reader.LoyaltyCardReader
import java.util.*


class NfcHelper(private val context: Context) {

    companion object {
        private const val TAG = "NfcHelper"
    }

    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(context)
    }

    fun isNfcSupported(): Boolean {
        return nfcAdapter != null
    }

    fun isNfcEnabled(): Boolean {
        return isNfcSupported() && nfcAdapter!!.isEnabled
    }

    fun enableReaderMode(
        activity: Activity?,
        loyaltyCardReader: LoyaltyCardReader,
        cardReaderFlags: Int,
        extras: Bundle?
    ) {
        Log.i(TAG, "Enabling reader mode")
        nfcAdapter?.enableReaderMode(
            activity,
            loyaltyCardReader,
            cardReaderFlags,
            extras
        )
    }

    fun disableReaderMode(activity: Activity?) {
        Log.i(TAG, "Disabling reader mode")
        nfcAdapter?.disableReaderMode(activity)
    }

    fun createTextRecord(
        payload: String,
        locale: Locale,
        encodeInUtf8: Boolean = true
    ): NdefRecord {
        val langBytes = locale.language.toByteArray(Charsets.US_ASCII)
        val utfEncoding = if (encodeInUtf8) Charsets.UTF_8 else Charsets.UTF_16
        val textBytes = payload.toByteArray(utfEncoding)
        val utfBit: Int = if (encodeInUtf8) 0 else 1 shl 7
        val status = (utfBit + langBytes.size).toChar()
        val data = ByteArray(1 + langBytes.size + textBytes.size)
        data[0] = status.toByte()
        System.arraycopy(langBytes, 0, data, 1, langBytes.size)
        System.arraycopy(textBytes, 0, data, 1 + langBytes.size, textBytes.size)
        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), data)
    }

    fun enableWriterMode(
        activity: Activity?,
        pendingIntent: PendingIntent,
        intentFilter: Array<IntentFilter>,
        techList: Array<Array<String>>
    ) {
        activity?.apply {
            nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilter, techList)
        }
    }

    fun disableWriterMode(activity: Activity?) {
        activity?.apply {
            nfcAdapter?.disableForegroundDispatch(this)
        }
    }

}
