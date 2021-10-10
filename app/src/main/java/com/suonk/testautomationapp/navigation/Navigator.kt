package com.suonk.testautomationapp.navigation

import androidx.fragment.app.FragmentActivity
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.ui.fragments.DeviceDetailsFragment
import com.suonk.testautomationapp.ui.fragments.EditUserDetailsFragment
import com.suonk.testautomationapp.ui.fragments.SplashScreenFragment
import com.suonk.testautomationapp.ui.fragments.main_pages.MainFragment
import javax.inject.Inject

class Navigator @Inject constructor(var activity: FragmentActivity?) {

    fun showSplashScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, SplashScreenFragment())
            .commit()
    }

    fun showMainScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, MainFragment())
            .commit()
    }

    fun showDeviceDetails() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, DeviceDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

    fun showEditUser() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, EditUserDetailsFragment())
            .addToBackStack(null)
            .commit()
    }
}