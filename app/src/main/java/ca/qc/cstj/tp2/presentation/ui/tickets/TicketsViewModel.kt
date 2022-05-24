package ca.qc.cstj.tp2.presentation.ui.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.repositories.TicketRepository
import ca.qc.cstj.tp2.domain.models.Ticket
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketsViewModel: ViewModel() {
    private val ticketRepository = TicketRepository()

    private val _tickets = MutableLiveData<Resource<List<Ticket>>>()
    val tickets : LiveData<Resource<List<Ticket>>> get() = _tickets

    private val _ticketsLoading = MutableLiveData<LoadingResource<List<Ticket>>>()
    val ticketsLoading : LiveData<LoadingResource<List<Ticket>>> get() = _ticketsLoading

    init {
        viewModelScope.launch {

            // ticketRepository retrieve all, refresh au 30 secondes
            ticketRepository.retrieveAll().collect {
                _ticketsLoading.value = it
            }
        }
    }
}