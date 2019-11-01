package com.namnh.nfcbasic

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.namnh.nfchelper.NfcHelper
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_card_reader, R.id.nav_write_tag, R.id.nav_read_tag
            )
        )
        navController?.apply {
            addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.nav_splash -> {
                        nav_bottom.visibility = View.GONE
                        supportActionBar?.hide()
                    }
                    else -> {
                        nav_bottom.visibility = View.VISIBLE
                        supportActionBar?.show()
                    }
                }
            }
            setupActionBarWithNavController(this, appBarConfiguration)
            nav_bottom.setupWithNavController(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }
}
