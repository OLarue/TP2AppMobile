package ca.qc.cstj.tp2.data.datasources

import android.widget.Toast
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.domain.models.Customer
import ca.qc.cstj.tp2.domain.models.Gateway
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GatewayDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll() : MutableList<Gateway> {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = Constants.BaseURL.GATEWAYS.httpGet().responseJson()
            when(result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun retrieveAllFromCustomer(href:String) : MutableList<Gateway>{
        return withContext(Dispatchers.IO) {
            val (_, _, result) = href.plus("/gateways").httpGet().responseJson()
            when(result) {
                is Result.Success -> {
                    return@withContext json.decodeFromString(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }

    suspend fun reboot(serialNumber:String) : Gateway{
        return withContext(Dispatchers.IO) {
            val url = "/${serialNumber}/actions?type=reboot"
            val(_,_,result) = Constants.BaseURL.GATEWAYS.plus(url).httpPost().jsonBody("").responseJson()
            when(result){
                is Result.Success -> {
                    return@withContext json.decodeFromString<Gateway>(result.value.content)
                }
                is Result.Failure -> {
                    throw result.error.exception
                }
            }
        }
    }


}