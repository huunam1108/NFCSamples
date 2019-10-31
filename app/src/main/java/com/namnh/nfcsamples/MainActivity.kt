package com.namnh.nfcsamples

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.namnh.nfcsamples.features.NfcHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private val nfcHelper: NfcHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!nfcHelper.isNfcSupported()) {
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        navController = findNavController(R.id.nav_host_fragment)
        navController?.apply {
            addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splash_fragment, R.id.login_fragment -> nav_bottom.visibility = View.GONE
                    else -> nav_bottom.visibility = View.VISIBLE
                }
            }
            nav_bottom.setupWithNavController(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        val nfcTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is NfcDetectedListener) {
            currentFragment.onNfcDectected(nfcTag)
        }
    }
}
