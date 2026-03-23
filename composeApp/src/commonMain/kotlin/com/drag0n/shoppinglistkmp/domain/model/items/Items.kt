package com.drag0n.shoppinglistkmp.domain.model.items


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Items(
    @SerialName("item_list")
    val itemList: List<Item>,
    @SerialName("success")
    val success: Boolean
)