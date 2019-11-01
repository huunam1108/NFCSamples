package com.namnh.nfcbasic.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.namnh.nfcbasic.R

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
        // move to main nfc ui
        mainHandler.postDelayed(gotoHomeFragment(), 1500)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null)
    }

    private fun gotoHomeFragment() = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_cardReaderFragment)
    }
}
