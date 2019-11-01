package com.namnh.nfcbasic.ui.tag.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NfcReadTagViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Simple Read Tag Fragment"
    }
    val text: LiveData<String> = _text
}
