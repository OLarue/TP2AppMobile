package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkNode(
    val name: String,
    val connection: Connection
)
