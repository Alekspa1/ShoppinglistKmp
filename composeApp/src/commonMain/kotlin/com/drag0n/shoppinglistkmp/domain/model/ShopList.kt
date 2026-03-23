package com.drag0n.shoppinglistkmp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopList(
    @SerialName("shop_list")
    val shopList: List<Shop>,
    @SerialName("success")
    val success: Boolean
)