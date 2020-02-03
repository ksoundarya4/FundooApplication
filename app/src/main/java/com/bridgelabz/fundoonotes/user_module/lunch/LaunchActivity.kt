/**
 * Fundoo Notes
 * @description Launch Activity that appears for 4s soon
 * as application icon is clicked.
 * @file LaunchActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.lunch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity

const val SPLASH_TIME = 4000

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val handler = Handler().postDelayed(Runnable() {
            @Override
            fun run() {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }, SPLASH_TIME.toLong())
    }
}

