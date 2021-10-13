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

    val allLights = repository.allLights.asLiveData()
    val allHeaters = repository.allHeaters.asLiveData()
    val allRollerShutters = repository.allRollerShutters.asLiveData()

    fun updateLight(light: Light) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateLight(light)
    }

    fun updateHeater(heater: Heater) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateHeater(heater)
    }

    fun updateRollerShutter(rs: RollerShutter) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRollerShutter(rs)
    }

    fun deleteLight(light: Light) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteLight(light)
    }

    fun deleteHeater(heater: Heater) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteHeater(heater)
    }

    fun deleteRollerShutter(rs: RollerShutter) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRollerShutter(rs)
    }

    //endregion

    //region ======================================= User and Address =======================================

    val user = repository.user.asLiveData()

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

    //endregion

    //region ==================================== Data Between Fragments ====================================

    val productType = MutableLiveData<String>()
    fun setProductType(pt: String) {
        productType.value = pt
    }

    val light = MutableLiveData<Light>()
    fun setLight(l: Light) {
        light.value = l
    }

    val rollerShutter = MutableLiveData<RollerShutter>()
    fun setRollerShutter(rs: RollerShutter) {
        rollerShutter.value = rs
    }

    val heater = MutableLiveData<Heater>()
    fun setHeater(h: Heater) {
        heater.value = h
    }

    //endregion
}