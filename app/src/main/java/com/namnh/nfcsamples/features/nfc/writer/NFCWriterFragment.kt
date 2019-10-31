package com.namnh.nfcsamples.features.nfc.writer

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.namnh.nfcsamples.MainActivity
import com.namnh.nfcsamples.NfcDetectedListener
import com.namnh.nfcsamples.R
import com.namnh.nfcsamples.data.SharedPrefApi
import com.namnh.nfcsamples.data.SharedPrefKey
import com.namnh.nfcsamples.features.nfc.BaseNfcFragment
import kotlinx.android.synthetic.main.fragment_nfc_writer.*
import org.koin.android.ext.android.inject
import java.util.*

class NFCWriterFragment : BaseNfcFragment(), NfcDetectedListener {
    private val sharedPref: SharedPrefApi by inject()
    private var nfcTag: Tag? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nfc_writer, container, false)
    }


    override fun onResume() {
        super.onResume()
        if (nfcHelper.isNfcEnabled()) {
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0
            )
            val intentFiltersArray = arrayOf<IntentFilter>()
            val techList = arrayOf(
                arrayOf(android.nfc.tech.Ndef::class.java.name),
                arrayOf(android.nfc.tech.NdefFormatable::class.java.name)
            )
            nfcHelper.enableWriterMode(activity, pendingIntent, intentFiltersArray, techList)
        }
    }

    override fun onPause() {
        super.onPause()
        if (nfcHelper.isNfcEnabled()) {
            nfcHelper.disableWriterMode(activity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val account = sharedPref.get(SharedPrefKey.ACCOUNT, String::class.java, null)
        tv_account.text = "Hello $account"
        btn_write_tag.setOnClickListener {
            if (nfcHelper.isNfcEnabled()) {
                writeToTag()
            }
        }
    }

    private fun writeToTag() {
        val payload = tv_account.text
        if (payload.isNullOrBlank()) {
            Toast.makeText(context, "Can not write empty text", Toast.LENGTH_LONG).show()
            return
        }
        if (nfcTag == null) {
            Toast.makeText(context, "Move tag closer!", Toast.LENGTH_LONG).show()
            return
        }
        val ndefRecord = nfcHelper.createTextRecord(payload.toString(), Locale.getDefault(), true)
        val ndefMessage = NdefMessage(arrayOf(ndefRecord))
        val ndef = Ndef.get(nfcTag)
        if (ndef == null) {
            val ndefFormatable = NdefFormatable.get(nfcTag)
            if (ndefFormatable != null) {
                ndefFormatable.connect()
                ndefFormatable.format(ndefMessage)
                ndefFormatable.close()
            }
            return
        }

        try {
            ndef.connect()
            ndef.writeNdefMessage(ndefMessage)
            ndef.close()
            Toast.makeText(context, "Write tag success", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Write tag failed, Move tag closer!", Toast.LENGTH_LONG).show()
        }
    }


    override fun onNfcDectected(tag: Tag) {
        Toast.makeText(context, "Found tag", Toast.LENGTH_SHORT).show()
        nfcTag = tag
    }
}
