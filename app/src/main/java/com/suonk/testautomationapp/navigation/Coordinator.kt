package com.suonk.testautomationapp.navigation

import javax.inject.Inject

class Coordinator @Inject constructor(private val navigator: Navigator) {

    fun showSplashScreen() {
        navigator.showSplashScreen()
    }

    fun showMainScreen() {
        navigator.showMainScreen()
    }

    fun showDeviceDetails() {
        navigator.showDeviceDetails()
    }

    fun showEditUserDetails() {
        navigator.showEditUser()
    }
}