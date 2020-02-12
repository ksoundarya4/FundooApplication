package com.bridgelabz.fundoonotes.user_module.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.login.viewmodel.AuthViewModel
import com.bridgelabz.fundoonotes.user_module.registration.model.validateConfirmPassword

class ForgotPasswordActivity : AppCompatActivity() {

    private val newPasswordEditText by lazy {
        findViewById<EditText>(R.id.new_password_edit_text)
    }
    private val confirmPasswordEditText by lazy {
        findViewById<EditText>(R.id.new_confirm_password_edit_text)
    }
    private val submiteButton by lazy {
        findViewById<Button>(R.id.buttom_new_password_submit)
    }
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSubmitButtonClickListener()

    }

    private fun setSubmitButtonClickListener() {
        val email = intent.getStringExtra("email")
        submiteButton.setOnClickListener {
            val newPassword = newPasswordEditText.editableText.toString()
            val newConfirmPassword = confirmPasswordEditText.editableText.toString()
            if (validateConfirmPassword(newPassword, newConfirmPassword)) {
                viewModel.onPasswordSubmitButtonClick(View(this), email!!, newPassword)
                viewModel.getUpdateStatus().observe(this, Observer { handleUpdatePassword(it) })
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
        } else {
            toast("Email does not exist. Register and then try to login")
        }
    }
}