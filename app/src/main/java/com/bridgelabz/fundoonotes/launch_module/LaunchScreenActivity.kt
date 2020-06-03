package com.bridgelabz.fundoonotes.launch_module

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.view.LoginActivity

const val SPLASH_TIME = 2000L

class LaunchScreenActivity : AppCompatActivity() {

    private val handler by lazy { Handler() }

    private val runnable = Runnable {
        if (!isFinishing) {

            val sharedPreference = FundooNotesPreference.getPreference(this)
            if (sharedPreference.contains("email")) {
                val intent = Intent(this, HomeDashBoardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        handler.postDelayed(
            runnable
            , SPLASH_TIME
        )
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}