package com.namnh.nfccardemulation.ui.cardemulation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.namnh.nfccardemulation.R
import com.namnh.nfchelper.data.SharedPrefApi
import com.namnh.nfchelper.data.SharedPrefKey
import kotlinx.android.synthetic.main.fragment_card_emulation.*
import org.koin.android.ext.android.inject

class CardEmulationFragment : Fragment() {
    private val sharedPref: SharedPrefApi by inject()
    private val args: CardEmulationFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_emulation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_account.text = if (args.account.isNullOrBlank()) {
            sharedPref.get(SharedPrefKey.ACCOUNT, String::class.java, "")
        } else {
            args.account
        }
    }
}
