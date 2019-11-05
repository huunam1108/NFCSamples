package com.namnh.nfcbasic.ui.cardreader

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment
import kotlinx.android.synthetic.main.fragment_card_reader.*

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
        // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
        // activity is interested in NFC-A devices (including other Android devices), and that the
        // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
        private const val CARD_READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK
    }
}