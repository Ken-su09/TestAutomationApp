package com.suonk.testautomationapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.ActivityMainBinding
import com.suonk.testautomationapp.navigation.Coordinator
import com.suonk.testautomationapp.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var navigator: Navigator
    private lateinit var coordinator: Coordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navigator.activity = this
        coordinator = Coordinator(navigator)

        startSplashScreen()
    }

    //region ======================================= Start Fragments ========================================

    private fun startSplashScreen() {
        coordinator.showSplashScreen()
//        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
    }

    fun startMainScreen() {
        coordinator.showMainScreen()
    }

    fun startDeviceDetails() {
        coordinator.showDeviceDetails()
    }

    fun startEditUserDetails() {
        coordinator.showEditUserDetails()
    }

    //endregion
}