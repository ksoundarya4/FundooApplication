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
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.view.LabelFragment
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class HomeDashBoardActivity : AppCompatActivity() {

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
    private lateinit var sharedEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)
        setSupportActionBar(toolbar)

        initHomeDashBoardActivity()
        callAddNoteFragment()
        setNavigationItemClicked()
    }

    private fun initHomeDashBoardActivity() {
        getUserSharedPreferences()
        setNoteFragment()
        setActionBarToggle()
    }

    private fun setNoteFragment() {
        navigationView.setCheckedItem(R.id.nav_home)
        replaceAddNoteFragment()
    }

    private fun getUserSharedPreferences() {
        val editor = preferences.edit()
        val email = preferences.getString("EMAIL", "emailId")
        sharedEmail = email!!
        editor.apply()
    }

    private fun callAddNoteFragment() {
        setFloatingActionBarClicked()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_dash_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_user_info -> {
                toast(getString(R.string.tast_when_user_profile_clicked))
                true
            }
            R.id.app_bar_search_note -> {
                val searchView = SearchView(this)
                searchView.setOnQueryTextListener(searchQueryListener)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    }

    override fun onBackPressed() {
        tellFragments()
        replaceNoteFragment()
        super.onBackPressed()
    }

    /**Function to set Floating Action Bar when it is clicked*/
    private fun setFloatingActionBarClicked() {
        floatingActionButton.setOnClickListener {
            replaceAddNoteFragment()
        }
    }

    /**Function to set Navigation Items when they are clicked*/
    private fun setNavigationItemClicked() {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    onNoteMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sing_out -> {
                    onSignOutMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_archive -> {
                    onArchiveMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_delete -> {
                    onDeleteMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_label -> {
                    nLabelMenuClick()
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    private fun nLabelMenuClick() {
        drawerLayout.closeDrawer(navigationView)
        reolaceLabelFragment()
    }

    private fun reolaceLabelFragment() {
        val labelFragment = LabelFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, labelFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onDeleteMenuClick() {
        drawerLayout.closeDrawer(navigationView)
        replaceTrashFragment()
        toast(getString(R.string.toast_when_Delete_menu_clicked))
    }

    private fun replaceTrashFragment() {
        val trashFragment = TrashFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, trashFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onArchiveMenuClick() {
        drawerLayout.closeDrawer(navigationView)
        replaceArchiveFragment()
        toast(getString(R.string.toast_archive_notes_ckick))
    }

    /**Function to replace home dash board with AddNoteFragment*/
    private fun replaceArchiveFragment() {
        val archiveFragment = ArchiveFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, archiveFragment)
        transaction.addToBackStack(null)
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

    /**Function to replace homeDashBoard with
    Note Fragment when note menu item clicked*/
    private fun onNoteMenuClick() {
        drawerLayout.closeDrawer(navigationView)
        replaceNoteFragment()
        toast(getString(R.string.toast_when_note_menu_tapped))
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
    private fun tellFragments() {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is OnBackPressed)
                fragment.onBackPressed()
        }
    }

    /**Function to replace home dash board with AddNoteFragment*/
    private fun replaceAddNoteFragment() {
        val fragment = AddNoteFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**Function to replace home dash board with noteFragment*/
    private fun replaceNoteFragment() {
        val fragment = NoteFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    /**Function to navigate to LoginActivity*/
    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}