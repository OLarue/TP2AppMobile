package ca.qc.cstj.tp2.presentation.ui.tickets.detail

import androidx.lifecycle.*
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.repositories.GatewayRepository
import ca.qc.cstj.tp2.data.repositories.TicketRepository
import ca.qc.cstj.tp2.domain.models.Gateway
import ca.qc.cstj.tp2.domain.models.Ticket
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailTicketViewModel(private val href:String) : ViewModel() {
    private val ticketRepository = TicketRepository()
    private val gatewayRepository = GatewayRepository()

    private val _ticket = MutableLiveData<Resource<Ticket>>()
    val ticket: LiveData<Resource<Ticket>> get() = _ticket

    private val _gateways = MutableLiveData<LoadingResource<List<Gateway>>>()
    val gateways: LiveData<LoadingResource<List<Gateway>>> get() = _gateways

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

    fun changeTicketStatus(){

    }

}