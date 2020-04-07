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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSubmitButtonClickListener()
        setHideKeyboardOnTouch(
            this,
            forgotPasswordActivity
        )
    }

    private fun setSubmitButtonClickListener() {
        val email = intent.getStringExtra("email")
        val accessToken = preference.getString("access_token", null)
        submitButton.setOnClickListener {
            val newPassword = newPasswordEditText.editableText.toString()
            val newConfirmPassword = confirmPasswordEditText.editableText.toString()
            if (validateConfirmPassword(
                    newPassword,
                    newConfirmPassword
                )
            ) {
                userViewModel.updateNewPassword(email!!, newPassword, accessToken!!)
                userViewModel.getUpdatePasswordStatus()
                    .observe(this, Observer { handleUpdatePassword(it) })
            } else {
                confirmPasswordEditText.error = "Did not match password"
            }
        }
    }

    private fun handleUpdatePassword(updateStatus: Boolean) {
        if (updateStatus) {
            Intent(this, HomeDashBoardActivity::class.java).apply {
                startActivity(this)
            }
            toast("Login Successful")
            finish()
        } else {
            toast("Email does not exist. Register and then try to login")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}