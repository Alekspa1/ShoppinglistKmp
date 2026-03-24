package com.drag0n.shoppinglistkmp.data.network

import com.drag0n.shoppinglistkmp.Const.CROSS_IT
import com.drag0n.shoppinglistkmp.Const.ITEM
import com.drag0n.shoppinglistkmp.Const.LIST
import com.drag0n.shoppinglistkmp.Const.UPDATES
import com.drag0n.shoppinglistkmp.domain.model.CreateList
import com.drag0n.shoppinglistkmp.domain.model.ShopList
import com.drag0n.shoppinglistkmp.domain.model.items.Items
import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class ShopsitemsRepositoryImpl(private val ktor: HttpClient) : ShopsitemsRepository {

    override suspend fun createShopList(
        key: String,
        name: String
    ): Result<CreateList> = runCatching {
        ktor.post("https://cyber-unisafe.ru/shopping/v4/CreateShoppingList"){
            parameter("key", key)
            parameter("name", name)
        }.body()
    }



    override suspend fun addItemShopList(id: Int, value: String, count: Int) {
        ktor.post("https://cyber-unisafe.ru/shopping/v4/AddToShoppingList"){
            parameter("id", id)
            parameter("value", value)
            parameter("n", count)
        }
    }

    override suspend fun getAllShopsList(key: String): Result<ShopList> = runCatching {
        ktor.get("https://cyber-unisafe.ru/shopping/v4/GetAllMyShopLists"){
            parameter("key", key)
        }.body()
    }

    override suspend fun getItemsList(id: Int): Result<Items> = runCatching {
        ktor.get("https://cyber-unisafe.ru/shopping/v4/GetShoppingList"){
            parameter("list_id", id)
        }.body()
    }

    override suspend fun delete(id: Int, who: String) {
        when(who){
                ITEM -> {
                    ktor.post("https://cyber-unisafe.ru/shopping/v4/RemoveFromList") {
                        parameter("item_id", id)
                    }
                }
                LIST ->{
                    ktor.post("https://cyber-unisafe.ru/shopping/v4/RemoveShoppingList") {
                        parameter("list_id", id)
                    }
                }
        }

    }

    override suspend fun update(
        id: Int,
        value: String,
        n: String,
        who: String
    ) {
        when(who){
            UPDATES -> {
                ktor.post("https://cyber-unisafe.ru/shopping/v4/UpdateShoppingList") {
                    parameter("id", id)
                    parameter("value", value)
                    parameter("n", n)
                }
            }
            CROSS_IT -> {
                ktor.post("https://cyber-unisafe.ru/shopping/v4/CrossItOff") {
                    parameter("id", id)
                }
            }
        }
    }
}