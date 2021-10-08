package com.suonk.testautomationapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.User
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

    fun getDeviceByName(deviceName: String) = repository.getDeviceByName(deviceName)

    fun operationOnDevice(position: Int, device: Device) = viewModelScope.launch(Dispatchers.IO) {
        when (position) {
            0 -> repository.addNewDevice(device)
            1 -> repository.deleteDevice(device)
            2 -> repository.updateDevice(device)
        }
    }

    //endregion

    //region ============================================= User =============================================

    val user = repository.user.asLiveData()

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

    //endregion

    //region ============================================ Address ===========================================

    val address = repository.address.asLiveData()

    fun updateAddress(address: Address) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAddress(address)
    }

    //endregion

    //region ==================================== Data Between Fragments ====================================

    val searchBarText = MutableLiveData<String>()
    fun setSearchBarText(text: String) {
        searchBarText.value = text
    }

    //endregion
}