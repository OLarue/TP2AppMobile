package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val href: String,
    val firstName: String,
    val email: String,
    val address: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val phone: String,
    val coord: Coordinate
)
