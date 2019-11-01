package com.namnh.nfcbasic.ui.tag.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NfcWriteTagViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Simple Write Tag Fragment"
    }
    val text: LiveData<String> = _text
}