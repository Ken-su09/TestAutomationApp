package com.suonk.testautomationapp.ui.fragments

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
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

    private var productT = ""

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        getDeviceFromViewModel()
        deviceModeSwitchListener()
        deviceSeekBarChangeListener()
    }

    //endregion

    //region =========================================== Listeners ==========================================

    private fun deviceModeSwitchListener() {
        binding?.deviceMode?.setOnCheckedChangeListener { _, isChecked ->
            when (productT) {
                "Light" -> {
                    if (isChecked) {
                        binding?.deviceIcon?.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                (activity as MainActivity).resources,
                                R.drawable.ic_light,
                                null
                            )
                        )
                    } else {
                        binding?.deviceIcon?.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                (activity as MainActivity).resources,
                                R.drawable.ic_light_off,
                                null
                            )
                        )
                    }
                }
                "Heater" -> {
                    if (isChecked) {
                        binding?.apply {
                            if (dataDeviceSeekBar.progress.toDouble() != 0.0) {
                                checkSeekBarTemperatureProgression(dataDeviceSeekBar.progress.toDouble() / 2.0)
                            }
                            Log.i(
                                "progressHeaterSeekBar",
                                "SeekBar Progress Double : ${dataDeviceSeekBar.progress.toDouble()}"
                            )
                        }
                    } else {
                        binding?.deviceIcon?.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                (activity as MainActivity).resources,
                                R.drawable.ic_heater_off,
                                null
                            )
                        )
                    }
                }
            }
        }
    }

    private fun deviceSeekBarChangeListener() {
        binding?.dataDeviceSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    when (productT) {
                        "Light" -> {
                            binding?.intensityTemperatureDeviceValue?.text =
                                getString(R.string.intensity_device) +
                                        seekBar?.progress
                        }
                        "Heater" -> {
                            binding?.apply {
                                val progressHeaterSeekBar =
                                    dataDeviceSeekBar.progress.toDouble().div(2.0)
                                Log.i(
                                    "progressHeaterSeekBar",
                                    "SeekBar Progress Double : $progressHeaterSeekBar"
                                )

                                if (deviceMode.isChecked) {
                                    when {
                                        progressHeaterSeekBar < 14.0 -> {
                                            deviceIcon.setImageDrawable(
                                                ResourcesCompat.getDrawable(
                                                    (activity as MainActivity).resources,
                                                    R.drawable.ic_heater_cold,
                                                    null
                                                )
                                            )
                                        }
                                        progressHeaterSeekBar >= 14.0 && progressHeaterSeekBar < 22.0 -> {
                                            deviceIcon.setImageDrawable(
                                                ResourcesCompat.getDrawable(
                                                    (activity as MainActivity).resources,
                                                    R.drawable.ic_heater,
                                                    null
                                                )
                                            )
                                        }
                                        else -> {
                                            deviceIcon.setImageDrawable(
                                                ResourcesCompat.getDrawable(
                                                    (activity as MainActivity).resources,
                                                    R.drawable.ic_heater_hot,
                                                    null
                                                )
                                            )
                                        }
                                    }
                                }

                                intensityTemperatureDeviceValue.text =
                                    getString(R.string.temperature_device) +
                                            progressHeaterSeekBar + "°C"
                            }
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun saveDeviceClick() {

    }

    //endregion

    private fun checkSeekBarTemperatureProgression(temperature: Double) {
        binding?.apply {
            when {
                temperature < 14.0 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_heater_cold,
                            null
                        )
                    )
                }
                temperature >= 14.0 && temperature < 22.0 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_heater,
                            null
                        )
                    )
                }
                else -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_heater_hot,
                            null
                        )
                    )
                }
            }
        }
    }

    //region ==================================== getDeviceFromViewModel ====================================

    private fun getDeviceFromViewModel() {
        viewModel.apply {
            productType.observe(viewLifecycleOwner, { productType ->
                productT = productType
                when (productType) {
                    "Light" -> {
                        light.observe(viewLifecycleOwner, { light ->
                            binding?.apply {
                                if (light.mode == "ON") {
                                    deviceIcon.setImageDrawable(
                                        ResourcesCompat.getDrawable(
                                            (activity as MainActivity).resources,
                                            R.drawable.ic_light,
                                            null
                                        )
                                    )
                                    deviceMode.isChecked = true
                                } else {
                                    deviceIcon.setImageDrawable(
                                        ResourcesCompat.getDrawable(
                                            (activity as MainActivity).resources,
                                            R.drawable.ic_light_off,
                                            null
                                        )
                                    )
                                    deviceMode.isChecked = false
                                }

                                dataDeviceSeekBar.apply {
                                    progress = light.intensity
                                    min = 0
                                    max = 100
                                    incrementProgressBy(1)
                                }
                                intensityTemperatureDeviceValue.text =
                                    getString(R.string.intensity_device) + light.intensity
                            }
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

                            binding?.deviceMode?.isVisible = false
                        })
                    }
                    "Heater" -> {
                        heater.observe(viewLifecycleOwner, { heater ->
                            binding?.apply {
                                if (heater.mode == "ON") {
                                    checkSeekBarTemperatureProgression(heater.temperature.toDouble())
                                    deviceMode.isChecked = true
                                } else {
                                    checkSeekBarTemperatureProgression(heater.temperature.toDouble())
                                    deviceIcon.setImageDrawable(
                                        ResourcesCompat.getDrawable(
                                            (activity as MainActivity).resources,
                                            R.drawable.ic_heater_off,
                                            null
                                        )
                                    )
                                    deviceMode.isChecked = false
                                }

                                intensityTemperatureDeviceValue.text =
                                    getString(R.string.temperature_device) +
                                            heater.temperature.toDouble() + "°C"

                                dataDeviceSeekBar.apply {
                                    progress = heater.temperature * 2
                                    min = 14
                                    max = 56
                                    incrementProgressBy(1)
                                }
                            }
                        })
                    }
                }
            })
        }
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}