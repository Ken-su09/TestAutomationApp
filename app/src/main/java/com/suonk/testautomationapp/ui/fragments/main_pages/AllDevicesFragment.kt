package com.suonk.testautomationapp.ui.fragments.main_pages

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentAllDevicesBinding
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.adapters.DevicesListAdapter
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class AllDevicesFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private lateinit var devicesListAdapter: DevicesListAdapter
    private val viewModel: AutomationViewModel by activityViewModels()
    private var binding: FragmentAllDevicesBinding? = null
    private var listOfDevices = mutableListOf<Device>()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesFirstTime: SharedPreferences

    private val listOfOnlyDevices = mutableListOf<Device>()

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDevicesBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        devicesListAdapter = DevicesListAdapter(activity as MainActivity, this, false)

        sharedPreferences = (activity as MainActivity).getSharedPreferences(
            "devices_sort_by",
            Context.MODE_PRIVATE
        )

        sharedPreferencesFirstTime = (activity as MainActivity).getSharedPreferences(
            "isDatabasePrepopulate",
            Context.MODE_PRIVATE
        )

        initRecyclerView()
        initToolbar()
        deleteToolbarClick()
    }

    private fun initRecyclerView() {
        binding?.deviceRecyclerView?.apply {
            this.adapter = devicesListAdapter
            getDevicesListFromDatabase(1500)
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity as MainActivity, 2)
        }
    }

    private fun initToolbar() {
        getDevicesListFilterText()

        binding?.toolbar?.inflateMenu(R.menu.devices_toolbar_menu)
        binding?.toolbar?.overflowIcon = ResourcesCompat.getDrawable(
            (activity as MainActivity).resources,
            R.drawable.ic_sort,
            null
        )
        binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_by_product_type -> {
                    getDevicesFilterByProductType("productType")
                    menuItem.isChecked = true
                    true
                }
                R.id.sort_by_device_name -> {
                    getDevicesFilterByProductType("deviceName")
                    menuItem.isChecked = true
                    true
                }
                R.id.only_light -> {
                    getOnlyDevicesFilterByProductType("Light")
                    menuItem.isChecked = true
                    true
                }
                R.id.only_heater -> {
                    getOnlyDevicesFilterByProductType("Heater")
                    menuItem.isChecked = true
                    true
                }
                R.id.only_roller_shutter -> {
                    getOnlyDevicesFilterByProductType("RollerShutter")
                    menuItem.isChecked = true
                    true
                }
                R.id.delete_mode -> {
                    binding?.toolbar?.visibility = View.INVISIBLE
                    binding?.deleteToolbar?.isVisible = true
                    getDevicesListDeleteMode(true)
                    true
                }
                else -> {
                    super.onOptionsItemSelected(menuItem)
                    true
                }
            }
        }
    }

    //endregion

    fun navigateToDeviceDetails(position: Int) {
        if (listOfOnlyDevices.isEmpty()) {
            viewModel.setDevice(listOfDevices[position])
            (activity as MainActivity).startDeviceDetails()
        } else {
            viewModel.setDevice(listOfOnlyDevices[position])
            (activity as MainActivity).startDeviceDetails()
        }
    }

    //region ======================================= Delete Device(s) =======================================

    private fun deleteToolbarClick() {
        binding?.deleteToolbarCancel?.setOnClickListener {
            binding?.toolbar?.visibility = View.VISIBLE
            binding?.deleteToolbar?.visibility = View.GONE
            getDevicesListDeleteMode(false)
        }
    }

    fun deleteDevice(position: Int) {
        val device = listOfDevices[position]

        MaterialAlertDialogBuilder(activity as MainActivity, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.delete_device_title) + " : " + device.deviceName)
            .setMessage(getString(R.string.alert_dialog_message))
            .setPositiveButton(getString(R.string.alert_dialog_positive_button)) { _, _ ->
                viewModel.deleteDevice(device)
                listOfDevices.clear()
                devicesListAdapter.submitList(null)

                getDevicesListDeleteMode(true)
            }
            .setNegativeButton(getString(R.string.alert_dialog_negative_button)) { dialogInterface, _ ->
                dialogInterface.cancel()
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun getDevicesListDeleteMode(deleteMode: Boolean) {
        devicesListAdapter = DevicesListAdapter(activity as MainActivity, this, deleteMode)
        binding?.deviceRecyclerView?.apply {
            this.adapter = devicesListAdapter
            getDevicesListFromDatabase(2000)
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity as MainActivity, 2)
        }
    }

    // endregion

    //region ========================================== getDevices ==========================================

    private fun getDevicesListFilterText() {
        binding?.searchDeviceEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(searchBarText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (searchBarText != "" || searchBarText.isEmpty()) {
                    val listOfContactsFilter = mutableListOf<Device>()
                    for (i in listOfDevices.indices) {
                        if (listOfDevices[i].deviceName.contains(searchBarText!!) ||
                            listOfDevices[i].productType.contains(searchBarText) ||
                            listOfDevices[i].deviceName.contains(
                                searchBarText.toString().lowercase()
                            ) ||
                            listOfDevices[i].productType.contains(
                                searchBarText.toString().lowercase()
                            ) ||
                            listOfDevices[i].deviceName.contains(
                                searchBarText.toString().uppercase()
                            ) ||
                            listOfDevices[i].productType.contains(
                                searchBarText.toString().uppercase()
                            )
                        ) {
                            listOfContactsFilter.add(listOfDevices[i])
                        }
                    }
                    devicesListAdapter.submitList(listOfContactsFilter)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun getOnlyDevicesFilterByProductType(productType: String) {
        CoroutineScope(Dispatchers.Main).launch {
            devicesListAdapter.submitList(null)
            listOfOnlyDevices.clear()
            binding?.searchDeviceEditText?.text?.clear()
            launchProgressBarSpin(1500)
            for (i in listOfDevices.indices) {
                if (listOfDevices[i].productType == productType) {
                    listOfOnlyDevices.add(listOfDevices[i])
                }
            }
            listOfOnlyDevices.sortBy { device -> device.deviceName }
            devicesListAdapter.submitList(listOfOnlyDevices)
        }
    }

    private fun getDevicesFilterByProductType(filterBy: String) {
        CoroutineScope(Dispatchers.Main).launch {
            devicesListAdapter.submitList(null)
            listOfOnlyDevices.clear()
            binding?.searchDeviceEditText?.text?.clear()
            launchProgressBarSpin(1500)
            if (filterBy == "deviceName") {
                listOfDevices.sortBy { device -> device.deviceName }
            } else {
                listOfDevices.sortBy { device -> device.productType }
            }
            devicesListAdapter.submitList(listOfDevices)
            val edit = sharedPreferences.edit()
            edit.putString("devices_sort_by", filterBy)
            edit.apply()
        }
    }

    private fun getDevicesListFromDatabase(time: Long) {
        viewModel.apply {
            allDevices.observe(viewLifecycleOwner, { devices ->
                CoroutineScope(Dispatchers.Main).launch {
                    listOfDevices.clear()
                    binding?.deviceRecyclerView?.isVisible = false
                    launchProgressBarSpin(time)

                    listOfDevices.addAll(devices)

                    Log.i("isListEmpty", "devices : ${devices.size}")
                    Log.i("isListEmpty", "listOfDevices : ${listOfDevices.size}")

                    if (listOfDevices.isEmpty() && devices.isEmpty()) {
                        Log.i("isListEmpty", "True")
                        for (i in 1..12) {
                            viewModel.getDeviceById(i).observe(viewLifecycleOwner, { device ->
                                if (device != null) {
                                    listOfDevices.add(device)

                                    when (sharedPreferences.getString(
                                        "devices_sort_by", "productType"
                                    )) {
                                        "productType" -> {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                launchProgressBarSpin(1500)
                                                binding?.deviceRecyclerView?.isVisible = true
                                                devicesListAdapter.submitList(null)
                                                listOfDevices.sortBy { deviceSort -> deviceSort.productType }
                                                binding?.toolbar?.menu?.getItem(1)?.isChecked = true
                                                devicesListAdapter.submitList(listOfDevices)
                                            }
                                        }
                                        "deviceName" -> {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                launchProgressBarSpin(1500)
                                                binding?.deviceRecyclerView?.isVisible = true
                                                devicesListAdapter.submitList(null)
                                                listOfDevices.sortBy { deviceSort -> deviceSort.deviceName }
                                                binding?.toolbar?.menu?.getItem(2)?.isChecked = true
                                                devicesListAdapter.submitList(listOfDevices)
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    } else {
                        Log.i("isListEmpty", "False")
                        when (sharedPreferences.getString("devices_sort_by", "productType")) {
                            "productType" -> {
                                binding?.deviceRecyclerView?.isVisible = true
                                listOfDevices.sortBy { device -> device.productType }
                                binding?.toolbar?.menu?.getItem(1)?.isChecked = true
                                devicesListAdapter.submitList(listOfDevices)
                            }
                            "deviceName" -> {
                                binding?.deviceRecyclerView?.isVisible = true
                                listOfDevices.sortBy { device -> device.deviceName }
                                binding?.toolbar?.menu?.getItem(2)?.isChecked = true
                                devicesListAdapter.submitList(listOfDevices)
                            }
                        }
                    }
                }
            })


//            if (!sharedPreferencesFirstTime.getBoolean("isDatabasePrepopulate", false)) {
//                CoroutineScope(Dispatchers.Main).launch {
//                    listOfDevices.clear()
//                    binding?.deviceRecyclerView?.isVisible = false
//                    launchProgressBarSpin(time)
//
//                    Log.i("isListEmpty", "${listOfDevices.size}")
//
//                    if (listOfDevices.isEmpty() && listOfDevices.size <= 12) {
//                        for (i in 1..12) {
//                            viewModel.getDeviceById(i).observe(viewLifecycleOwner, { device ->
//                                if (device != null) {
//                                    listOfDevices.add(device)
//
//                                    when (sharedPreferences.getString(
//                                        "devices_sort_by", "productType"
//                                    )) {
//                                        "productType" -> {
//                                            CoroutineScope(Dispatchers.Main).launch {
//                                                launchProgressBarSpin(1500)
//                                                binding?.deviceRecyclerView?.isVisible = true
//                                                devicesListAdapter.submitList(null)
//                                                listOfDevices.sortBy { deviceSort -> deviceSort.productType }
//                                                binding?.toolbar?.menu?.getItem(1)?.isChecked = true
//                                                devicesListAdapter.submitList(listOfDevices)
//                                            }
//                                        }
//                                        "deviceName" -> {
//                                            CoroutineScope(Dispatchers.Main).launch {
//                                                binding?.deviceRecyclerView?.isVisible = false
//                                                launchProgressBarSpin(1500)
//                                                binding?.deviceRecyclerView?.isVisible = true
//                                                devicesListAdapter.submitList(null)
//                                                listOfDevices.sortBy { deviceSort -> deviceSort.deviceName }
//                                                binding?.toolbar?.menu?.getItem(2)?.isChecked = true
//                                                devicesListAdapter.submitList(listOfDevices)
//                                            }
//                                        }
//                                    }
//                                }
//                            })
//                        }
//                    }
//                }
//
//                val edit = sharedPreferencesFirstTime.edit()
//                edit.putBoolean("isDatabasePrepopulate", true)
//                edit.apply()
//                Log.i("isDatabasePrepopulate", "true")
//            }
        }
    }

    //endregion

    private suspend fun launchProgressBarSpin(time: Long) {
        binding?.deviceListProgressBar?.isVisible = true
        delay(time)
        binding?.deviceListProgressBar?.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}