package com.namnh.nfcbasic.ui.tag.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment

class NfcWriteTagFragment : BaseNfcFragment() {

    private lateinit var nfcWriteTagViewModel: NfcWriteTagViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nfcWriteTagViewModel =
            ViewModelProviders.of(this).get(NfcWriteTagViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_write_tag, container, false)
        val textView: TextView = root.findViewById(R.id.tv_account)
        nfcWriteTagViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}