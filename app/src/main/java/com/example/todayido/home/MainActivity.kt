package com.example.todayido.home

import android.os.Bundle
import com.example.todayido.R
import com.example.todayido.baseAndUtils.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initViewModel() {

    }
}
