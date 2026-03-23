package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class CreateShopListUseCase(private val repository: ShopsitemsRepository) {

    suspend operator fun invoke(key: String,name: String) = repository.createShopList(key,name)
}