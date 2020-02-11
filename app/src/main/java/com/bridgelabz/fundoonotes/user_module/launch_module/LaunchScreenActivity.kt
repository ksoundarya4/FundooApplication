package com.bridgelabz.fundoonotes.user_module.launch_module

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity

const val SPLASH_TIME = 3000L

class LaunchScreenActivity : AppCompatActivity() {

    private val handler by lazy { Handler() }
    private lateinit var sharedPreference: SharedPreferences
    private var firstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        sharedPreference = getSharedPreferences("LaunchScreen", Context.MODE_PRIVATE)
        firstTime = sharedPreference.getBoolean("firstTime", true)

        if (firstTime) {
            handler.postDelayed({

                val sharedPreferenceEditor = sharedPreference.edit()
                firstTime = false
                sharedPreferenceEditor.putBoolean("firstTime", firstTime)
                sharedPreferenceEditor.apply()

                Intent(this, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }, SPLASH_TIME)

        } else {
            Intent(this, HomeDashBoardActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}