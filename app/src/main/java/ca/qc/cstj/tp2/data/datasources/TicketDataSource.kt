package ca.qc.cstj.tp2.data.datasources

import android.widget.Toast
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.domain.models.Customer
import ca.qc.cstj.tp2.domain.models.Ticket
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class TicketDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun retrieveAll(): List<Ticket>{

        return withContext(Dispatchers.IO){
            val (req,res,result) = Constants.BaseURL.TICKETS.httpGet().responseJson()

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

    suspend fun retrieve(href: String) : Ticket{
        return withContext(Dispatchers.IO) {
            val (_,_,result) = href.httpGet().responseJson()
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

    suspend fun solveTicket(href: String) : Ticket{
        return withContext(Dispatchers.IO){
            val solvedURL = "${href}/actions?type=solve"
            val (_,_,result) = solvedURL.httpPost().responseJson()
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

    suspend fun openTicket(href: String) : Ticket{
        return withContext(Dispatchers.IO){
            val solvedURL = "${href}/actions?type=open"
            val (_,_,result) = solvedURL.httpPost().responseJson()
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