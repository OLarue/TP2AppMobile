package ca.qc.cstj.tp2.data.datasources

import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.domain.models.Network
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NetworkDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll() : List<Network> {
        return withContext(Dispatchers.IO) {
            val (_,_, result) = Constants.BaseURL.NETWORK.httpGet().responseJson()
            when(result){
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }

        }

    }
}