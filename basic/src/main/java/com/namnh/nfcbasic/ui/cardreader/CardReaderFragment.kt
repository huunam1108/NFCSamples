package com.namnh.nfcbasic.ui.cardreader

import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment
import com.namnh.nfcbasic.ui.cardreader.adapter.AccountAdapter
import com.namnh.nfchelper.data.SharedPrefApi
import com.namnh.nfchelper.data.SharedPrefKey
import kotlinx.android.synthetic.main.fragment_card_reader.*
import org.koin.android.ext.android.inject

class CardReaderFragment : BaseNfcFragment(), LoyaltyCardReader.AccountCallback {

    private lateinit var loyaltyCardReader: LoyaltyCardReader
    private val registeredAccounts = mutableListOf<String>()
    private lateinit var accountAdapter: AccountAdapter
    private val sharedPref: SharedPrefApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loyaltyCardReader = LoyaltyCardReader(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registeredAccounts.clear()
        sharedPref.getList(SharedPrefKey.REGISTERED_ACCOUNTS, String::class.java)?.map {
            registeredAccounts.add(it)
        }
        handleUi()
        accountAdapter = AccountAdapter(registeredAccounts)
        rv_account.layoutManager = LinearLayoutManager(context)
        rv_account.adapter = accountAdapter
    }

    private fun handleUi() {
        if (registeredAccounts.isEmpty()) {
            rv_account.visibility = View.GONE
            tv_account.visibility = View.VISIBLE
            tv_account.text = getString(R.string.ready)
        } else {
            rv_account.visibility = View.VISIBLE
            tv_account.visibility = View.GONE
        }
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
            if (registeredAccounts.contains(account)) {
                Toast.makeText(context, "Account's already registered", Toast.LENGTH_SHORT).show()
                return@runOnUiThread
            }
            registeredAccounts.add(account)
            handleUi()
            accountAdapter.notifyItemInserted(registeredAccounts.size - 1)
        }
        sharedPref.putList(SharedPrefKey.REGISTERED_ACCOUNTS, registeredAccounts)
    }

    companion object {
        // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
        // activity is interested in NFC-A devices (including other Android devices), and that the
        // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
        private const val CARD_READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK
    }
}