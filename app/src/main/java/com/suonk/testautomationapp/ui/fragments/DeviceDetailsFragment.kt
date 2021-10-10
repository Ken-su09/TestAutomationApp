package com.suonk.testautomationapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentDeviceDetailsBinding
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.viewmodels.AutomationViewModel

class DeviceDetailsFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private val viewModel: AutomationViewModel by activityViewModels()
    private var binding: FragmentDeviceDetailsBinding? = null

    private lateinit var sharedPreferences: SharedPreferences

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    private fun initializeUI() {
        binding?.intensityLight?.min = 0
        binding?.intensityLight?.max = 100
        getDeviceFromViewModel()

//        binding?.intensityLight?.setOnScrollChangeListener(object : View)
    }

    private fun getDeviceFromViewModel() {
        viewModel.apply {
            productType.observe(viewLifecycleOwner, { productType ->
                when (productType) {
                    "Light" -> {
                        light.observe(viewLifecycleOwner, { light ->
                            if (light.mode == "ON") {
                                binding?.deviceIcon?.setImageDrawable(
                                    ResourcesCompat.getDrawable(
                                        (activity as MainActivity).resources,
                                        R.drawable.ic_light,
                                        null
                                    )
                                )
                                binding?.deviceSwitch?.isChecked = true
                            } else {
                                binding?.deviceIcon?.setImageDrawable(
                                    ResourcesCompat.getDrawable(
                                        (activity as MainActivity).resources,
                                        R.drawable.ic_light_off,
                                        null
                                    )
                                )
                                binding?.deviceSwitch?.isChecked = false
                            }
                            binding?.intensityLight?.progress = light.intensity
                            binding?.intensityLightValue?.text =
                                "${binding?.intensityLight?.progress}"
                        })
                    }
                    "RollerShutter" -> {
                        rollerShutter.observe(viewLifecycleOwner, { rollerShutter ->
                            binding?.deviceIcon?.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    (activity as MainActivity).resources,
                                    R.drawable.ic_roller_shutter,
                                    null
                                )
                            )
                        })
                    }
                    "Heater" -> {
                        heater.observe(viewLifecycleOwner, { heater ->
                            if (heater.mode == "ON") {
                                binding?.deviceIcon?.setImageDrawable(
                                    ResourcesCompat.getDrawable(
                                        (activity as MainActivity).resources,
                                        R.drawable.ic_heater,
                                        null
                                    )
                                )
                                binding?.deviceSwitch?.isChecked = true
                            } else {
                                binding?.deviceIcon?.setImageDrawable(
                                    ResourcesCompat.getDrawable(
                                        (activity as MainActivity).resources,
                                        R.drawable.ic_heater,
                                        null
                                    )
                                )
                                binding?.deviceSwitch?.isChecked = false
                            }
                        })
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}