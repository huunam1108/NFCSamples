package com.namnh.nfcsamples.features.nfc.reader

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.namnh.nfcsamples.R
import com.namnh.nfcsamples.features.NfcHelper
import com.namnh.nfcsamples.features.nfc.BaseNfcFragment
import kotlinx.android.synthetic.main.fragment_card_reader.*
import org.koin.android.ext.android.inject

class CardReaderFragment : BaseNfcFragment(), LoyaltyCardReader.AccountCallback {

    private lateinit var loyaltyCardReader: LoyaltyCardReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loyaltyCardReader = LoyaltyCardReader(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_reader, container, false)
    }

    override fun onResume() {
        super.onResume()
        nfcHelper.enableReaderMode(activity, loyaltyCardReader, CARD_READER_FLAGS, null)
    }

    override fun onPause() {
        super.onPause()
        nfcHelper.disableReaderMode(activity)
    }

    override fun onAccountChecked(account: String) {
        activity?.runOnUiThread {
            tv_account.text = account
        }
    }

    companion object {
        private const val CARD_READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK
    }
}