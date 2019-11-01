package com.namnh.nfchelper

import android.nfc.Tag

interface NfcDetectedListener {
    fun onNfcDetected(tag: Tag)
}
