package com.bridgelabz.fundoonotes.user_module.lunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity

const val SPLASH_TIME = 2000

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
        }, SPLASH_TIME.toLong());
    }
}

