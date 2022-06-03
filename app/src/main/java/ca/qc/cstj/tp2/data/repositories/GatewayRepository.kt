package ca.qc.cstj.tp2.data.repositories

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.data.datasources.GatewayDataSource
import ca.qc.cstj.tp2.domain.models.Customer
import ca.qc.cstj.tp2.domain.models.Gateway
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.Exception

class GatewayRepository {
    private val gatewayDataSource = GatewayDataSource()
    private val json = Json { ignoreUnknownKeys=true }

    suspend fun retrieveAll() : Flow<LoadingResource<MutableList<Gateway>>> {
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

    suspend fun retrieveAllFromCustomer(href: String) :  Flow<LoadingResource<MutableList<Gateway>>>{
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

    suspend fun install(href: String, jsonData: String) : Resource<Gateway> {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = href.plus("/gateways").httpPost().jsonBody(jsonData).responseJson()
            when(result){
                is Result.Success -> {
                    return@withContext Resource.Success(json.decodeFromString<Gateway>(result.value.content))
                }
                is Result.Failure -> {
                    return@withContext Resource.Error(result.error.exception)
                }
            }
        }
    }
}