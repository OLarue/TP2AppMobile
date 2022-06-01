package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import androidx.lifecycle.*
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.domain.models.Gateway
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class DetailGatewayViewModel (private val href:String) : ViewModel(){
    private val gatewayRepository = GatewayRepository()

    private val _gateway = MutableLiveData<Resource<Gateway>>()
    val gateway: LiveData<Resource<Gateway>> get() = _gateway


    init{
        viewModelScope.launch {
        }
    }


    //code pour le reboot, reste code a P-A pour tester
    fun reboot(){
//        val serialNumber = _gateway.value!!.data!!.serialNumber
        val serialNumber = Constants.HARDCORDED_SERIAL_NUMBER
        viewModelScope.launch{

            gatewayRepository.reboot(serialNumber).collect{
                _gateway.value = it
            }
        }
    }

    //Pour le constructeur qui prend un parametre
    class Factory(private val href:String) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }
}