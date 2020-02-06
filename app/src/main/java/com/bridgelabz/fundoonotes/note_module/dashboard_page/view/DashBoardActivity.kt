package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.google.android.material.navigation.NavigationView

class DashBoardActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}
