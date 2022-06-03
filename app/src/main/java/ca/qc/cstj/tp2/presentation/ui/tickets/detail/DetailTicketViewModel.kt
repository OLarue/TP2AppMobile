package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.*
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.data.repositories.TicketRepository
import ca.qc.cstj.tp2.domain.models.Gateway
import ca.qc.cstj.tp2.domain.models.Ticket
import io.github.g00fy2.quickie.QRResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailTicketViewModel(private val href:String) : ViewModel() {
    private val ticketRepository = TicketRepository()
    private val gatewayRepository = GatewayRepository()

    private val _ticket = MutableLiveData<Resource<Ticket>>()
    val ticket: LiveData<Resource<Ticket>> get() = _ticket

    private val _gateways = MutableLiveData<LoadingResource<MutableList<Gateway>>>()
    val gateways: LiveData<LoadingResource<MutableList<Gateway>>> get() = _gateways

    init{
        viewModelScope.launch{
            _ticket.value = ticketRepository.retrieve(href)

            gatewayRepository.retrieveAllFromCustomer(ticket.value!!.data!!.customer.href).collect {
                _gateways.value = it
            }
        }
    }

    //Pour le constructeur qui prend un parametre
    class Factory(private val href:String) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }

    fun installRouter(json: String) {

        viewModelScope.launch {
            val result = gatewayRepository.install(_ticket.value!!.data!!.customer.href, json)
            /*when(result)
            {
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    result.data?.let { _gateways.value!!.data!!.add(it) }
                }
            }*/
        }
    }

}