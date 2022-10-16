package dev.syoritohatsuki.deathcounter.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClientConfig(
    val ip: String = "0.0.0.0",
    val port: Int = 3000,
    val msDelay: Int = 5000
)
