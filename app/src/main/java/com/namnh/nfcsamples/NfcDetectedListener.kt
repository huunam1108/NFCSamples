package com.namnh.nfcsamples

import android.nfc.Tag

interface NfcDetectedListener {
    fun onNfcDectected(tag: Tag)
}