package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val status: String,
    val ip: String,
    val download: Float,
    val upload: Float,
    val signal: Int,
    val ping: Int
)
