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

    fun getLightByName(name: String) = repository.getLightByName(name).asLiveData()
    fun getHeaterByName(name: String) = repository.getHeaterByName(name).asLiveData()
    fun getRollerShutterByName(name: String) = repository.getRollerShutterByName(name).asLiveData()

    suspend fun addNewLight(light: Light) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNewLight(light)
    }

    suspend fun addNewHeater(heater: Heater) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNewHeater(heater)
    }

    suspend fun addNewRollerShutter(rs: RollerShutter) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNewRollerShutter(rs)
    }

    suspend fun updateLight(light: Light) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateLight(light)
    }

    suspend fun updateHeater(heater: Heater) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateHeater(heater)
    }

    suspend fun updateRollerShutter(rs: RollerShutter) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRollerShutter(rs)
    }

    suspend fun deleteLight(light: Light) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteLight(light)
    }

    suspend fun deleteHeater(heater: Heater) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteHeater(heater)
    }

    suspend fun deleteRollerShutter(rs: RollerShutter) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRollerShutter(rs)
    }

    //endregion

    //region ======================================= User and Address =======================================

    val user = repository.user.asLiveData()
    val address = repository.address.asLiveData()

    fun userAndAddress(id: Int) = repository.userAndAddressWithAddressId(id).asLiveData()

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }

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