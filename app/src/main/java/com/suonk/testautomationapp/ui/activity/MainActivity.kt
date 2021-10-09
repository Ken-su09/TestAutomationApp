package com.suonk.testautomationapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}