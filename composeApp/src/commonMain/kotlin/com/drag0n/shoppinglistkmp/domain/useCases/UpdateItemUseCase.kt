package com.drag0n.shoppinglistkmp.domain.useCases

import com.drag0n.shoppinglistkmp.domain.repository.ShopsitemsRepository

class UpdateItemUseCase(private val repository: ShopsitemsRepository) {

    suspend operator fun invoke(id: Int,value:String,n:String,who: String) = repository.update(id,value,n,who)
}