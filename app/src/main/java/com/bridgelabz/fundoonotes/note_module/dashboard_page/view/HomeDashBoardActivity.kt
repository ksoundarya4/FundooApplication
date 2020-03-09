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
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class HomeDashBoardActivity : AppCompatActivity() {

    private val dashBoadViewModelFactory: DashBoardViewModelFactory by lazy {
        DashBoardViewModelFactory(DatabaseHelper(this))
    }
    private val dashBoardViewModel: DashBoardViewModel by lazy {
        ViewModelProvider(this, dashBoadViewModelFactory).get(DashBoardViewModel::class.java)
    }
    private val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    private val drawerLayout: DrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    private val navigationView: NavigationView by lazy {
        findViewById<NavigationView>(R.id.nav_view)
    }

    private val floatingActionButton: FloatingActionButton by lazy {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)
        setSupportActionBar(toolbar)

        initHomeDashBoardActivity()
        authenticateUser()
        setClickOnFloatingActionButton()
        setNavigationItemClicked()
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

    private fun initHomeDashBoardActivity() {

        getUserSharedPreferences()
        setNoteFragment()
        setActionBarToggle()
    }

    private fun setNoteFragment() {
        navigationView.setCheckedItem(R.id.nav_home)
        val fragment = NoteFragment()
        replaceFragment(fragment)
    }

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
        note.userId = authenticatedUser!!.id
        bundle.putSerializable(getString(R.string.note), note)
        return bundle
    }

    /**Function to set Navigation Items when they are clicked*/
    private fun setNavigationItemClicked(): Boolean {

        navigationView.setNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.nav_home -> {
                    fragment = NoteFragment()
                    replaceFragment(fragment)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sing_out -> {
                    onSignOutMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_archive -> {
                    fragment = ArchiveFragment()
                    replaceFragment(fragment)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_delete -> {
                    fragment = TrashFragment()
                    replaceFragment(fragment)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_label -> {
                    fragment = LabelFragment()
                    replaceFragment(fragment)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_reminder -> {
                    fragment = ReminderFragment()
                    replaceFragment(fragment)
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment?) {
        drawerLayout.closeDrawer(navigationView)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment!!)
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
        removePreference()
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

    /**Function to replace home dash board with AddNoteFragment*/
    private fun replaceAddNoteFragment(bundle: Bundle?) {
        val fragment = AddNoteFragment()
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
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