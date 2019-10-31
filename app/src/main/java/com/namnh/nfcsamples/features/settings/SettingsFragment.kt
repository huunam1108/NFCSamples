package com.namnh.nfcsamples.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.namnh.nfcsamples.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private var googleSignInClient: GoogleSignInClient? = null
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_signout.setOnClickListener {
            revokeAccess()
        }
    }

    private fun revokeAccess() {
        if (googleSignInClient == null) {
            googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        }
        googleSignInClient?.signOut()?.addOnCompleteListener {
            activity?.finish()
        }

    }
}
