/**
 * Fundoo Notes
 * @description HomeDashBoardActivity displays all user actions.
 * @file HomeDashBoardActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.view.LabelFragment
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.DashBoardViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.DashBoardViewModelFactory
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
import com.bridgelabz.fundoonotes.repository.api_service.NoteCallBack
import com.bridgelabz.fundoonotes.repository.api_service.NoteResponseModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.api_service.RetrofitHelper
import com.bridgelabz.fundoonotes.repository.api_service.getNote
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

class HomeDashBoardActivity : AppCompatActivity() {

    private val notes = ArrayList<Note>()
    private val noteCallBack = object : NoteCallBack {
        override fun onNoteReceivedSuccess(noteResponseModel: NoteResponseModel) {
            Log.i(tag, noteResponseModel.toString())
            val note = noteResponseModel.getNote()
            Log.i(tag, note.toString())
            notes.add(note)
        }

        override fun onNoteReceivedFailure(exception: Throwable) {
            Log.i(tag, exception.message!!)
        }
    }
    private val tag = "HomeDashBoardActivity"
    private val dashBoadViewModelFactory: DashBoardViewModelFactory by lazy {
        DashBoardViewModelFactory(DatabaseHelper(this))
    }
    private val dashBoardViewModel: DashBoardViewModel by lazy {
        ViewModelProvider(this, dashBoadViewModelFactory).get(DashBoardViewModel::class.java)
    }
    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val drawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    private val navigationView by lazy {
        findViewById<NavigationView>(R.id.nav_view)
    }

    private val floatingActionButton by lazy {
        findViewById<FloatingActionButton>(R.id.fab)
    }

    private val preferences: SharedPreferences by lazy {
        this.getSharedPreferences(
            "LaunchScreen",
            Context.MODE_PRIVATE
        )
    }
    private lateinit var authenticatedEmail: String
    private var authenticatedUser: User? = null
    private var signInClient: GoogleSignInClient? = null
    private var googleAccount: GoogleSignInAccount? = null
    private var accessToken: AccessToken? = null
    //    private var currentFragment: Fragment = NoteFragment()
    private val retrofitHelper = RetrofitHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)
        setSupportActionBar(toolbar)

        initHomeDashBoardActivity()
        setClickOnFloatingActionButton()
        setNavigationItemClicked()
        setGoogleSignInClient()
        setFacebookAccessToken()
        retrofitHelper.addNoteToServer()
        retrofitHelper.getNotesFromServer(noteCallBack)
        Log.i(tag, notes.toString())
    }

    private fun observeCurrentFragment() {
        dashBoardViewModel.getFragmentLiveData().observe(this
            , Observer {
                replaceFragment(fragment = it)
            })
    }

    private fun setFacebookAccessToken() {
        accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null)
            useLoginInformation(accessToken!!)
    }

    private fun setGoogleSignInClient() {
        val signInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build()
        signInClient = GoogleSignIn.getClient(this, signInOption)
        googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(googleAccount)
    }

    private fun updateUI(googleAccount: GoogleSignInAccount?) {
        if (googleAccount != null) {
            val firstname = googleAccount.givenName
            val lastName = googleAccount.familyName
            val email = googleAccount.email
            val id = googleAccount.id
            authenticatedUser = User(firstname!!, lastName!!, email!!)
            authenticatedUser!!.id = id!!
        } else {
            authenticateUser()
        }
    }

    private fun authenticateUser() {

        dashBoardViewModel.authenticatedUser(authenticatedEmail)
        dashBoardViewModel.getUser().observe(this, Observer {
            observeUser(it)
        })
    }

    private fun observeUser(user: User?) {
        if (user != null)
            authenticatedUser = user
    }

    private fun useLoginInformation(accessToken: AccessToken) {
        GraphRequest.newMeRequest(accessToken, graphRequestCallback)
    }

    private val graphRequestCallback =
        GraphRequest.GraphJSONObjectCallback { `object`, _ ->
            try {
                val firstName = `object`!!.getString("name")
                val lastNAme = ""
                val email = `object`.getString("email")
                authenticatedUser = User(firstName, lastNAme, email)
                authenticatedEmail = email
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

    private fun initHomeDashBoardActivity() {

        observeCurrentFragment()
        getUserSharedPreferences()
        setActionBarToggle()
//        setNoteFragment()
    }

//    private fun setNoteFragment() {
//        navigationView.setCheckedItem(R.id.nav_home)
//        val fragment = NoteFragment()
//        replaceFragment(currentFragment)
//    }

    private fun getUserSharedPreferences() {
        val editor = preferences.edit()
        val email = preferences.getString("email", "")
        authenticatedEmail = email!!
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_dash_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_user_info -> {
                startUserProfileFragment()
                toast(getString(R.string.tast_when_user_profile_clicked))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        callFragmentsOnBackPressed()
        super.onBackPressed()
    }

    /**Function to set Floating Action Bar when it is clicked*/
    private fun setClickOnFloatingActionButton() {
        floatingActionButton.setOnClickListener {
            val bundle = setNoteArguments()
            replaceAddNoteFragment(bundle)
        }
    }

    private fun setNoteArguments(): Bundle? {
        val bundle = Bundle()
        val note = Note()
        if (authenticatedUser != null)
            note.userId = authenticatedUser!!.id
        bundle.putSerializable(getString(R.string.note), note)
        return bundle
    }

    /**Function to set Navigation Items when they are clicked*/
    private fun setNavigationItemClicked(): Boolean {

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    dashBoardViewModel.setFragmentLiveData(NoteFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sing_out -> {
                    onSignOutMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_archive -> {
                    dashBoardViewModel.setFragmentLiveData(ArchiveFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_delete -> {
                    dashBoardViewModel.setFragmentLiveData(TrashFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_label -> {
                    dashBoardViewModel.setFragmentLiveData(LabelFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_reminder -> {
                    dashBoardViewModel.setFragmentLiveData(ReminderFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_pinned -> {
                    dashBoardViewModel.setFragmentLiveData(PinnedNoteFragment())
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        drawerLayout.closeDrawer(navigationView)
        if (fragment.isAdded) return

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


    /**Function to set Action Bar Toggle of Drawer Layout*/
    private fun setActionBarToggle() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    /**Function to set alert dialog when SignOut
     * Menu item is clicked */
    private fun onSignOutMenuClick() {
        signOutAlertDialog()
    }

    private fun removePreference() {
        if (preferences.contains("email")) {
            val editor = preferences.edit()
            editor.clear().apply()
        }
    }

    /**Function that performs sign out alert operation*/
    private fun signOutAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setMessage(getString(R.string.sign_out_alert_message))
        alertDialogBuilder.setTitle(getString(R.string.sign_out_alert_title))
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder
            .setPositiveButton(
                getString(R.string.sign_out_alert_positive_button)
            ) { _, _ ->
                checkForGoogleAccount(googleAccount)
                checkForFaceBookAccount(accessToken)
                removePreference()
                navigateToLoginScreen()
                toast(
                    getString(R.string.toast_when_sign_out_alert_positive_button_clicked)
                )
            }
        alertDialogBuilder.setNegativeButton(getString(R.string.sign_out_alerst_negative_button))
        { dialog, _ ->
            drawerLayout.closeDrawer(navigationView)
            dialog.cancel()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun checkForFaceBookAccount(accessToken: AccessToken?) {
        if (accessToken != null)
            LoginManager.getInstance().logOut()
    }

    private fun checkForGoogleAccount(googleAccount: GoogleSignInAccount?) {
        if (googleAccount != null) {
            signInClient!!.signOut()
        }
    }

    /**Function to tell fragment that back navigation is Pressed*/
    private fun callFragmentsOnBackPressed() {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is OnBackPressed) {
                fragment.onBackPressed()
                setNoteFragment()
            }
        }
    }

    private fun setNoteFragment() {
        navigationView.setCheckedItem(R.id.nav_home)
        dashBoardViewModel.setFragmentLiveData(NoteFragment())
    }

    /**Function to replace home dash board with AddNoteFragment*/
    private fun replaceAddNoteFragment(bundle: Bundle?) {
        val fragment = AddNoteFragment()
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addSharedElement(floatingActionButton, "shared_element_container")
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**Function to navigate to LoginActivity*/
    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun startUserProfileFragment(): Boolean {
        val fragmentManager = supportFragmentManager
        val reminderDialog = UserProfileDialogFragment()
        reminderDialog.arguments = Bundle().apply {
            putSerializable(getString(R.string.authenticated_user), authenticatedUser)
        }
        reminderDialog.show(fragmentManager, getString(R.string.dialog_reminder_title))
        return true
    }
}