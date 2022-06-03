package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.data.datasources.GatewayDataSource
import ca.qc.cstj.tp2.domain.models.Gateway
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
                delay(Constants.RefreshRates.GATEWAY_REFRESH_RATE)
            }
        }
    }

    suspend fun retrieveAllFromCustomer(href: String) :  Flow<LoadingResource<List<Gateway>>>{
        return flow {
            while (true) {
                try {
                    emit(LoadingResource.Loading())
                    emit(LoadingResource.Success(gatewayDataSource.retrieveAllFromCustomer(href)))
                }
                catch (ex: Exception) {
                    emit(LoadingResource.Error(ex, ex.message))
                }
                delay(Constants.RefreshRates.GATEWAY_REFRESH_RATE)
            }
        }
    }

    suspend fun reboot(serialNumber: String): LoadingResource<Gateway> {
        return try {
            LoadingResource.Success(gatewayDataSource.reboot(serialNumber))
        } catch (ex:Exception) {
            LoadingResource.Error(ex, ex.message)
        }

    }

    suspend fun update(serialNumber: String) : LoadingResource<Gateway> {
        return try {
            LoadingResource.Success(gatewayDataSource.update(serialNumber))
        } catch (ex:Exception) {
            LoadingResource.Error(ex, ex.message)
        }
    }

    suspend fun retrieve(href: String): Flow<LoadingResource<Gateway>> {
        return flow{
            while(true){
                try{
                    emit(LoadingResource.Loading())
                    emit(LoadingResource.Success(gatewayDataSource.retrieve(href)))
                } catch (ex:Exception){
                    emit(LoadingResource.Error(ex,ex.message))
                }
                delay(Constants.RefreshRates.GATEWAY_REFRESH_RATE)
            }
        }
    }

}