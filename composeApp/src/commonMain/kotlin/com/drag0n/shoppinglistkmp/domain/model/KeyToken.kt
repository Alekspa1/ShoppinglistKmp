package com.drag0n.shoppinglistkmp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyToken(
    @SerialName("key")
    val key: String,
    @SerialName("success")
    val success: Boolean
)