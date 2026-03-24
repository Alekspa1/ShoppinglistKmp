package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class DeleteUseCase(private val repository: ShopsitemsRepository) {

    suspend operator fun invoke(id:Int,who: String) = repository.delete(id,who)
}