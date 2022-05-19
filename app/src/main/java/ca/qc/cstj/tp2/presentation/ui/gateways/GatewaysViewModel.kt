package ca.qc.cstj.tp2.presentation.ui.gateways

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.domain.models.Gateway
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GatewaysViewModel : ViewModel() {
    private val gatewayRepository = GatewayRepository()

    private val _gateways = MutableLiveData<LoadingResource<List<Gateway>>>()
    val gateways : LiveData<LoadingResource<List<Gateway>>> get() = _gateways

    init {
        refreshGateways()
    }

    fun refreshGateways() {
        viewModelScope.launch {
            gatewayRepository.retrieveAll().collect {
                _gateways.value = it
            }
        }
    }
}