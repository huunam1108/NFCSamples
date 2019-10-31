package com.namnh.nfcsamples.features.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.namnh.nfcsamples.R

class SplashFragment : Fragment() {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signedAccount = GoogleSignIn.getLastSignedInAccount(context)
        if (signedAccount != null) {
            // move to main nfc ui
            mainHandler.postDelayed(gotoHomeFragment(), 1500)
        } else {
            // go to login fragment
            mainHandler.postDelayed(gotoLoginFragment(), 1500)
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null)
    }

    private fun gotoLoginFragment() = Runnable {
        findNavController().navigate(R.id.action_splash_fragment_to_login_fragment)
    }

    private fun gotoHomeFragment() = Runnable {
        findNavController().navigate(R.id.action_splash_fragment_to_nfc_writer_fragment)
    }
}
