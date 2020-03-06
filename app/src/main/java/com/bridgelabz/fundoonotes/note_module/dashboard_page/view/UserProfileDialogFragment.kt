package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.registration.model.User

class UserProfileDialogFragment : DialogFragment() {

    private val userProfilePicture: ImageView by lazy {
        requireView().findViewById<ImageView>(R.id.user_profile_pic)
    }
    private val userEmail: TextView by lazy {
        requireView().findViewById<TextView>(R.id.user_email_text)
    }
    private val userFullName: TextView by lazy {
        requireView().findViewById<TextView>(R.id.user_full_name_text)
    }
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getUserArgument()
    }

    private fun getUserArgument() {
        if (arguments != null) {
            user = arguments!!.get(getString(R.string.authenticated_user)) as User
            val fullName = "${user.firstName} ${user.lastName}"
            userFullName.text = fullName
            userEmail.text = user.email
        }
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}