package com.suonk.testautomationapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suonk.testautomationapp.models.data.*
import com.suonk.testautomationapp.repositories.AutomationAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutomationViewModel @Inject constructor(private val repository: AutomationAppRepository) :
    ViewModel() {

    //region ============================================ Devices ===========================================

    val allDevices = repository.allDevices.asLiveData()
    fun getDeviceById(id: Int) = repository.getDeviceById(id).asLiveData()

    fun updateDevice(device: Device) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDevice(device)
    }

    fun deleteDevice(device: Device) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteDevice(device)
    }

    //endregion

    //region ============================================= User =============================================

    val user = repository.user.asLiveData()

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

    //endregion

    //region ==================================== Data Between Fragments ====================================

    val device = MutableLiveData<Device>()
    fun setDevice(d: Device) {
        device.value = d
    }

    //endregion
}