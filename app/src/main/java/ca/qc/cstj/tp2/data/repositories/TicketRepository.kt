package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.datasources.TicketDataSource
import ca.qc.cstj.tp2.domain.models.Ticket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class TicketRepository {

    private val ticketDataSource =  TicketDataSource()

    suspend fun retrieveAll(): Flow<LoadingResource<List<Ticket>>> {
        return  flow{
            while (true)
            {
                try {
                    emit(LoadingResource.Loading())
                    emit(LoadingResource.Success(ticketDataSource.retrieveAll()))
                } catch (ex: Exception){
                    emit(LoadingResource.Error(ex,"erreur serveur"))
                }
                delay(Constants.RefreshRates.TICKET_REFRESH_RATE)
            }
        }
    }


}