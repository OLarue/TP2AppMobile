package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.datasources.TicketDataSource
import ca.qc.cstj.tp2.domain.models.Ticket
import kotlin.Exception

class TicketRepository {

    private val ticketDataSource =  TicketDataSource()

    suspend fun retrieveAll(): LoadingResource<List<Ticket>> {
        return  try {
            LoadingResource.Loading()
            LoadingResource.Success(ticketDataSource.retrieveAll())
        } catch (ex: Exception) {
            LoadingResource.Error(ex)
        }
    }

}