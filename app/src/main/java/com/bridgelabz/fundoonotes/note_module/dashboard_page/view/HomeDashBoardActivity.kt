/**
 * Fundoo Notes
 * @description HomeDashBoardActivity displays all user actions.
 * @file HomeDashBoardActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


@Suppress("DEPRECATION")
class HomeDashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        actionBarDrawerToggle.syncState()

        navigateToHome()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            navigateToAddNoteFragment()
        }

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(navigationView)
                    navigateToHome()
                    toast("Home Tapped")
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sing_out -> {
                    signOutAlertDialog()
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    private fun signOutAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setMessage("Do you want to sign out ?")
        alertDialogBuilder.setTitle("ALERT!")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                navigateToLoginScreen()
                toast(
                    "Signing Out"
                )
            }
        alertDialogBuilder.setNegativeButton("NO")
        { dialog, _ ->
            dialog.cancel()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_dash_board, menu)

        val item: MenuItem = menu.getItem(0)

        if (item.itemId == R.id.app_bar_user_info) {
            item.icon = resources.getDrawable(R.drawable.download)
//                getDrawable(R.drawable.download)
//            var imageView = ImageView(this)
//            imageView.setImageResource(R.drawable.download)
//            imageView.maxWidth = 10
//            imageView.maxHeight = 10
//            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
////            imageView.setPadding(50, 0, 50, 0)
//            item.actionView = imageView
//
//            imageView.setOnClickListener {
//                Toast.makeText(this, "Clicked ImageView", Toast.LENGTH_SHORT).show()
//            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_user_info -> {
                toast("Clicked User Info")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAddNoteFragment() {
        val fragment = AddNoteFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun navigateToHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

//    private fun loadFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment)
//        transaction.commit()
//    }

    private fun navigateToLoginScreen() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }
}