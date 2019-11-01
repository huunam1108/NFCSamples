package com.namnh.nfcbasic.ui.tag.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.namnh.nfcbasic.R
import com.namnh.nfcbasic.ui.BaseNfcFragment

class NfcReadTagFragment : BaseNfcFragment() {

    private lateinit var nfcReadTagViewModel: NfcReadTagViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nfcReadTagViewModel =
            ViewModelProviders.of(this).get(NfcReadTagViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_read_tag, container, false)
        val textView: TextView = root.findViewById(R.id.tv_tag_info)
        nfcReadTagViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}
