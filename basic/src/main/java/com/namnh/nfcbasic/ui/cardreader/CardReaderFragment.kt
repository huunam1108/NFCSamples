package com.namnh.nfcbasic.ui.cardreader

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment
import kotlinx.android.synthetic.main.fragment_card_reader.*

class CardReaderFragment : BaseNfcFragment(), LoyaltyCardReader.AccountCallback {

    private lateinit var cardReaderViewModel: CardReaderViewModel
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
        cardReaderViewModel =
            ViewModelProviders.of(this).get(CardReaderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_card_reader, container, false)
        val textView: TextView = root.findViewById(R.id.tv_account)
        cardReaderViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
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