package com.suonk.testautomationapp.ui.fragments.main_pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentAllDevicesBinding
import com.suonk.testautomationapp.models.data.Device
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.adapters.DevicesListAdapter
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllDevicesFragment : Fragment() {

    private lateinit var devicesListAdapter: DevicesListAdapter
    private val viewModel: AutomationViewModel by activityViewModels()
    private var binding: FragmentAllDevicesBinding? = null
    private var listOfDevices = mutableListOf<Device>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDevicesBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    private fun initializeUI() {

//        getDeviceListFilterText()
        devicesListAdapter = DevicesListAdapter(activity as MainActivity)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.deviceRecyclerView?.apply {
            this.adapter = devicesListAdapter
            getDevicesListFromDatabase()
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity as MainActivity, 2)
        }
    }

    private fun getDevicesListFromDatabase() {
        listOfDevices.clear()

        viewModel.apply {
            allLights.observe(viewLifecycleOwner, { lights ->
                listOfDevices.addAll(lights)
            })

            allHeaters.observe(viewLifecycleOwner, { heaters ->
                listOfDevices.addAll(heaters)
            })

            allRollerShutters.observe(viewLifecycleOwner, { rollerShutters ->
                listOfDevices.addAll(rollerShutters)
                Log.i("listOfDevices", "${listOfDevices}")
            })
        }

        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            devicesListAdapter.submitList(listOfDevices)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}