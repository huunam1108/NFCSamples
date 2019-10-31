package com.namnh.nfcsamples.features.nfc.cardemulation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.namnh.nfcsamples.R
import com.namnh.nfcsamples.features.nfc.BaseNfcFragment

class CardEmulationFragment : BaseNfcFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_emulation, container, false)
    }
}
