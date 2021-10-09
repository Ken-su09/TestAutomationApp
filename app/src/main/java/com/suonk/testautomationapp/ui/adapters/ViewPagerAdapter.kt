package com.suonk.testautomationapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suonk.testautomationapp.ui.fragments.main_pages.AllDevicesFragment
import com.suonk.testautomationapp.ui.fragments.main_pages.UserFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllDevicesFragment()
            }
            1 -> {
                UserFragment()
            }
            else -> {
                AllDevicesFragment()
            }
        }
    }
}