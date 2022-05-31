package ca.qc.cstj.tp2.presentation.ui.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.repositories.NetworkRepository
import kotlinx.coroutines.launch
import ca.qc.cstj.tp2.domain.models.Network
import ca.qc.cstj.tp2.domain.models.NetworkNode
import kotlinx.coroutines.flow.collect



class NetworkViewModel : ViewModel() {
    private val networkRepository = NetworkRepository()

    private val _network = MutableLiveData<LoadingResource<Network>>()
    val network : LiveData<LoadingResource<Network>> get() = _network

    init {
        refreshNetwork()
    }

    private fun refreshNetwork() {
        viewModelScope.launch {
            networkRepository.retrieve().collect(){
                _network.value = it
            }
        }
    }
}