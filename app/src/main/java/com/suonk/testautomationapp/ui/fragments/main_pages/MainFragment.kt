package com.suonk.testautomationapp.ui.fragments.main_pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentMainBinding
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.adapters.ViewPagerAdapter

class MainFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentMainBinding? = null

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding?.viewPager?.adapter = ViewPagerAdapter(activity as MainActivity)

        TabLayoutMediator(
            binding?.tabLayout!!,
            binding?.viewPager!!
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_house_automation, null)
                }
                1 -> {
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)
                }
            }
        }.attach()
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}