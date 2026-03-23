package com.drag0n.shoppinglistkmp.domain.repository

import com.drag0n.shoppinglistkmp.domain.model.CreateList
import com.drag0n.shoppinglistkmp.domain.model.ShopList
import com.drag0n.shoppinglistkmp.domain.model.items.Items

interface ShopsitemsRepository {

    suspend fun createShopList(key: String,name: String) : Result<CreateList>
    suspend fun addItemShopList(id: Int,value: String, count: Int)
    suspend fun getAllShopsList(key: String) : Result<ShopList>
    suspend fun getItemsList(id:Int) : Result<Items>

}