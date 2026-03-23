package com.drag0n.shoppinglistkmp.domain.repository

import com.drag0n.shoppinglistkmp.domain.model.KeyToken

interface KeyRepository {
   suspend fun createKey() : Result<KeyToken>
}