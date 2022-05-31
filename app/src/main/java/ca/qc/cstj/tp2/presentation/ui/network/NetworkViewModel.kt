package ca.qc.cstj.tp2.presentation.ui.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.repositories.NetworkRepository
import kotlinx.coroutines.launch
import ca.qc.cstj.tp2.domain.models.Network
import kotlinx.coroutines.flow.collect



class NetworkViewModel : ViewModel() {
    private val networkRepository = NetworkRepository()

    private val _networks = MutableLiveData<LoadingResource<List<Network>>>()
    val networks : LiveData<LoadingResource<List<Network>>> get() = _networks

    init {
        refreshNetworks()
    }

    private fun refreshNetworks() {
        viewModelScope.launch {
            networkRepository.retrieveAll().collect {
                _networks.value = it
            }
        }
    }
}