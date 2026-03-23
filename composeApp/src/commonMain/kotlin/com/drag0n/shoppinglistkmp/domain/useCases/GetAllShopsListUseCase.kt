package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class GetAllShopsListUseCase(private val repository: ShopsitemsRepository) {

    suspend operator fun invoke(key: String) = repository.getAllShopsList(key)
}