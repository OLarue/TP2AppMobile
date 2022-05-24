package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val status: String = "",
    val ip: String = "",
    val download: Float = 0.0F,
    val upload: Float = 0.0F,
    val signal: Int = 0,
    val ping: Int = 0
)
