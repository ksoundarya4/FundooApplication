package com.bridgelabz.fundoonotes.user_module.lunch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity

const val SPLASH_TIME = 3000L

class LaunchScreenActivity : AppCompatActivity() {

    private val handler by lazy { Handler() }
    private val launchImage by lazy {
        findViewById<ImageView>(R.id.image_launcher_icon)
    }
    private val rotateAnimation by lazy {
        AnimationUtils.loadAnimation(baseContext, R.anim.rotate)
    }
    private val antiRotateAnimation by lazy {
        AnimationUtils.loadAnimation(baseContext, R.anim.anti_rotate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

//        handler.postDelayed({
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, SPLASH_TIME)
        intent = Intent(this, LoginActivity::class.java)

        launchImage.startAnimation(antiRotateAnimation)
        antiRotateAnimation.setAnimationListener(setRotateAnimation)
        rotateAnimation.setAnimationListener(setAntiRotateAnimation)
    }

    private val setRotateAnimation = object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            launchImage.startAnimation(rotateAnimation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationStart(animation: Animation?) {
        }
    }
    private val setAntiRotateAnimation = object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            launchImage.startAnimation(antiRotateAnimation)
            startActivity(intent)
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationStart(animation: Animation?) {
        }
    }
}
