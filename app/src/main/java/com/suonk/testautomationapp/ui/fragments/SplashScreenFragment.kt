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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private var binding: FragmentSplashScreenBinding? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun appLogoAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.suddenly_appear)
        val frameAnimation = binding?.appLogo?.drawable as AnimationDrawable
        frameAnimation.start()
        binding?.appName?.startAnimation(animation)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)
            navController.navigate(R.id.action_splashScreenFragment_to_mainFragment)
            frameAnimation.stop()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        appLogoAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.root
    }
}