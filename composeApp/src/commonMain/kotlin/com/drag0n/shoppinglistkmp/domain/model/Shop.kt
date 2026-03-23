package com.drag0n.shoppinglistkmp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    @SerialName("created")
    val created: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)