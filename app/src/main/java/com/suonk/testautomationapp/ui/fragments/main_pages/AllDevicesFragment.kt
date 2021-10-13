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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        devicesListAdapter = DevicesListAdapter(activity as MainActivity, this)


        sharedPreferences = (activity as MainActivity).getSharedPreferences(
            "devices_sort_by",
            Context.MODE_PRIVATE
        )

        initRecyclerView()
        initToolbar()
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
                else -> {
                    super.onOptionsItemSelected(menuItem)
                    true
                }
            }
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

    //endregion

    fun navigateToDeviceDetails(position: Int) {
        when (listOfDevices[position].productType) {
            "Light" -> {
                viewModel.setLight(listOfDevices[position] as Light)
            }
            "Heater" -> {
                viewModel.setHeater(listOfDevices[position] as Heater)
            }
            "RollerShutter" -> {
                viewModel.setRollerShutter(listOfDevices[position] as RollerShutter)
            }
        }
        viewModel.setProductType(listOfDevices[position].productType)
        (activity as MainActivity).startDeviceDetails()
    }

    private fun getDevicesListFromDatabase() {
        listOfDevices.clear()

        viewModel.apply {
            CoroutineScope(Dispatchers.Main).launch {
                allLights.observe(viewLifecycleOwner, { lights ->
                    listOfDevices.addAll(lights)
                    Log.i("prePopulateDatabase", "$lights")
                })

                allHeaters.observe(viewLifecycleOwner, { heaters ->
                    listOfDevices.addAll(heaters)
                    Log.i("prePopulateDatabase", "$heaters")
                })

                allRollerShutters.observe(viewLifecycleOwner, { rollerShutters ->
                    listOfDevices.addAll(rollerShutters)
                    Log.i("prePopulateDatabase", "$listOfDevices")
                })

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
                Log.i(
                    "prePopulateDatabase", "Avant"
                )
                Log.i("listOfDevices", "$listOfDevices")
                devicesListAdapter.submitList(listOfDevices)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}