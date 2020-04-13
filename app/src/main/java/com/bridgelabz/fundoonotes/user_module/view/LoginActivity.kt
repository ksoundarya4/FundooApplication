/**
 * Fundoo Notes
 * @description LoginActivity class that contain
 * user email and password to login.
 * @file LoginActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModel
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModelFactory
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {

    private val signInRequestCode = 0
    private var signInClient: GoogleSignInClient? = null
    private val emailKey = "email"
    private val tag = "LoginActivity"
    private val emailEditText by lazy {
        findViewById<EditText>(R.id.login_email)
    }
    private val passwordEditText by lazy {
        findViewById<EditText>(R.id.login_password)
    }
    private val emailErrorText by lazy {
        findViewById<TextView>(R.id.email_error_text)
    }
    private val passwordErrorText by lazy {
        findViewById<TextView>(R.id.passwor_error_text)
    }
    private val loginButton by lazy {
        findViewById<Button>(R.id.button_login)
    }
    private val forgotPasswordButton by lazy {
        findViewById<Button>(R.id.button_forgot_password)
    }
    private val registerButton by lazy {
        findViewById<Button>(R.id.button_register)
    }
    private val loginActivity by lazy {
        findViewById<ConstraintLayout>(R.id.login_constaint_layout)
    }
    private val preferences: SharedPreferences by lazy {
        FundooNotesPreference.getPreference(this)
    }
    private val googleSignInButton by lazy {
        findViewById<SignInButton>(R.id.google_sign_in_button)
    }
    private val facebookSignInButton by lazy {
        findViewById<LoginButton>(R.id.facebook_sign_in_button)
    }
    private val callbackManager = CallbackManager.Factory.create()

    private val userViewModelFactory by lazy {
        UserViewModelFactory(
            this
        )
    }
    private val userViewModel by lazy {
        ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText.addTextChangedListener(emailWatcher)
        passwordEditText.addTextChangedListener(passwordWatcher)
        setButtonClickListeners()
        setHideKeyboardOnTouch(
            context = this,
            view = loginActivity
        )
        setGoogleSignInOption()
    }

    /**
     * To set GoogleSignIn Option
     */
    private fun setGoogleSignInOption() {
        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        signInClient = GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun setButtonClickListeners() {
        setClickOnLoginButton()
        setClickOnRegisterButton()
        setClickOnForgotPasswordButton()
        setClickToGoogleButton()
        setUpFacebookButton()
    }

    private fun setClickOnLoginButton() {
        loginButton.setOnClickListener {
            val inputEmail = emailEditText.editableText.toString()
            val inputPassword = passwordEditText.editableText.toString()
            if (isNetworkAvailable(this)) {
                userViewModel.userLogin(inputEmail, inputPassword,preferences)
                userViewModel.getLoginResponse().observe(this, Observer { handelLoginStatus(it) })
            } else {
                showSnackBar(loginActivity, "No Internet Connection")
            }
        }
    }

    private fun setClickOnRegisterButton() {
        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    private fun setClickOnForgotPasswordButton() {
        forgotPasswordButton.setOnClickListener {
            val email = emailEditText.editableText.toString()
            Intent(this, ForgotPasswordActivity::class.java).apply {
                putExtra("email", email)
                startActivity(this)
            }
        }
    }

    private fun setUpFacebookButton() {
        facebookSignInButton.setPermissions(listOf(emailKey))
        facebookSignInButton.registerCallback(callbackManager, facebookCallback)
    }

    private fun setClickToGoogleButton() {
        googleSignInButton.setSize(SignInButton.SIZE_WIDE)
        googleSignInButton.setOnClickListener {
            onGoogleSignInButtonClicked()
        }
    }

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            navigateToHomeDashBoard()
        }

        override fun onCancel() {
            Log.d(tag, "cancelled")
        }

        override fun onError(error: FacebookException?) {
            Log.d(tag, "Failed")
            toast("Connect to network")
        }
    }

    private fun onGoogleSignInButtonClicked() {
        val signIntent = signInClient!!.signInIntent
        startActivityForResult(signIntent, signInRequestCode)
    }

    /**Function to notify user based on Login status
     *
     * @param loginStatus contain the authentication state
     * of email and password.
     */
    private fun handelLoginStatus(loginStatus: AuthState) {
        when (loginStatus) {
            AuthState.NOT_AUTH -> {
                toast(getString(R.string.toast_email_not_registered))
            }
            AuthState.AUTH_FAILED -> {
                toast(getString(R.string.toast_login_unsuccessful))
                passwordEditText.error = getString(R.string.invalidPassword)
            }
            AuthState.AUTH -> {
                val inputEmail = emailEditText.editableText.toString()
                setSharedPreference(inputEmail)
                toast(getString(R.string.toast_login_successful))
                navigateToHomeDashBoard()
            }
        }
    }

    private fun setSharedPreference(email: String) {
        FundooNotesPreference.editPreference(
            preference = preferences,
            key = emailKey,
            value = email
        )
    }

    /**Email text watcher*/
    private val emailWatcher = object : TextWatcher {

        override fun afterTextChanged(editableEmail: Editable?) {
            if (editableEmail.isNullOrEmpty())
                emailErrorText.text = getString(R.string.emptyEmail)
            if (editableEmail!!.length >= 30)
                emailErrorText.text = getString(R.string.emailTooLong)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    /**Password text watcher*/
    private val passwordWatcher = object : TextWatcher {
        override fun afterTextChanged(editablePassword: Editable?) {
            if (editablePassword.isNullOrEmpty())
                passwordErrorText.text = getString(R.string.emptyPassword)
            else {
                if (editablePassword.length < 5)
                    passwordErrorText.text = getString(R.string.shortPasswordLength)
                else
                    passwordErrorText.text = getString(R.string.validPasswordLength)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == signInRequestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task!!)
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val userEmail = account!!.email
            setSharedPreference(userEmail!!)
            navigateToHomeDashBoard()
        } catch (exception: ApiException) {
            toast("Sign in Failed")
            Log.w(tag, "signInResult:failed code=" + exception.statusCode)
        }
    }

    private fun navigateToHomeDashBoard() {
        val intent = Intent(this, HomeDashBoardActivity::class.java)
        finish()
        startActivity(intent)
    }
}