package com.namnh.nfcsamples.features.nfc

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import com.namnh.nfcsamples.features.NfcHelper
import org.koin.android.ext.android.inject

open class BaseNfcFragment : Fragment() {

    protected val nfcHelper: NfcHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNfcSettings()
    }

    private fun checkNfcSettings() {
        if (!nfcHelper.isNfcEnabled()) {
            showNfcSettings()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NFC_SETTING_REQUEST_CODE) {
            checkNfcSettings()
        }
    }

    private fun showNfcSettings() {
        val intent = Intent(Settings.ACTION_NFC_SETTINGS)
        startActivityForResult(intent, NFC_SETTING_REQUEST_CODE)
    }

    companion object {
        private const val NFC_SETTING_REQUEST_CODE = 10001
    }
}