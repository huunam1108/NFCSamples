package com.namnh.nfchelper

import com.namnh.nfchelper.Utils.hexStringToByteArray

object Constants {
    // AID for our loyalty card service.
    const val SAMPLE_LOYALTY_CARD_AID = "F222222222"
    // ISO-DEP command HEADER for selecting an AID.
    // https://cardwerk.com/smart-card-standard-iso7816-4-section-6-basic-interindustry-commands/
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    // 00 CLA -> No SM or no SM indication
    // A4 INS -> SELECT FILE
    // 04 P1 -> The parameter bytes P1-P2 of a command may have any value
    // 00 P2 -> If a parameter byte provides no further qualification, then it shall be set to ’00’
    const val SELECT_APDU_HEADER = "00A40400"
    // "OK" status word sent in response to SELECT AID command (0x9000)
    val SELECT_OK_SW = hexStringToByteArray("9000")
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    val UNKNOWN_CMD_SW = hexStringToByteArray("0000")
}