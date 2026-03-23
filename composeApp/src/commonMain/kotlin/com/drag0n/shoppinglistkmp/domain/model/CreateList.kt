package com.drag0n.shoppinglistkmp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateList(
    @SerialName("list_id")
    val listId: Int,
    @SerialName("success")
    val success: Boolean
)