package com.suonk.testautomationapp.ui.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.ItemDeviceBinding
import com.suonk.testautomationapp.models.data.Device

class DevicesListAdapter(private val activity: Activity) :
    ListAdapter<Device, DevicesListAdapter.ViewHolder>(DevicesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = getItem(position)
        Log.i("deviceData", "$device")
        holder.onBind(device)
    }

    inner class ViewHolder(
        private val binding: ItemDeviceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(device: Device) {

            Log.i("deviceData", "${device.deviceName}")
            Log.i("deviceData", "${device.deviceIcon}")

            binding.deviceName.text = device.deviceName

            binding.deviceIcon.setImageDrawable(
                ResourcesCompat.getDrawable(
                    activity.resources,
                    device.deviceIcon,
                    null
                )
            )
        }
    }

    class DevicesComparator : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.deviceIcon == newItem.deviceIcon &&
                    oldItem.deviceName == newItem.deviceName &&
                    oldItem.productType == newItem.productType
        }
    }
}