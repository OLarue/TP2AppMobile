package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
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
                    emit(LoadingResource.Error(ex,ex.message))
                }
                delay(Constants.RefreshRates.TICKET_REFRESH_RATE)
            }
        }
    }

    suspend fun retrieve(href: String): Resource<Ticket> {
        try{
            return Resource.Success(ticketDataSource.retrieve(href))
        } catch (ex:Exception) {
            return Resource.Error(ex, ex.message)
        }
    }

    suspend fun updateStatus(){

    }


}