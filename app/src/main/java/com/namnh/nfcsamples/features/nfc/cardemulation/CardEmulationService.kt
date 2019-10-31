package com.namnh.nfcsamples.features.nfc.cardemulation

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.namnh.nfcsamples.data.SharedPrefApi
import com.namnh.nfcsamples.data.SharedPrefKey
import com.namnh.nfcsamples.features.Constants.SAMPLE_LOYALTY_CARD_AID
import com.namnh.nfcsamples.features.Constants.SELECT_OK_SW
import com.namnh.nfcsamples.features.Constants.UNKNOWN_CMD_SW
import com.namnh.nfcsamples.features.Utils
import com.namnh.nfcsamples.features.Utils.buildSelectApdu
import com.namnh.nfcsamples.features.Utils.concatArrays
import org.koin.android.ext.android.inject
import java.util.*

class CardEmulationService : HostApduService() {
    private val sharedPref: SharedPrefApi by inject()

    /**
     * Called if the connection to the NFC card is lost, in order to let the application know the
     * cause for the disconnection (either a lost link, or another AID being selected by the
     * reader).
     *
     * @param reason Either DEACTIVATION_LINK_LOSS or DEACTIVATION_DESELECTED
     */
    override fun onDeactivated(reason: Int) {
        Toast.makeText(applicationContext, "Disconnect with reader!", Toast.LENGTH_LONG).show()
    }

    /**
     * This method will be called when a command APDU has been received from a remote device. A
     * response APDU can be provided directly by returning a byte-array in this method. In general
     * response APDUs must be sent as quickly as possible, given the fact that the user is likely
     * holding his device over an NFC reader when this method is called.
     *
     * <p class="note">If there are multiple services that have registered for the same AIDs in
     * their meta-data entry, you will only get called if the user has explicitly selected your
     * service, either as a default or just for the next tap.
     *
     * <p class="note">This method is running on the main thread of your application. If you
     * cannot return a response APDU immediately, return null and use the {@link
     * #sendResponseApdu(byte[])} method later.
     *
     * @param commandApdu The APDU that received from the remote device
     * @param extras A bundle containing extra data. May be null.
     * @return a byte-array containing the response APDU, or null if no response APDU can be sent
     * at this point.
     */
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu == null || commandApdu.isEmpty()) return UNKNOWN_CMD_SW
        Log.i(TAG, "Received APDU: " + Utils.byteArrayToHexString(commandApdu))

        // If the APDU matches the SELECT AID command for this service,
        // send the loyalty card account number, followed by a SELECT_OK status trailer (0x9000).
        val selectApdu = buildSelectApdu(SAMPLE_LOYALTY_CARD_AID)
        if (Arrays.equals(selectApdu, commandApdu)) {
            val account = sharedPref.get(SharedPrefKey.ACCOUNT, String::class.java, "DEFAULT")
            val accountBytes = account.toByteArray()
            Log.i(TAG, "Sending account number: $account")
            return concatArrays(accountBytes, SELECT_OK_SW)
        }
        return UNKNOWN_CMD_SW
    }

    companion object {
        private const val TAG = "CardEmulationService"
    }
}