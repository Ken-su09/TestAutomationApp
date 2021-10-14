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
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentAllDevicesBinding
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.models.data.Heater
import com.suonk.testautomationapp.models.data.Light
import com.suonk.testautomationapp.models.data.RollerShutter
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

        initRecyclerView()
        initToolbar()
        deleteToolbarClick()
    }

    private fun initRecyclerView() {
        binding?.deviceRecyclerView?.apply {
            this.adapter = devicesListAdapter
            getDevicesListFromDatabase()
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity as MainActivity, 2)
        }
    }

    private fun initToolbar() {
        getDeviceListFilterText()

        binding?.toolbar?.inflateMenu(R.menu.devices_toolbar_menu)
        binding?.toolbar?.overflowIcon = ResourcesCompat.getDrawable(
            (activity as MainActivity).resources,
            R.drawable.ic_sort,
            null
        )
        binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_by_product_type -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        devicesListAdapter.submitList(null)
                        delay(1500)
                        listOfDevices.sortBy { device -> device.productType }
                        devicesListAdapter.submitList(listOfDevices)
                    }
                    val edit = sharedPreferences.edit()
                    edit.putString("devices_sort_by", "productType")
                    edit.apply()
                    menuItem.isChecked = true
                    true
                }
                R.id.sort_by_device_name -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        devicesListAdapter.submitList(null)
                        delay(1500)
                        listOfDevices.sortBy { device -> device.deviceName }
                        devicesListAdapter.submitList(listOfDevices)
                    }
                    val edit = sharedPreferences.edit()
                    edit.putString("devices_sort_by", "deviceName")
                    edit.apply()
                    menuItem.isChecked = true
                    true
                }
                R.id.only_light -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        devicesListAdapter.submitList(null)
                        delay(1500)
                        val listOfLights = mutableListOf<Device>()
                        for (i in listOfDevices.indices) {
                            if (listOfDevices[i].productType == "Light") {
                                listOfLights.add(listOfDevices[i])
                            }
                        }
                        listOfLights.sortBy { device -> device.deviceName }
                        devicesListAdapter.submitList(listOfLights)
                    }
                    val edit = sharedPreferences.edit()
                    edit.putString("devices_sort_by", "only_light")
                    edit.apply()
                    menuItem.isChecked = true
                    true
                }
                R.id.only_heater -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        devicesListAdapter.submitList(null)
                        delay(1500)
                        val listOfLights = mutableListOf<Device>()
                        for (i in listOfDevices.indices) {
                            if (listOfDevices[i].productType == "Heater") {
                                listOfLights.add(listOfDevices[i])
                            }
                        }
                        listOfLights.sortBy { device -> device.deviceName }
                        devicesListAdapter.submitList(listOfLights)
                    }
                    val edit = sharedPreferences.edit()
                    edit.putString("devices_sort_by", "only_heater")
                    edit.apply()
                    menuItem.isChecked = true
                    true
                }
                R.id.only_roller_shutter -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        devicesListAdapter.submitList(null)
                        delay(1500)
                        val listOfLights = mutableListOf<Device>()
                        for (i in listOfDevices.indices) {
                            if (listOfDevices[i].productType == "RollerShutter") {
                                listOfLights.add(listOfDevices[i])
                            }
                        }
                        listOfLights.sortBy { device -> device.deviceName }
                        devicesListAdapter.submitList(listOfLights)
                    }
                    val edit = sharedPreferences.edit()
                    edit.putString("devices_sort_by", "only_roller_shutter")
                    edit.apply()
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

    private fun deleteToolbarClick() {
        binding?.deleteToolbarCancel?.setOnClickListener {
            binding?.toolbar?.visibility = View.VISIBLE
            binding?.deleteToolbar?.visibility = View.GONE
            getDevicesListDeleteMode(false)
        }
    }

    //endregion

    fun navigateToDeviceDetails(position: Int) {
        viewModel.setDevice(listOfDevices[position])
        (activity as MainActivity).startDeviceDetails()
    }

    fun deleteDevice(position: Int) {
        val device = listOfDevices[position]

        MaterialAlertDialogBuilder(activity as MainActivity, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.delete_device_title))
            .setMessage(getString(R.string.alert_dialog_message))
            .setPositiveButton(getString(R.string.alert_dialog_positive_button)) { _, _ ->
                listOfDevices.clear()
                viewModel.deleteDevice(device)
                getDevicesListDeleteMode(true)
            }
            .setNegativeButton(getString(R.string.alert_dialog_negative_button)) { dialogInterface, _ ->
                dialogInterface.cancel()
                dialogInterface.dismiss()
            }
            .show()
    }

    //region ========================================== getDevices ==========================================

    private fun getDevicesListDeleteMode(deleteMode: Boolean) {
        devicesListAdapter.submitList(null)
        devicesListAdapter = DevicesListAdapter(activity as MainActivity, this, deleteMode)

        binding?.deviceRecyclerView?.apply {
            this.adapter = devicesListAdapter
            getDevicesListFromDatabase()
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity as MainActivity, 2)
        }
    }

    private fun getDeviceListFilterText() {
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

    private fun getDevicesListFromDatabase() {
        listOfDevices.clear()

        viewModel.apply {
            allDevices.observe(viewLifecycleOwner, { devices ->
                listOfDevices.addAll(devices)
                Log.i("prePopulateDatabase", "$listOfDevices")

                CoroutineScope(Dispatchers.Main).launch {
                    binding?.deviceListProgressBar?.isVisible = true
                    delay(1500)
                    binding?.deviceListProgressBar?.isVisible = false

                    when (sharedPreferences.getString("devices_sort_by", "productType")) {
                        "productType" -> {
                            listOfDevices.sortBy { device -> device.productType }
                            binding?.toolbar?.menu?.getItem(0)?.isChecked = true
                        }
                        "deviceName" -> {
                            listOfDevices.sortBy { device -> device.deviceName }
                            binding?.toolbar?.menu?.getItem(1)?.isChecked = true
                        }
                    }
                    devicesListAdapter.submitList(listOfDevices)
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