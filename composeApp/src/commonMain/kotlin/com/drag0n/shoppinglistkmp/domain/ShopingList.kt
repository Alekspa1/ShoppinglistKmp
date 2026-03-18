package com.drag0n.shoppinglistkmp.domain

data class ShopingList(
    val id: Long = 0,
    val name : String = "Список 1",
    val items: List<ShopItems> = emptyList()
)
