package com.namnh.nfcbasic.ui.tag.write

import android.app.ProgressDialog
import android.nfc.NdefMessage
import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment
import com.namnh.nfchelper.NfcDetectedListener
import kotlinx.android.synthetic.main.fragment_write_tag.*

class NfcWriteTagFragment : BaseNfcFragment(), NfcDetectedListener {

    private var message: NdefMessage? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_tag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tagTypes = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tagContentType,
            android.R.layout.simple_dropdown_item_1line
        )
        sp_type.adapter = tagTypes

        btn_write_tag.setOnClickListener {
            val tagType = sp_type.selectedItemPosition
            val content = edt_content.text
            if (content.isNullOrBlank()) {
                Snackbar.make(
                    container,
                    "Please insert something to write !",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            message = when (tagType) {
                0 -> {
                    nfcHelper.createUriMessage(content.toString())
                }
                1 -> {
                    nfcHelper.createPhoneMessage(content.toString())
                }
                else -> {
                    nfcHelper.createTextMessage(content.toString())
                }
            }
            if (message != null) {
                if (progressDialog == null) {
                    progressDialog = ProgressDialog(activity)
                    progressDialog?.setMessage("Move your tag closer!")
                    progressDialog?.setCancelable(true)
                }
                progressDialog?.show()
            }
        }
    }

    override fun onNfcDetected(tag: Tag) {
        progressDialog?.dismiss()
        message?.let { message ->
            if (nfcHelper.writeTag(tag, message))
                Snackbar.make(
                    container,
                    "Write success!",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show()
            else
                Snackbar.make(
                    container,
                    "Write failed !",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show()
        }
    }
}
