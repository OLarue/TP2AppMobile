package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.domain.models.Network
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.datasources.NetworkDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow


class NetworkRepository {
    private val networkDataSource = NetworkDataSource()

    suspend fun retrieveAll() : Flow<LoadingResource<List<Network>>> {
        return flow {
            while (true) {
                try {
                    emit(LoadingResource.Loading())
                    emit(LoadingResource.Success(networkDataSource.retrieveAll()))
                } catch(ex: Exception) {
                    emit(LoadingResource.Error(ex, ex.message))
                }
                delay(Constants.RefreshRates.NETWORK_REFRESH_RATE)
            }
        }
    }
}