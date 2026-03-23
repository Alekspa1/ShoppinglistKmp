package com.drag0n.shoppinglistkmp.domain.model.items


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("id")
    val id: Int,
    @SerialName("is_crossed")
    val isCrossed: Boolean,
    @SerialName("n")
    val n: Int,
    @SerialName("name")
    val name: String
)