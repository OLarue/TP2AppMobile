package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import androidx.lifecycle.*
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.domain.models.Gateway
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DetailGatewayViewModel (private val href:String) : ViewModel(){
    private val gatewayRepository = GatewayRepository()

    private val _gateway = MutableLiveData<LoadingResource<Gateway>>()
    val gateway: LiveData<LoadingResource<Gateway>> get() = _gateway


    init{
        viewModelScope.launch {

            gatewayRepository.retrieve(href).collect {
                _gateway.value = it
            }
        }
    }

    fun reboot() {
        val serialNumber = _gateway.value!!.data!!.serialNumber
        viewModelScope.launch{
            _gateway.value = gatewayRepository.reboot(serialNumber)
        }
    }

    fun update() {
        val serialNumber = _gateway.value!!.data!!.serialNumber
        viewModelScope.launch{
            _gateway.value = gatewayRepository.update(serialNumber)
        }
    }

    //Pour le constructeur qui prend un parametre
    class Factory(private val href:String) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }
}