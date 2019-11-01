package com.namnh.nfchelper

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.util.Log
import java.io.ByteArrayOutputStream
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
        readerCallback: NfcAdapter.ReaderCallback,
        cardReaderFlags: Int,
        extras: Bundle?
    ) {
        Log.i(TAG, "Enabling reader mode")
        nfcAdapter?.enableReaderMode(
            activity,
            readerCallback,
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

    fun enableWriteMode(
        activity: Activity?,
        pendingIntent: PendingIntent,
        intentFilter: Array<IntentFilter>,
        techList: Array<Array<String>>
    ) {
        activity?.apply {
            nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilter, techList)
        }
    }

    fun disableWriteMode(activity: Activity?) {
        activity?.apply {
            nfcAdapter?.disableForegroundDispatch(this)
        }
    }

    fun writeTag(tag: Tag, message: NdefMessage): Boolean {
        try {
            val ndefTag = Ndef.get(tag)
            if (ndefTag == null) {
                val ndefFormatable = NdefFormatable.get(tag)
                if (ndefFormatable != null) {
                    ndefFormatable.connect()
                    ndefFormatable.format(message)
                    ndefFormatable.close()
                    return true
                }
            } else {
                ndefTag.connect()
                ndefTag.writeNdefMessage(message)
                ndefTag.close()
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Can not write tag: ${e.message}")
        }
        return false
    }

    fun createUriMessage(content: String): NdefMessage {
        val record = NdefRecord.createUri(content)
        return NdefMessage(arrayOf(record))
    }

    fun createPhoneMessage(content: String): NdefMessage {
        val record = NdefRecord.createUri("tel$content")
        return NdefMessage(arrayOf(record))
    }

    fun createTextMessage(content: String): NdefMessage? {
        try {
            val lang = Locale.getDefault().language.toByteArray(Charsets.US_ASCII)
            val text = content.toByteArray(Charsets.UTF_8) // Content in UTF-8

            val langSize = lang.size
            val textLength = text.size

            val payload = ByteArrayOutputStream(1 + langSize + textLength)
            payload.write(langSize and 0x1F)
            payload.write(lang, 0, langSize)
            payload.write(text, 0, textLength)
            val record = NdefRecord(
                NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT,
                ByteArray(0),
                payload.toByteArray()
            )
            return NdefMessage(arrayOf(record))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Can not create text message")
        }

        return null
    }
}
