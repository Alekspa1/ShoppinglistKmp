package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class GetItemsListUseCase(private val repository: ShopsitemsRepository) {
    suspend operator fun invoke(id:Int) = repository.getItemsList(id)
}