package com.example.todayido.splashScreen.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.todayido.R
import com.example.todayido.base.BaseActivity
import com.example.todayido.home.MainActivity
import kotlinx.android.synthetic.main.activity_spash_screen.*

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        setupView()
    }

    private fun setupView() {

        val fadeInfadeOut =  AnimationUtils.loadAnimation(this, R.anim.fade_in_fade_out)
        logo.animation = fadeInfadeOut
        logo.animate()
        fadeInfadeOut.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                logo.visibility = View.GONE
                Intent(this@SplashScreenActivity, MainActivity::class.java).run {
                    startActivity(this)
                    finish()
                }
            }
        })
    }
}

