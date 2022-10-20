package dev.syoritohatsuki.deathcounter.client.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClientConfig(
    val port: Int = 3000,
    val msDelay: Int = 5000
)
