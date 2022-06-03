package ca.qc.cstj.tp2.core

object Constants {

    object BaseURL {
        //private const val BASE_API = "http://10.0.2.2:5000"
        private const val BASE_API = "https://api.andromia.science"
        const val TICKETS = "${BASE_API}/tickets"
        const val CUSTOMERS = "${BASE_API}/customers"
        const val GATEWAYS = "${BASE_API}/gateways"
        const val NETWORK = "${BASE_API}/network"
    }

    const val FLAG_API_URL = "https://flagcdn.com/h40/%s.png"

    enum class TicketPriority {
        Low, Normal, High, Critical
    }

    enum class TicketStatus {
        Open, Solved
    }

    enum class ConnectionStatus {
        Online, Offline
    }

    val LOADING_MAX = 10

    object RefreshRates{
        const val TICKET_REFRESH_RATE : Long = 30000
        const val GATEWAY_REFRESH_RATE : Long = 5000
        const val NETWORK_REFRESH_RATE : Long = 120000
    }


    object NodeMetrics{
        const val LATENCY : String = " ns"
        const val SIGNAL : String = " dBm"
        const val UPLOAD : String = " Ebps"
        const val DOWNLOAD : String = " Ebps"
        //We could've join them together( uplaod and download), but one might change in the future while the other stay at this metric.
    }

    val HARDCORDED_SERIAL_NUMBER : String = "ca6ac438abb1dd44"
}