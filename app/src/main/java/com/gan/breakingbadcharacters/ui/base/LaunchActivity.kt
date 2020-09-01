package com.gan.breakingbadcharacters.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gan.breakingbadcharacters.R

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }
}