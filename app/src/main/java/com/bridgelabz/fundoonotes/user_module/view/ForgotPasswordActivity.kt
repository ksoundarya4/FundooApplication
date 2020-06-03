package com.bridgelabz.fundoonotes.user_module.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.model.validateConfirmPassword
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModel
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModelFactory

class ForgotPasswordActivity : AppCompatActivity() {

    private val newPasswordEditText by lazy {
        findViewById<EditText>(R.id.new_password_edit_text)
    }
    private val confirmPasswordEditText by lazy {
        findViewById<EditText>(R.id.new_confirm_password_edit_text)
    }
    private val submitButton by lazy {
        findViewById<Button>(R.id.buttom_new_password_submit)
    }
    private val userViewModelFactory by lazy {
        UserViewModelFactory(
            this
        )
    }
    private val userViewModel by lazy {
        ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }
    private val forgotPasswordActivity by lazy {
        findViewById<ConstraintLayout>(R.id.forgot_password_constraint_layout)
    }
    private val preference by lazy {
        FundooNotesPreference.getPreference(this)
    }
    private val emailKey = "email"
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSubmitButtonClickListener()
        setHideKeyboardOnTouch(
            this,
            forgotPasswordActivity
        )
        getEmailFromIntent()
    }

    private fun getEmailFromIntent() {
        val emailFromIntent = intent.getStringExtra(emailKey)
        if (emailFromIntent != null)
            email = emailFromIntent
        else
            handleUpdatePassword(false)
    }

    private fun setSubmitButtonClickListener() {
        val accessToken = preference.getString("access_token", null)
        submitButton.setOnClickListener {
            if (isNetworkAvailable(this)) {
                val newPassword = newPasswordEditText.editableText.toString()
                val newConfirmPassword = confirmPasswordEditText.editableText.toString()
                if (validateConfirmPassword(
                        newPassword,
                        newConfirmPassword
                    )
                ) {
                    userViewModel.updateNewPassword(email, newPassword, accessToken!!)
                    userViewModel.getUpdatePasswordStatus().observe(
                        this,
                        Observer { updateStatus -> handleUpdatePassword(updateStatus) })
                } else {
                    confirmPasswordEditText.error = "Did not match password"
                }
            } else
                showSnackBar(it, "No Internet")
        }
    }

    private fun handleUpdatePassword(updateStatus: Boolean) {
        if (updateStatus) {
            setSharedPreference(email)
            navigateToHomeDashBOard()
            toast("Login Successful")
        } else {
            toast("Email does not exist. Register and then try to login")
        }
    }

    private fun navigateToHomeDashBOard() {
        Intent(this, HomeDashBoardActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun setSharedPreference(email: String) {
        FundooNotesPreference.editPreference(
            preference = preference,
            key = emailKey,
            value = email
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}