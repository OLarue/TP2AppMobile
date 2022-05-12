package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val nac: String,
    val SSID: String,
    val version: String,
    val kernel: List<String>,
    val kernelRevision: Int
)
