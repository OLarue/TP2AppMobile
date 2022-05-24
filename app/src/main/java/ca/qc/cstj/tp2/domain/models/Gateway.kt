package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Gateway(
    val href: String = "",
    val serialNumber: String = "",
    val revision: String = "",
    val pin: String = "",
    val hash: String = "",
    val connection: Connection = Connection(),
    val config: Config = Config(),
    val customer: Customer = Customer()
)
