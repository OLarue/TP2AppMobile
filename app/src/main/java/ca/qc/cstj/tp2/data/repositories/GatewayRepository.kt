package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.datasources.GatewayDataSource
import ca.qc.cstj.tp2.domain.models.Gateway
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GatewayRepository {
    private val gatewayDataSource = GatewayDataSource()

    suspend fun retrieveAll() : Flow<LoadingResource<List<Gateway>>> {
        return flow {
            while (true) {
                try {
                    emit(LoadingResource.Loading())
                    emit(LoadingResource.Success(gatewayDataSource.retrieveAll()))
                } catch (ex: Exception) {
                    emit(LoadingResource.Error(ex, ex.message))
                }
                delay(Constants.GATEWAYS_REFRESH_RATE)
            }
        }
    }
}