package com.namnh.nfcbasic.ui.cardreader

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.util.Log
import com.namnh.nfchelper.Constants.SAMPLE_LOYALTY_CARD_AID
import com.namnh.nfchelper.Constants.SELECT_OK_SW
import com.namnh.nfchelper.Utils.buildSelectApdu
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

class LoyaltyCardReader(private val accountCallback: AccountCallback) : NfcAdapter.ReaderCallback {

    interface AccountCallback {
        fun onAccountChecked(account: String)
    }

    override fun onTagDiscovered(tag: Tag?) {
        if (tag == null) {
            return
        }
        Log.i(TAG, "New tag discovered")
        val isoDep = IsoDep.get(tag) ?: return
        val weakAccountCallback = WeakReference(accountCallback)
        try {
            // Connect to the remote NFC device
            isoDep.connect()
            // Build SELECT AID command for our loyalty card service.
            // This command tells the remote device which service we wish to communicate with.
            Log.i(TAG, "Requesting remote AID: $SAMPLE_LOYALTY_CARD_AID")
            val command = buildSelectApdu(SAMPLE_LOYALTY_CARD_AID)
            // Send command to remote device
            val result = isoDep.transceive(command)
            // If AID is successfully selected, 0x9000 is returned as the status word (last 2
            // bytes of the result) by convention. Everything before the status word is
            // optional payload, which is used here to hold the account number.
            val resultLength = result.size
            val status = byteArrayOf(result[resultLength - 2], result[resultLength - 1])
            val payload = result.copyOf(resultLength - 2)

            if (Arrays.equals(SELECT_OK_SW, status)) {
                // The remote NFC device will immediately respond with its stored account number
                val account = String(payload, Charsets.UTF_8)
                Log.i(TAG, "Account Received: $account")
                weakAccountCallback.get()?.onAccountChecked(account)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "error while communicating with card: ${e.message}")
        }
    }


    companion object {
        private const val TAG = "LoyaltyCardReader"
    }
}
