package com.namnh.nfcsamples.features

import com.namnh.nfcsamples.features.Utils.hexStringToByteArray

object Constants {
    // AID for our loyalty card service.
    const val SAMPLE_LOYALTY_CARD_AID = "F222222222"
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    const val SELECT_APDU_HEADER = "00A40400"
    // "OK" status word sent in response to SELECT AID command (0x9000)
    val SELECT_OK_SW = byteArrayOf(0x90.toByte(), 0x00.toByte())
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    val UNKNOWN_CMD_SW = hexStringToByteArray("0000")
}