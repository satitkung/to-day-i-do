package com.example.todayido.splashScreen.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import com.example.todayido.R
import com.example.todayido.baseAndUtils.BaseActivity
import com.example.todayido.home.MainActivity
import com.example.todayido.splashScreen.viewModel.SplashScreenViewModel
import kotlinx.android.synthetic.main.activity_spash_screen.*

class SplashScreenActivity : BaseActivity() {

    private lateinit var splashScreenViewModel: SplashScreenViewModel
    private lateinit var fadeInFadeOut: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
        initViewModel()
    }

    override fun initViewModel() {
        splashScreenViewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        initAnimation()
        setupView()
    }

    private fun initAnimation() {
        fadeInFadeOut =  AnimationUtils.loadAnimation(this, R.anim.fade_in_fade_out)
        fadeInFadeOut.setAnimationListener(object : Animation.AnimationListener{
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

    private fun setupView() {
        logo.animation = fadeInFadeOut
        logo.animate()
    }

}

