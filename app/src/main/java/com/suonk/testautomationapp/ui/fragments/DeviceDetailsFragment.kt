package com.suonk.testautomationapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentDeviceDetailsBinding
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.viewmodels.AutomationViewModel

class DeviceDetailsFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private val viewModel: AutomationViewModel by activityViewModels()
    private var binding: FragmentDeviceDetailsBinding? = null

    private var productT = ""

    private lateinit var currentLight: Light
    private lateinit var currentHeater: Heater
    private lateinit var currentRollerShutter: RollerShutter

    private var currentMode = ""
    private var currentPosition = 0
    private var currentId = -1
    private var currentDeviceName = ""
    private var currentTemperature = 0.0

    private var myFromUser = false

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
        rollerShutterSeekBarChangeListener()
        backClick()
        saveDeviceClick()
    }

    //endregion

    //region =========================================== Listeners ==========================================

    private fun deviceModeSwitchListener() {
        binding?.apply {
            deviceMode.setOnCheckedChangeListener { _, isChecked ->
                when (productT) {
                    "Light" -> {
                        if (isChecked) {
                            if (dataDeviceSeekBar.progress != 0) {
                                checkSeekBarIntensityProgression(dataDeviceSeekBar.progress)
                                currentMode = "ON"
                                myFromUser = currentMode != currentLight.mode
                            }
                        } else {
                            deviceIcon.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    (activity as MainActivity).resources,
                                    R.drawable.ic_light_off,
                                    null
                                )
                            )
                            currentMode = "OFF"
                            myFromUser = currentMode != currentLight.mode
                        }
                    }
                    "Heater" -> {
                        if (isChecked) {
                            if (dataDeviceSeekBar.progress.toDouble() != 0.0) {
                                checkSeekBarTemperatureProgression(dataDeviceSeekBar.progress.toDouble() / 2.0)
                                currentMode = "ON"
                                myFromUser = currentMode != currentHeater.mode
                            }
                        } else {
                            deviceIcon.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    (activity as MainActivity).resources,
                                    R.drawable.ic_heater_off,
                                    null
                                )
                            )
                            currentMode = "OFF"
                            myFromUser = currentMode != currentHeater.mode
                        }
                    }
                }
            }
        }
    }

    private fun deviceSeekBarChangeListener() {
        binding?.dataDeviceSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                myFromUser = fromUser
                if (fromUser) {
                    when (productT) {
                        "Light" -> {
                            binding?.apply {
                                if (deviceMode.isChecked) {
                                    checkSeekBarIntensityProgression(progress)
                                }

                                intensityTemperatureDeviceValue.text =
                                    getString(R.string.intensity_device) + seekBar?.progress
                            }
                        }
                        "Heater" -> {
                            binding?.apply {
                                val progressHeaterSeekBar =
                                    dataDeviceSeekBar.progress.toDouble().div(2.0)
                                if (deviceMode.isChecked) {
                                    checkSeekBarTemperatureProgression(progressHeaterSeekBar)
                                }

                                intensityTemperatureDeviceValue.text =
                                    getString(R.string.temperature_device) + progressHeaterSeekBar + "°C"
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

    private fun rollerShutterSeekBarChangeListener() {
        binding?.apply {
            rollerShutterPositionSeekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        checkSeekBarPositionProgression(progress)
                        rollerShutterPositionValue.text =
                            "Position : $progress"
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })
        }
    }

    private fun backClick() {
        binding?.apply {
            back.setOnClickListener {
                if (checkIfDeviceHasBeenEdited()) {
                    MaterialAlertDialogBuilder(activity as MainActivity, R.style.AlertDialogTheme)
                        .setTitle(getString(R.string.alert_dialog_title_back))
                        .setMessage(getString(R.string.alert_dialog_message))
                        .setPositiveButton(getString(R.string.alert_dialog_positive_button)) { _, _ ->
                            activity?.supportFragmentManager?.popBackStack()
                        }
                        .setNegativeButton(getString(R.string.alert_dialog_negative_button)) { dialogInterface, _ ->
                            dialogInterface.cancel()
                            dialogInterface.dismiss()
                        }
                        .show()
                } else {
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        }
    }

    private fun saveDeviceClick() {
        binding?.apply {
            saveDeviceIcon.setOnClickListener {
                if (checkIfDeviceHasBeenEdited()) {
                    MaterialAlertDialogBuilder(activity as MainActivity, R.style.AlertDialogTheme)
                        .setTitle(getString(R.string.alert_dialog_title_save))
                        .setMessage(getString(R.string.alert_dialog_message))
                        .setPositiveButton(getString(R.string.alert_dialog_positive_button)) { _, _ ->
                            viewModel.apply {
                                when (productT) {
                                    "Light" -> {
                                        updateDevice(
                                            Device(
                                                currentDeviceName,
                                                productT,
                                                null,
                                                Light(
                                                    currentMode,
                                                    dataDeviceSeekBar.progress
                                                ),
                                                null,
                                                currentId
                                            )
                                        )
                                    }
                                    "Heater" -> {
                                        updateDevice(
                                            Device(
                                                currentDeviceName,
                                                productT,
                                                Heater(
                                                    currentMode,
                                                    (dataDeviceSeekBar.progress.toDouble() / 2).toInt()
                                                ),
                                                null,
                                                null,
                                                currentId
                                            )
                                        )
                                    }
                                    "RollerShutter" -> {
                                        updateDevice(
                                            Device(
                                                currentDeviceName,
                                                productT,
                                                null,
                                                null,
                                                RollerShutter(
                                                    rollerShutterPositionSeekBar.progress
                                                ),
                                                currentId
                                            )
                                        )
                                    }
                                }
                            }
                            (activity as MainActivity).apply {
                                supportFragmentManager.popBackStack()
                                supportFragmentManager.beginTransaction()
                            }
                        }
                        .setNegativeButton(getString(R.string.alert_dialog_negative_button)) { dialogInterface, _ ->
                            dialogInterface.cancel()
                            dialogInterface.dismiss()
                        }
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.edit_devices_no_changes),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    //endregion

    //region ========================================= checkSeekBar =========================================

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

    private fun checkSeekBarIntensityProgression(intensity: Int) {
        binding?.apply {
            when {
                intensity < 25 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_light_low_intensity,
                            null
                        )
                    )
                }
                intensity in 25..69 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_light,
                            null
                        )
                    )
                }
                else -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_light_bright,
                            null
                        )
                    )
                }
            }
        }
    }

    private fun checkSeekBarPositionProgression(position: Int) {
        binding?.apply {
            when {
                position <= 8 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_0,
                            null
                        )
                    )
                }
                position in 9..20 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_15,
                            null
                        )
                    )
                }
                position in 21..35 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_30,
                            null
                        )
                    )
                }
                position in 36..50 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_40,
                            null
                        )
                    )
                }
                position in 51..60 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_50,
                            null
                        )
                    )
                }
                position in 61..70 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_60,
                            null
                        )
                    )
                }
                position in 71..80 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_70,
                            null
                        )
                    )
                }
                position in 81..92 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter_90,
                            null
                        )
                    )
                }
                position in 93..100 -> {
                    deviceIcon.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            (activity as MainActivity).resources,
                            R.drawable.ic_roller_shutter,
                            null
                        )
                    )
                }
            }
        }
    }

    //endregion

    private fun checkIfDeviceHasBeenEdited(): Boolean {
        return when (productT) {
            "Light" -> {
                if (!myFromUser) {
                    myFromUser
                } else {
                    currentLight.mode != currentMode ||
                            currentLight.intensity != binding?.dataDeviceSeekBar?.progress
                }
            }
            "Heater" -> {
                if (!myFromUser) {
                    myFromUser
                } else {
                    currentHeater.mode != currentMode ||
                            currentTemperature != binding?.dataDeviceSeekBar?.progress?.toDouble()!! / 2
                }
            }
            "RollerShutter" -> {
                binding?.rollerShutterPositionSeekBar?.progress != currentPosition
            }
            else -> {
                false
            }
        }
    }

    //region ==================================== getDeviceFromViewModel ====================================

    private fun getDeviceFromViewModel() {
        viewModel.apply {
            device.observe(viewLifecycleOwner, { device ->
                productT = device.productType
                currentId = device.id
                currentDeviceName = device.deviceName
                binding?.deviceName?.text = currentDeviceName

                when (productT) {
                    "Light" -> {
                        currentLight = device.light!!
                        currentMode = currentLight.mode

                        binding?.apply {
                            if (currentLight.mode == "ON") {
                                checkSeekBarIntensityProgression(currentLight.intensity)
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
                                progress = currentLight.intensity
                                min = 0
                                max = 100
                                incrementProgressBy(1)
                            }
                            intensityTemperatureDeviceValue.text =
                                getString(R.string.intensity_device) + currentLight.intensity
                        }
                    }
                    "RollerShutter" -> {
                        currentRollerShutter = device.rollerShutter!!
                        currentPosition = currentRollerShutter.position

                        binding?.apply {
                            deviceIcon.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    (activity as MainActivity).resources,
                                    R.drawable.ic_roller_shutter,
                                    null
                                )
                            )

                            deviceMode.isVisible = false
                            deviceModeTitle.isVisible = false
                            dataDeviceSeekBar.isVisible = false

                            rollerShutterPositionSeekBar.apply {
                                isVisible = true
                                min = 0
                                max = 100
                                incrementProgressBy(1)
                                progress = currentRollerShutter.position
                            }

                            checkSeekBarPositionProgression(currentRollerShutter.position)

                            rollerShutterPositionValue.text =
                                "Position : ${currentRollerShutter.position}"
                        }
                    }
                    "Heater" -> {
                        currentHeater = device.heater!!
                        currentMode = currentHeater.mode
                        currentTemperature = currentHeater.temperature.toDouble()

                        binding?.apply {
                            if (currentHeater.mode == "ON") {
                                checkSeekBarTemperatureProgression(currentHeater.temperature.toDouble())
                                deviceMode.isChecked = true
                            } else {
                                checkSeekBarTemperatureProgression(currentHeater.temperature.toDouble())
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
                                getString(R.string.temperature_device) + currentTemperature + "°C"

                            dataDeviceSeekBar.apply {
                                progress = currentHeater.temperature * 2
                                min = 14
                                max = 56
                                incrementProgressBy(1)
                            }
                        }
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