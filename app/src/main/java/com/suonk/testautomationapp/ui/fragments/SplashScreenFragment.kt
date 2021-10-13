package com.suonk.testautomationapp.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentSplashScreenBinding
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.fragments.main_pages.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private var binding: FragmentSplashScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        appLogoAnimation()
        return binding?.root
    }

    private fun appLogoAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.suddenly_appear)
        val frameAnimation = binding?.appLogo?.drawable as AnimationDrawable
        frameAnimation.start()
        binding?.appName?.startAnimation(animation)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2750)
            (activity as MainActivity)?.startMainScreen()
            frameAnimation.stop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.root
    }
}