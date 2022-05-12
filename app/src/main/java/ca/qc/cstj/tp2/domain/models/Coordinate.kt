package ca.qc.cstj.tp2.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val latitude: Float,
    val longitude: Float
)
