package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class UserProfileDialogFragment : DialogFragment(), GoogleApiClient.OnConnectionFailedListener {

    private val userProfilePicture by lazy {
        requireView().findViewById<ImageView>(R.id.user_profile_pic)
    }
    private val userEmail by lazy {
        requireView().findViewById<TextView>(R.id.user_email_text)
    }
    private val userFullName by lazy {
        requireView().findViewById<TextView>(R.id.user_full_name_text)
    }
    private lateinit var user: User
    private var googleApiClient: GoogleApiClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getGoogleApiClient()
    }

    private fun getGoogleApiClient() {
        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleApiClient =
            GoogleApiClient.Builder(requireActivity()).enableAutoManage(requireActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOption).build()
    }

    override fun onStart() {
        super.onStart()
        val optionalPendingResult =
            Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (optionalPendingResult.isDone) {
            val result: GoogleSignInResult = optionalPendingResult.get()
            handleGoogleSignInResult(result)
        } else {
            getUserArgument()
        }
    }

    private fun handleGoogleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            userFullName.text = account!!.displayName
            userEmail.text = account.email
            val imageUri: Uri? = account.photoUrl
            userProfilePicture.setImageURI(imageUri)
        } else {
            getUserArgument()
        }
    }

    private fun getUserArgument() {
        if (arguments != null) {
            try {
                user = arguments?.get(getString(R.string.authenticated_user)) as User
                val fullName = "${user.firstName} ${user.lastName}"
                userFullName.text = fullName
                userEmail.text = user.email
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("ReminderFragment", "Connection Failed")
    }

    override fun onPause() {
        super.onPause()
        googleApiClient!!.stopAutoManage(requireActivity())
        googleApiClient!!.disconnect()
    }
}