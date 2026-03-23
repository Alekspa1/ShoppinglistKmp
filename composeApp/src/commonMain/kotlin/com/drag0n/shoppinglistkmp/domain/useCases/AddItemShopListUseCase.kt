package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class AddItemShopListUseCase(private val repository: ShopsitemsRepository) {
    suspend operator fun invoke (id: Int,value: String, count: Int) = repository.addItemShopList(id,value,count)
}