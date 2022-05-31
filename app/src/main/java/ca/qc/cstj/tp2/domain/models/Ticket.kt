package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val href: String = "",
    val ticketNumber: String = "",
    val createdDate: String = "",
    val priority: String = "",
    val status: String = "",
    val customer: Customer = Customer()
)
